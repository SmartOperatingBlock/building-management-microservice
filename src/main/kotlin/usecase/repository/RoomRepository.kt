/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package usecase.repository

import entity.zone.Room
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import java.time.Instant

/**
 * Interface that models the repository to manage Rooms.
 */
interface RoomRepository {
    /**
     * Create a [room].
     * @return null if the room already exists, the room created otherwise.
     */
    fun createRoom(room: Room): Room?

    /**
     * Delete a room by its [roomId].
     * @return true if correctly deleted, false otherwise.
     */
    fun deleteRoom(roomId: RoomID): Boolean

    /**
     * Find a room by its [roomId] and get its data in a specific [dateTime] if specified.
     * @return the room if present, null otherwise.
     */
    fun findBy(roomId: RoomID, dateTime: Instant?): Room?

    /**
     * Get all the room environmental data withing a range of dates.
     * The room is identified by its [roomId] and the data is obtained from the [start] instant to the [end] one.
     * @return the list of room data associated to its date-time if the room exist, null otherwise.
     */
    fun getRoomEnvironmentalData(
        roomId: RoomID,
        start: Instant,
        end: Instant,
    ): List<Pair<Instant, RoomEnvironmentalData>>?

    /**
     * Get all the rooms.
     * @return the set of rooms.
     */
    fun getRooms(): Set<Room>

    /**
     * Update the [environmentalData] about a room identified by its [roomId].
     * The data is associated to a [dateTime].
     */
    fun updateRoomEnvironmentalData(
        roomId: RoomID,
        environmentalData: RoomEnvironmentalData,
        dateTime: Instant,
    ): Boolean
}
