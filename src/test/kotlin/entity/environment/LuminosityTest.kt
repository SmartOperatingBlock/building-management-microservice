/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package entity.environment

import entity.environment.EnvironmentData.Luminosity
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec

class LuminosityTest : StringSpec({
    val negativeLuminosityValue = -10.0

    "it should be impossible to create a luminosity object with negative value" {
        shouldThrow<IllegalArgumentException> { Luminosity(negativeLuminosityValue) }
    }
})
