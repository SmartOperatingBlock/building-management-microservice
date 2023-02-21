/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.service

import entity.zone.Room
import usecase.repository.RoomRepository

/**
 * Module that wraps all the services that orchestrate the application logic (not domain one).
 */
object Service {
    /**
     * Interface that models an Application Service.
     * An Application Service handle application logic without business elements.
     * @param[T] the type returned by the service.
     */
    interface ApplicationService<out T> {
        /**
         * Method to execute the application service.
         * @return the result of type [T]
         */
        fun execute(): T
    }

    /**
     * Application Service that has the objective of creating a [room] using the [roomRepository] passed.
     */
    class CreateRoom(private val room: Room, private val roomRepository: RoomRepository) : ApplicationService<Room?> {
        override fun execute(): Room? = this.roomRepository.createRoom(room)
    }
}
