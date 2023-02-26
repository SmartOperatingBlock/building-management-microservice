/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package entity.zone

import kotlinx.serialization.Serializable

/**
 * It describes a zone inside the Operating block.
 * Each zone is identified by an [id] and described
 * by a [name] and a [description].
 */
data class Zone(
    val id: ZoneID,
    val name: String,
    val description: String,
) {
    init {
        require(this.name.isNotEmpty())
        require(this.description.isNotEmpty())
    }

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is Zone -> this.id == other.id
        else -> false
    }

    override fun hashCode(): Int = this.id.hashCode()
}

/**
 * It represents the Operating Block zone ID [value].
 */
@Serializable
data class ZoneID(val value: String) {
    init {
        // Constructor validation: the id must not be empty
        require(this.value.isNotEmpty())
    }
}
