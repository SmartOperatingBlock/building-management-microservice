/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.service

import application.controller.MedicalTechnologyController
import application.controller.RoomController
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.medicaltechnology.MedicalTechnologyType
import entity.zone.Room
import entity.zone.RoomID
import entity.zone.RoomType
import entity.zone.ZoneID
import infrastructure.database.DatabaseManager
import infrastructure.database.withMongo
import infrastructure.digitaltwins.DigitalTwinManagerTestDouble
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.Instant
import java.time.temporal.ChronoUnit

class MedicalTechnologyServiceTest : StringSpec({
    val exampleMedicalTechnology = MedicalTechnology(
        id = MedicalTechnologyID("mt-1"),
        name = "name",
        description = "description",
        type = MedicalTechnologyType.ENDOSCOPE
    )
    val databaseManager by lazy { DatabaseManager("mongodb://localhost:27017") }
    fun medicalTechnologyController() = MedicalTechnologyController(DigitalTwinManagerTestDouble(), databaseManager)
    fun roomController() = RoomController(DigitalTwinManagerTestDouble(), databaseManager)

    "I should be able to create a medical technology" {
        withMongo {
            val medicalTechnologyController = medicalTechnologyController()
            val result = MedicalTechnologyService.CreateMedicalTechnology(
                exampleMedicalTechnology,
                medicalTechnologyController
            ).execute()
            result shouldNotBe null
            MedicalTechnologyService.GetMedicalTechnology(exampleMedicalTechnology.id, medicalTechnologyController)
                .execute() shouldBe exampleMedicalTechnology
        }
    }

    "I should not be able to insert a pre-existing medical technology" {
        withMongo {
            val medicalTechnologyController = medicalTechnologyController()
            MedicalTechnologyService.CreateMedicalTechnology(exampleMedicalTechnology, medicalTechnologyController)
                .execute()
            MedicalTechnologyService.CreateMedicalTechnology(exampleMedicalTechnology, medicalTechnologyController)
                .execute() shouldBe null
        }
    }

    "I should be able to delete an existing medical technology" {
        withMongo {
            val medicalTechnologyController = medicalTechnologyController()
            MedicalTechnologyService.CreateMedicalTechnology(exampleMedicalTechnology, medicalTechnologyController)
                .execute()
            val result = MedicalTechnologyService.DeleteMedicalTechnology(
                exampleMedicalTechnology.id,
                medicalTechnologyController
            ).execute()
            result shouldBe true
            MedicalTechnologyService.GetMedicalTechnology(exampleMedicalTechnology.id, medicalTechnologyController)
                .execute() shouldBe null
        }
    }

    "If a medical technology does not exist, then call delete on it result in a failed request" {
        withMongo {
            val medicalTechnologyController = medicalTechnologyController()
            MedicalTechnologyService.DeleteMedicalTechnology(exampleMedicalTechnology.id, medicalTechnologyController)
                .execute() shouldBe false
        }
    }

    "I should be able to get the current state of an existing medical technology" {
        withMongo {
            val medicalTechnologyController = medicalTechnologyController()
            MedicalTechnologyService.CreateMedicalTechnology(exampleMedicalTechnology, medicalTechnologyController)
                .execute()
            MedicalTechnologyService.GetMedicalTechnology(exampleMedicalTechnology.id, medicalTechnologyController)
                .execute() shouldBe exampleMedicalTechnology
        }
    }

    "I should be able to get the past state of an existing medical technology" {
        withMongo {
            val medicalTechnologyController = medicalTechnologyController()
            MedicalTechnologyService.CreateMedicalTechnology(exampleMedicalTechnology, medicalTechnologyController)
                .execute()
            MedicalTechnologyService.GetMedicalTechnology(
                exampleMedicalTechnology.id,
                medicalTechnologyController,
                Instant.now().minus(1, ChronoUnit.DAYS)
            ).execute()?.id shouldBe exampleMedicalTechnology.id
        }
    }

    "If a medical technology does not exist, a request on its state, must return null" {
        withMongo {
            MedicalTechnologyService.GetMedicalTechnology(exampleMedicalTechnology.id, medicalTechnologyController())
                .execute() shouldBe null
        }
    }

    "It should be possible to map an existing medical technology to an existing room" {
        withMongo {
            val medicalTechnologyController = medicalTechnologyController()
            val roomController = roomController()
            MedicalTechnologyService.CreateMedicalTechnology(exampleMedicalTechnology, medicalTechnologyController)
                .execute()
            val roomId = RoomID("r1")
            RoomService.CreateRoom(
                Room(
                    roomId,
                    RoomType.OPERATING_ROOM,
                    ZoneID("z1")
                ),
                roomController
            ).execute()
            MedicalTechnologyService.MapMedicalTechnologyToRoom(
                exampleMedicalTechnology.id,
                roomId,
                roomController,
                medicalTechnologyController
            ).execute() shouldBe true
            MedicalTechnologyService.GetMedicalTechnology(exampleMedicalTechnology.id, medicalTechnologyController)
                .execute()?.roomId shouldBe roomId
        }
    }

    "If the room does not exist, it should not be possible to map a medical technology to that room" {
        withMongo {
            val medicalTechnologyController = medicalTechnologyController()
            MedicalTechnologyService.CreateMedicalTechnology(exampleMedicalTechnology, medicalTechnologyController)
                .execute()
            MedicalTechnologyService.MapMedicalTechnologyToRoom(
                exampleMedicalTechnology.id,
                RoomID("r-1"),
                roomController(),
                medicalTechnologyController
            ).execute() shouldBe false
        }
    }

    "If the medical technology does not exist, we can't map it to a room" {
        withMongo {
            val medicalTechnologyController = medicalTechnologyController()
            val roomController = roomController()
            RoomService.CreateRoom(
                Room(
                    RoomID("r1"),
                    RoomType.OPERATING_ROOM,
                    ZoneID("z1")
                ),
                roomController
            )
            MedicalTechnologyService.MapMedicalTechnologyToRoom(
                exampleMedicalTechnology.id,
                RoomID("r-1"),
                roomController,
                medicalTechnologyController
            ).execute() shouldBe false
        }
    }
})
