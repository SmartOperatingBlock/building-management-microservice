/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.event.model

import kotlinx.serialization.Serializable

/** Medical Technology update event. */
@Serializable
data class MedicalTechnologyEvent(
    override val key: String,
    override val data: MedicalTechnologyUsagePayload,
    override val dateTime: String,
) : Event<MedicalTechnologyUsagePayload>

/**
 * [MedicalTechnologyEvent] payload that refers to the usage described by the [inUse] property of
 * a medical technology identified by its [medicalTechnologyID].
 */
@Serializable
data class MedicalTechnologyUsagePayload(val medicalTechnologyID: String, val inUse: Boolean)

/** Module that wraps the possible keys for a [MedicalTechnologyEvent]. */
object MedicalTechnologyEventKey {
    /** Medical Technology usage update event. */
    const val USAGE_EVENT = "MEDICAL_TECHNOLOGY_USAGE_EVENT"
}
