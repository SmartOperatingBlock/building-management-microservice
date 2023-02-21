/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.provider

import application.controller.manager.RoomDigitalTwinManager

/**
 * Provider of managers.
 */
interface ManagerProvider {
    /** The manager of the Digital Twin of the room. */
    val roomDigitalTwinManager: RoomDigitalTwinManager
}
