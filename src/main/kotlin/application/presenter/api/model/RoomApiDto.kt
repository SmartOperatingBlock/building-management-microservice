/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Presenter class to be able to deserialize data that comes from API.
 * It deserializes [id], [name], [zoneId] and the [type] of the room.
 */
@Serializable
data class RoomApiDto(
    val id: String,
    val name: String,
    @SerialName("zone-id") val zoneId: String,
    val type: RoomApiDtoType
)

/**
 * Presenter enum class to deserialize room type that comes from API.
 */
@Serializable
enum class RoomApiDtoType {
    /** Operating room type. */
    OPERATING_ROOM,
    /** Pre-operating room type. */
    PRE_OPERATING_ROOM
}
