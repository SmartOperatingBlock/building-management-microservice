/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package usecase.repository

import entity.zone.Room
import entity.zone.RoomID

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
     * Find a room by its [roomId].
     * @return the room if present, null otherwise.
     */
    fun findBy(roomId: RoomID): Room?
}
