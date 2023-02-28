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
 * It represents the presentation of a single [entity.zone.Room] entry
 * with plain information to be serialized. The necessary information are [id], [name], [zoneId] and the [type].
 */
@Serializable
data class RoomEntry(
    val id: String,
    val name: String,
    val zoneId: String,
    val type: String
)
