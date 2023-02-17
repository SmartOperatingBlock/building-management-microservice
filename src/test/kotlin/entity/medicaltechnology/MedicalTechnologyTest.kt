/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package entity.medicaltechnology

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class MedicalTechnologyTest : StringSpec({
    val medicalTechnology = MedicalTechnology(
        MedicalTechnologyID("1"),
        "mt1",
        "The base medical technology",
        MedicalTechnologyType.ENDOSCOPE)
    val medicalTechnologyUpdate = MedicalTechnology(MedicalTechnologyID("1"),
        "mt1",
        "The base medical technology with a changed description",
        MedicalTechnologyType.XRAY)
    val differentIdMedicalTechnology = MedicalTechnology(MedicalTechnologyID("2"),
        "mt1",
        "The base medical technology",
        MedicalTechnologyType.ENDOSCOPE)

    "a medical technology id should not be empty" {
        shouldThrow<IllegalArgumentException> { MedicalTechnologyID("") }
    }

    "medical technology name should not be empty" {
        shouldThrow<IllegalArgumentException> { MedicalTechnology(
            MedicalTechnologyID("1"),
            "", "description",
            MedicalTechnologyType.XRAY
        ) }
    }

    "medical technology description should not be empty" {
        shouldThrow<IllegalArgumentException> { MedicalTechnology(
            MedicalTechnologyID("1"),
            "name", "",
            MedicalTechnologyType.XRAY
        ) }
    }

    listOf(
        differentIdMedicalTechnology,
        null,
        4,
    ).forEach {
        "medical technology should not be equal to medical technologies with different id, other classes or null" {
            medicalTechnology shouldNotBe it
        }
    }

    "two medical technology are equal only based on their id" {
        medicalTechnology shouldBe medicalTechnologyUpdate
    }

    "as medical technology are equal only based on their id, then hashcode must respect it" {
        medicalTechnology.hashCode() shouldBe medicalTechnologyUpdate.hashCode()
    }

    "two different medical technology should have different hash codes" {
        medicalTechnology.hashCode() shouldNotBe differentIdMedicalTechnology.hashCode()
    }
})
