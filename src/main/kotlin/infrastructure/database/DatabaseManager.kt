/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.database

import application.controller.manager.RoomDatabaseManager
import application.presenter.database.model.TimeSeriesDataType
import application.presenter.database.model.TimeSeriesRoomEnvironmentalData
import application.presenter.database.model.TimeSeriesRoomMetadata
import application.presenter.database.serialization.toRoomEnvironmentalData
import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import entity.zone.Room
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import org.litote.kmongo.KMongo
import org.litote.kmongo.descendingSort
import org.litote.kmongo.div
import org.litote.kmongo.eq
import org.litote.kmongo.find
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.lte
import java.time.Instant

/**
 * Implementation of the room database manager.
 */
class DatabaseManager(customConnectionString: String? = null) : RoomDatabaseManager {
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
        roomEnvironmentalDataCollectionName
    )

    override fun saveRoom(room: Room): Boolean = this.roomCollection.safeMongoDbWrite(defaultResult = false) {
        insertOne(room).wasAcknowledged()
    }

    override fun deleteRoom(roomId: RoomID): Boolean = this.roomCollection.safeMongoDbWrite(defaultResult = false) {
        deleteOne(Room::id eq roomId).deletedCount > 0
    }

    override fun findBy(roomId: RoomID, dateTime: Instant): Room? =
        this.roomCollection.findOne { Room::id eq roomId }?.copy(
            environmentalData = TimeSeriesDataType.values().associateWith {
                this.roomTimeSeriesCollection.find(
                    TimeSeriesRoomEnvironmentalData::metadata / TimeSeriesRoomMetadata::roomId eq roomId,
                    TimeSeriesRoomEnvironmentalData::metadata / TimeSeriesRoomMetadata::type eq it,
                    TimeSeriesRoomEnvironmentalData::dateTime lte dateTime
                ).descendingSort(TimeSeriesRoomEnvironmentalData::dateTime).first() ?: null
            }.toRoomEnvironmentalData()
        )

    override fun getAllRooms(): Set<Room> = this.roomCollection.find().toSet()

    override fun updateRoomEnvironmentalData(
        roomId: RoomID,
        environmentalData: RoomEnvironmentalData,
        dateTime: Instant
    ): Boolean {
        // insert new data to the room_environmental_data collection
        // update environmental data to the room document inside the room collection
        // the environmental data collection has the room ID associated to the environmental data and a timestamp
        // this data model must be created within this package because it is used only here.
        TODO("Not yet implemented")
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
    }
}
