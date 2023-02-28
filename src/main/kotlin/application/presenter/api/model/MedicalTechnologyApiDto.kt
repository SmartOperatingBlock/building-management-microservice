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
 * Presenter class to be able to handle data that comes from API.
 * It handles [id], [name], [description], [type], [inUse] and the [roomId].
 */
@Serializable
data class MedicalTechnologyApiDto(
    val id: String,
    val name: String,
    val description: String,
    val type: MedicalTechnologyApiDtoType,
    val inUse: Boolean = false,
    val roomId: String? = null
)

/**
 * Presenter enum to deserialize medical technology type that comes from API.
 */
@Serializable
enum class MedicalTechnologyApiDtoType {
    ENDOSCOPE,
    XRAY,
}
