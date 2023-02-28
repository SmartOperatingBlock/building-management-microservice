/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.database.model

import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.RoomID
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

/**
 * This class models a time series data of a medical technology.
 * This data is respect to a specific [dateTime].
 * Its [metadata] will be useful in order to correctly interpret the carried [value].
 */
@Serializable
data class TimeSeriesMedicalTechnologyUsage(
    @Contextual val dateTime: Instant,
    val metadata: TimeSeriesMedicalTechnologyMetadata,
    val value: Boolean
)

/**
 * This class represents the metadata about data in time series.
 * It contains the [medicalTechnologyId] respect to which the usage is referred and the [roomId] in which happened.
 */
@Serializable
data class TimeSeriesMedicalTechnologyMetadata(val medicalTechnologyId: MedicalTechnologyID, val roomId: RoomID)
