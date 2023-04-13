/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.api.model

import kotlinx.serialization.Serializable

/**
 * Presenter class to be able to handle data that is returned to the API.
 * It handles [id], [name], [description], [type], [inUse] and the [roomId].
 */
@Serializable
data class MedicalTechnologyApiDto(
    val id: String,
    val name: String,
    val description: String,
    val type: MedicalTechnologyApiDtoType,
    val inUse: Boolean = false,
    val roomId: String? = null,
)

/**
 * It represents the presentation of a single [entity.medicaltechnology.MedicalTechnology] entry.
 * The necessary information are [id], [name], [description] and the [type].
 */
@Serializable
data class MedicalTechnologyEntry(
    val id: String,
    val name: String,
    val description: String,
    val type: MedicalTechnologyApiDtoType,
)

/**
 * Presenter enum to handle medical technology type.
 */
@Serializable
enum class MedicalTechnologyApiDtoType {
    ENDOSCOPE,
    XRAY,
}
