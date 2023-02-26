/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.controller.manager

import entity.zone.Room
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import java.util.Date

/**
 * This interface models the manager of the database for rooms.
 * In this way it is independent respect to the specific technology that is used,
 * having the only semantic dependency on the concept of db.
 */
interface RoomDatabaseManager {
    /**
     * Save a [room] inside the DB.
     * @return true if successfully stored, false otherwise.
     */
    fun saveRoom(room: Room): Boolean

    /**
     * Delete a room identified by its [roomId] from the DB.
     * @return true if successfully deleted, false otherwise.
     */
    fun deleteRoom(roomId: RoomID): Boolean

    /**
     * Get a room identified by its [roomId] from the DB.
     * Specify a past [dateTime] in order to get historical data.
     * @return null if data is not available, the room instead.
     */
    fun findBy(roomId: RoomID, dateTime: Date): Room?

    /**
     * Get all the rooms available.
     * @return a set of rooms.
     */
    fun getAllRooms(): Set<Room>

    /**
     * Update the [environmentalData] about a room identified by its [roomId].
     * The data is associated to a [dateTime].
     */
    fun updateRoomEnvironmentalData(
        roomId: RoomID,
        environmentalData: RoomEnvironmentalData,
        dateTime: Instant
    ): Boolean
}
