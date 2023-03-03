/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.digitaltwins.query

import infrastructure.digitaltwins.query.AdtQuery.Companion.AdtQueryUtils.eq
import infrastructure.digitaltwins.query.AdtQuery.Companion.AdtQueryUtils.isOfModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class AdtQueryTest : StringSpec({
    "it should be possible to specify a simple query to obtain all digital twins" {
        val query = AdtQuery
            .createQuery()
            .select("*")
            .fromDigitalTwins("T")
            .query

        val expectedQuery = "SELECT * FROM DIGITALTWINS T"

        query shouldBe expectedQuery
    }

    "it should be possible to specify a complete query" {
        val query = AdtQuery
            .createQuery()
            .selectTop(1, "T.prop1")
            .fromDigitalTwins("T")
            .joinRelationship("CT", "T", "relationship1")
            .where("T" isOfModel "model1")
            .and("CT.\$dtId" eq "id1")
            .query

        val expectedResult = "SELECT TOP(1) T.prop1" +
            " FROM DIGITALTWINS T" +
            " JOIN CT RELATED T.relationship1" +
            " WHERE IS_OF_MODEL(T, 'model1')" +
            " AND CT.\$dtId = 'id1'"

        query shouldBe expectedResult
    }
})
