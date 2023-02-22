/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.controller

import application.controller.manager.RoomDigitalTwinManager
import entity.zone.Room
import entity.zone.RoomID
import usecase.repository.RoomRepository
import java.util.Date

/**
 * Implementation of room repository that handle the application logic
 * using both db and digital twin.
 * @param[roomDtManager] the digital twin manager of rooms.
 */
class RoomController(private val roomDtManager: RoomDigitalTwinManager) : RoomRepository {
    override fun createRoom(room: Room): Room? =
        if (this.roomDtManager.createRoomDigitalTwin(room)) room else null

    override fun deleteRoom(roomId: RoomID): Boolean {
        return this.roomDtManager.deleteRoomDigitalTwin(roomId)
    }

    override fun findBy(roomId: RoomID, dateTime: Date?): Room? =
        if (dateTime == null) {
            this.roomDtManager.findBy(roomId)
        } else {
            null
        }

    override fun getRooms(): Set<Room> {
        TODO("Not yet implemented")
    }
}
