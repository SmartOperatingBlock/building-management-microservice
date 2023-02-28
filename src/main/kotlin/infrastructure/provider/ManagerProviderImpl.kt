/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.provider

import infrastructure.database.DatabaseManager
import infrastructure.digitaltwins.DigitalTwinManager

/**
 * Provider that creates the different managers.
 */
class ManagerProviderImpl : ManagerProvider {
    private val digitalTwinManager = DigitalTwinManager()
    private val databaseManager = DatabaseManager()

    override val roomDigitalTwinManager = digitalTwinManager
    override val roomDatabaseManager = databaseManager
    override val medicalTechnologyDigitalTwinManager = digitalTwinManager
    override val medicalTechnologyDatabaseManager = databaseManager
}
