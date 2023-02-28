/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.controller.manager

import entity.zone.Room
import entity.zone.RoomID

/**
 * This interface models the manager of Digital Twins for rooms.
 * In this way it is independent respect to the specific technology that is used,
 * having the only semantic dependency on the concept of Digital Twin.
 */
interface RoomDigitalTwinManager {
    /**
     * Create the Digital Twin of the [room].
     * @return true if successfully created, false otherwise.
     */
    fun createRoomDigitalTwin(room: Room): Boolean

    /**
     * Delete the Digital Twin of the room identified by [roomId].
     * @return true if successfully deleted, false otherwise.
     */
    fun deleteRoomDigitalTwin(roomId: RoomID): Boolean

    /**
     * Get the Room Digital Twin data of the room identified by [roomId].
     * @return null if the room is not present, the room otherwise.
     */
    fun findBy(roomId: RoomID): Room?
}
