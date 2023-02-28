/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.controller

import application.controller.manager.RoomDatabaseManager
import application.controller.manager.RoomDigitalTwinManager
import entity.zone.Room
import entity.zone.RoomID
import usecase.repository.RoomRepository
import java.time.Instant

/**
 * Implementation of room repository that handle the application logic
 * using both db and digital twin.
 * @param[roomDtManager] the digital twin manager for rooms.
 * @param[roomDatabaseManager] the database manager for rooms.
 */
class RoomController(
    private val roomDtManager: RoomDigitalTwinManager,
    private val roomDatabaseManager: RoomDatabaseManager
) : RoomRepository {
    override fun createRoom(room: Room): Room? = (
        this.roomDtManager.createRoomDigitalTwin(room) &&
            this.roomDatabaseManager.saveRoom(room).rollback {
                this.roomDtManager.deleteRoomDigitalTwin(room.id)
            }
        ).let { if (it) room else null }

    override fun deleteRoom(roomId: RoomID): Boolean =
        this.roomDtManager.deleteRoomDigitalTwin(roomId) && this.roomDatabaseManager.deleteRoom(roomId)

    override fun findBy(roomId: RoomID, dateTime: Instant?): Room? =
        if (dateTime == null) { // if the date-time is null, then obtain present information
            this.roomDtManager.findBy(roomId)
        } else { // get historical data
            this.roomDatabaseManager.findBy(roomId, dateTime)
        }

    override fun getRooms(): Set<Room> = this.roomDatabaseManager.getAllRooms()

    private fun Boolean.rollback(rollbackActions: () -> Unit): Boolean =
        if (!this) {
            rollbackActions()
            false
        } else true
}
