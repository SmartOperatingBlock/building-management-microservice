/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.digitaltwins

import application.controller.manager.RoomDigitalTwinManager
import entity.zone.Room
import entity.zone.RoomID

/**
 * Simple Test Double (fake) for [RoomDigitalTwinManager] in order to perform tests.
 */
class DigitalTwinManagerTestDouble : RoomDigitalTwinManager {
    private val roomList: MutableSet<Room> = mutableSetOf()

    override fun createRoomDigitalTwin(room: Room): Boolean {
        this.roomList.add(room)
        return true
    }

    override fun deleteRoomDigitalTwin(roomId: RoomID): Boolean {
        return this.roomList.removeIf { it.id == roomId }
    }

    override fun findBy(roomId: RoomID): Room? {
        return this.roomList.find { it.id == roomId }
    }
}
