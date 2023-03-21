/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.service

import application.presenter.api.model.RoomEntry
import application.presenter.api.serializer.ApiSerializer.toRoomEntry
import entity.zone.Room
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import usecase.repository.RoomRepository
import java.time.Instant

/**
 * Module that wraps all the services that orchestrate the application logic (not domain one).
 */
object RoomService {
    /**
     * Application Service that has the objective of creating a [room] using the provided [roomRepository].
     */
    class CreateRoom(private val room: Room, private val roomRepository: RoomRepository) : ApplicationService<Room?> {
        override fun execute(): Room? =
            if (this.roomRepository.findBy(room.id, null) == null) {
                this.roomRepository.createRoom(room)
            } else null
    }

    /**
     * Application Service that has the objective of deleting a room using the provided [roomRepository].
     */
    class DeleteRoom(
        private val roomID: RoomID,
        private val roomRepository: RoomRepository
    ) : ApplicationService<Boolean> {
        override fun execute(): Boolean = this.roomRepository.deleteRoom(roomID)
    }

    /**
     * Application Service that has the objective of getting the information about a specific room identified
     * by a [roomID] that - if specified - are respect a specific [dateTime] using the provided [roomRepository].
     */
    class GetRoom(
        private val roomID: RoomID,
        private val roomRepository: RoomRepository,
        private val dateTime: Instant? = null
    ) : ApplicationService<Room?> {
        override fun execute(): Room? = this.roomRepository.findBy(roomID, dateTime)
    }

    /**
     * Application Service that has the object to obtain all the environmental data about a room, identified by
     * its [roomID], within a range of date [startDateTime] - [endDateTime] using the provided [roomRepository].
     */
    class ExportRoomEnvironmentalData(
        private val roomID: RoomID,
        private val roomRepository: RoomRepository,
        private val startDateTime: Instant,
        private val endDateTime: Instant?
    ) : ApplicationService<List<Pair<Instant, RoomEnvironmentalData>>?> {
        override fun execute(): List<Pair<Instant, RoomEnvironmentalData>>? =
            this.roomRepository.getRoomEnvironmentalData(roomID, startDateTime, endDateTime ?: Instant.now())
    }

    /**
     * Application Service that has the objective of getting all the room that are inside the building using
     * the provided [roomRepository].
     * This method will not return all the information about a room, but only its [RoomEntry].
     */
    class GetAllRoomEntry(private val roomRepository: RoomRepository) : ApplicationService<Set<RoomEntry>> {
        override fun execute(): Set<RoomEntry> = this.roomRepository.getRooms().map { it.toRoomEntry() }.toSet()
    }

    /**
     * Application Service that has the objective of updating the [environmentalData] about a room identified
     * by its [roomId]. The data refers to a specific [dateTime] and it is updated via the provided
     * [roomRepository].
     */
    class UpdateRoomEnvironmentData(
        private val roomId: RoomID,
        private val environmentalData: RoomEnvironmentalData,
        private val dateTime: Instant,
        private val roomRepository: RoomRepository
    ) : ApplicationService<Boolean> {
        override fun execute(): Boolean =
            if (this.roomRepository.findBy(roomId, null) != null) {
                this.roomRepository.updateRoomEnvironmentalData(roomId, environmentalData, dateTime)
            } else false
    }
}
