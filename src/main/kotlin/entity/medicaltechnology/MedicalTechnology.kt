/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package entity.medicaltechnology

import entity.zone.RoomID
import kotlinx.serialization.Serializable

/**
 * Describe a medical technology used inside the operating block.
 * This is an entity that is identified by an [id].
 * In addition, it has a [name], a [description] and a [type].
 * A medical technology can be associated to a [entity.zone.Room] through its [roomId].
 * Inside the room the technology can be used and its usage is tracked by [isInUse].
 */
@Serializable
data class MedicalTechnology(
    val id: MedicalTechnologyID,
    val name: String,
    val description: String,
    val type: MedicalTechnologyType,
    val isInUse: Boolean = false,
    val roomId: RoomID? = null,
) {
    init {
        require(name.isNotEmpty())
        require(description.isNotEmpty())
    }

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is MedicalTechnology -> this.id == other.id
        else -> false
    }

    override fun hashCode(): Int = this.id.hashCode()
}

/**
 * Identification of a [MedicalTechnology].
 * @param[value] the id.
 */
@Serializable
data class MedicalTechnologyID(val value: String) {
    init {
        // Constructor validation: The id must not be empty
        require(this.value.isNotEmpty())
    }
}

/**
 * The type of [MedicalTechnology].
 */
@Serializable
enum class MedicalTechnologyType {
    /** Endoscope technology. */
    ENDOSCOPE,
    /** X-ray technology. */
    XRAY,
}
