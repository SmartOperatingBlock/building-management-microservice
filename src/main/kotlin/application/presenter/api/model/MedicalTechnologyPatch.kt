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
 * This class represents the merge-patch document of a medical technology.
 * It is possible only to modify its [roomId] - that can be also set to null.
 */
@Serializable
data class MedicalTechnologyPatch(val roomId: String?)
