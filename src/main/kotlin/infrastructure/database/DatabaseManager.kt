/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.database

import application.controller.manager.MedicalTechnologyDatabaseManager
import application.controller.manager.RoomDatabaseManager
import application.presenter.database.model.TimeSeriesDataType
import application.presenter.database.model.TimeSeriesMedicalTechnologyMetadata
import application.presenter.database.model.TimeSeriesMedicalTechnologyUsage
import application.presenter.database.model.TimeSeriesRoomEnvironmentalData
import application.presenter.database.model.TimeSeriesRoomMetadata
import application.presenter.database.serialization.toRoomEnvironmentalData
import application.presenter.database.serialization.toTimeSeries
import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.Room
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import org.litote.kmongo.KMongo
import org.litote.kmongo.ascendingSort
import org.litote.kmongo.descendingSort
import org.litote.kmongo.div
import org.litote.kmongo.eq
import org.litote.kmongo.find
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.gt
import org.litote.kmongo.lte
import org.litote.kmongo.setValue
import java.time.Instant

/**
 * Implementation of the room database manager.
 */
class DatabaseManager(customConnectionString: String? = null) : RoomDatabaseManager, MedicalTechnologyDatabaseManager {
    private val connectionString: String

    init {
        // If the client has not provided a custom connection string, then we need to get it from environment.
        if (customConnectionString == null) {
            checkNotNull(System.getenv(mongodbConnectionStringVariable)) { "mongodb connection string required" }
        }
        connectionString = customConnectionString ?: System.getenv(mongodbConnectionStringVariable)
    }
    private val database = KMongo.createClient(this.connectionString).getDatabase(databaseName)
    private val roomCollection = this.database.getCollection<Room>(roomCollectionName)
    private val roomTimeSeriesCollection = this.database.getCollection<TimeSeriesRoomEnvironmentalData>(
        roomEnvironmentalDataCollectionName,
    )
    private val medicalTechnologiesCollection =
        this.database.getCollection<MedicalTechnology>(medicalTechnologyCollectionName)
    private val medicalTechnologyDataCollection =
        this.database.getCollection<TimeSeriesMedicalTechnologyUsage>(medicalTechnologyDataCollectionName)

    override fun saveRoom(room: Room): Boolean = this.roomCollection.safeMongoDbWrite(defaultResult = false) {
        insertOne(room).wasAcknowledged()
    }

    override fun deleteRoom(roomId: RoomID): Boolean = this.roomCollection.safeMongoDbWrite(defaultResult = false) {
        roomTimeSeriesCollection.deleteMany(
            TimeSeriesRoomEnvironmentalData::metadata / TimeSeriesRoomMetadata::roomId eq roomId,
        )
        deleteOne(Room::id eq roomId).deletedCount > 0
    }

    override fun findBy(roomId: RoomID, dateTime: Instant): Room? =
        this.roomCollection.findOne { Room::id eq roomId }?.copy(
            environmentalData = TimeSeriesDataType.values().associateWith {
                this.roomTimeSeriesCollection.find(
                    TimeSeriesRoomEnvironmentalData::metadata / TimeSeriesRoomMetadata::roomId eq roomId,
                    TimeSeriesRoomEnvironmentalData::metadata / TimeSeriesRoomMetadata::type eq it,
                    TimeSeriesRoomEnvironmentalData::dateTime lte dateTime,
                ).descendingSort(TimeSeriesRoomEnvironmentalData::dateTime).first() ?: null
            }.toRoomEnvironmentalData(),
        )

    override fun getRoomEnvironmentalData(
        roomId: RoomID,
        start: Instant,
        end: Instant,
    ): List<Pair<Instant, RoomEnvironmentalData>>? {
        var roomCurrentData = this.findBy(roomId, start)?.environmentalData
        if (roomCurrentData != null) {
            // The room exist
            return this.roomTimeSeriesCollection.find(
                TimeSeriesRoomEnvironmentalData::metadata / TimeSeriesRoomMetadata::roomId eq roomId,
                TimeSeriesRoomEnvironmentalData::dateTime gt start,
                TimeSeriesRoomEnvironmentalData::dateTime lte end,
            ).ascendingSort(TimeSeriesRoomEnvironmentalData::dateTime).toList().map {
                // update current data about the room in order to have an update image of it
                val updatedRoom = mapOf(it.metadata.type to it).toRoomEnvironmentalData(roomCurrentData)
                roomCurrentData = updatedRoom
                // map the update to corresponding date time
                it.dateTime to updatedRoom
            }
        } else {
            return null // The room does not exist
        }
    }

    override fun getAllRooms(): Set<Room> = this.roomCollection.find().toSet()

    override fun updateRoomEnvironmentalData(
        roomId: RoomID,
        environmentalData: RoomEnvironmentalData,
        dateTime: Instant,
    ): Boolean = this.roomTimeSeriesCollection.safeMongoDbWrite(mapOf()) {
        // update time series
        val updatesMap = environmentalData.toTimeSeries(dateTime, roomId)
        insertMany(updatesMap.values.map { it })
        updatesMap
    }.let {
        // update room info with the latest values
        this@DatabaseManager.roomCollection.safeMongoDbWrite(false) {
            updateOne(
                Room::id eq roomId,
                it.toRoomEnvironmentalData().let { roomEnvData ->
                    listOfNotNull(
                        roomEnvData.temperature?.let { t ->
                            setValue(Room::environmentalData / RoomEnvironmentalData::temperature, t)
                        },
                        roomEnvData.humidity?.let { t ->
                            setValue(Room::environmentalData / RoomEnvironmentalData::humidity, t)
                        },
                        roomEnvData.luminosity?.let { t ->
                            setValue(Room::environmentalData / RoomEnvironmentalData::luminosity, t)
                        },
                        roomEnvData.presence?.let { t ->
                            setValue(Room::environmentalData / RoomEnvironmentalData::presence, t)
                        },
                    )
                },
            )
            true
        }
    }

    override fun saveMedicalTechnology(medicalTechnology: MedicalTechnology): Boolean =
        this.medicalTechnologiesCollection.safeMongoDbWrite(defaultResult = false) {
            insertOne(medicalTechnology).wasAcknowledged()
        }

    override fun deleteMedicalTechnology(medicalTechnologyId: MedicalTechnologyID): Boolean =
        this.medicalTechnologiesCollection.safeMongoDbWrite(defaultResult = false) {
            medicalTechnologyDataCollection.deleteMany(
                TimeSeriesMedicalTechnologyUsage::metadata /
                    TimeSeriesMedicalTechnologyMetadata::medicalTechnologyId eq medicalTechnologyId,
            )
            deleteOne(MedicalTechnology::id eq medicalTechnologyId).deletedCount > 0
        }

    override fun findBy(medicalTechnologyId: MedicalTechnologyID, dateTime: Instant): MedicalTechnology? =
        with(
            this.medicalTechnologyDataCollection.find(
                TimeSeriesMedicalTechnologyUsage::metadata /
                    TimeSeriesMedicalTechnologyMetadata::medicalTechnologyId eq medicalTechnologyId,
                TimeSeriesMedicalTechnologyUsage::dateTime lte dateTime,
            ).descendingSort(TimeSeriesMedicalTechnologyUsage::dateTime).first(),
        ) {
            this@DatabaseManager.medicalTechnologiesCollection.findOne {
                MedicalTechnology::id eq medicalTechnologyId
            }?.copy(isInUse = this?.value ?: false, roomId = this?.metadata?.roomId)
        }

    override fun mapTo(medicalTechnologyId: MedicalTechnologyID, roomId: RoomID?): Boolean =
        this.medicalTechnologiesCollection.safeMongoDbWrite(defaultResult = false) {
            updateOne(MedicalTechnology::id eq medicalTechnologyId, setValue(MedicalTechnology::roomId, roomId))
                .matchedCount > 0
        }

    override fun updateMedicalTechnologyUsage(
        medicalTechnologyId: MedicalTechnologyID,
        usage: Boolean,
        roomId: RoomID,
        dateTime: Instant,
    ): Boolean = this.medicalTechnologyDataCollection.safeMongoDbWrite(false) {
        // update time series data
        insertOne(
            TimeSeriesMedicalTechnologyUsage(
                dateTime,
                TimeSeriesMedicalTechnologyMetadata(medicalTechnologyId, roomId),
                usage,
            ),
        ).wasAcknowledged()
    }.let {
        // update medical technology current data
        this@DatabaseManager.medicalTechnologiesCollection.safeMongoDbWrite(false) {
            updateOne(MedicalTechnology::id eq medicalTechnologyId, setValue(MedicalTechnology::isInUse, usage))
            true
        }
    }

    private fun <T, R> MongoCollection<T>.safeMongoDbWrite(defaultResult: R, operation: MongoCollection<T>.() -> R): R =
        try {
            operation()
        } catch (exception: MongoException) {
            println(exception)
            defaultResult
        }

    companion object {
        private const val mongodbConnectionStringVariable = "MONGODB_CONNECTION_STRING"
        private const val databaseName = "building_management"
        private const val roomCollectionName = "rooms"
        private const val roomEnvironmentalDataCollectionName = "room_data"
        private const val medicalTechnologyCollectionName = "medical_technologies"
        private const val medicalTechnologyDataCollectionName = "medical_technologies_data"
    }
}
