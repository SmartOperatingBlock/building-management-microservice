/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package entity.zone

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ZoneTest : StringSpec({
    val zone = Zone(ZoneID("1"), "name", "description")
    val zoneUpdate = Zone(ZoneID("1"), "name", "updated description")
    val differentZone = Zone(ZoneID("2"), "name", "description")

    "zone id should not be empty" {
        shouldThrow<IllegalArgumentException> { ZoneID("") }
    }

    "zone name should not be empty" {
        shouldThrow<IllegalArgumentException> { Zone(ZoneID("1"), "", "description") }
    }

    "zone description should not be empty" {
        shouldThrow<IllegalArgumentException> { Zone(ZoneID("1"), "name", "") }
    }

    listOf(
        differentZone,
        null,
        4,
    ).forEach {
        "a zone should not be equal to other zones with different id, other classes or null" {
            zone shouldNotBe it
        }
    }

    "two zones are equal only based on their id" {
        zone shouldBe zoneUpdate
    }

    "two equal zones should have the same hashcode" {
        zone.hashCode() shouldBe zoneUpdate.hashCode()
    }

    "two different zones should not have the same hashcode" {
        zone.hashCode() shouldNotBe differentZone.hashCode()
    }
})
