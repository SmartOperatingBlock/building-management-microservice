/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.database

import application.controller.manager.RoomDatabaseManager
import entity.zone.Room
import entity.zone.RoomID
import java.util.Date

/**
 * Implementation of the room database manager.
 */
class DatabaseManager : RoomDatabaseManager {
    init {
        checkNotNull(System.getenv(mongodbConnectionStringVariable)) { "mongodb connection string required" }
    }

//    private val database = KMongo
//        .createClient(System.getenv(mongodbConnectionStringVariable))
//        .getDatabase(databaseName)

    override fun saveRoom(room: Room): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteRoom(roomId: RoomID): Boolean {
        TODO("Not yet implemented")
    }

    override fun findBy(roomId: RoomID, dateTime: Date): Room? {
        TODO("Not yet implemented")
    }

    override fun getAllRooms(): Set<Room> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val mongodbConnectionStringVariable = "MONGODB_CONNECTION_STRING"
//        private const val databaseName = "building_management"
//        private const val roomCollectionName = "rooms"
    }
}
