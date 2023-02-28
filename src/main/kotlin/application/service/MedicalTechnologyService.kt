/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.service

import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import usecase.repository.MedicalTechnologyRepository
import java.time.Instant

/**
 * Module that wraps all the services that orchestrate the application logic (not domain one).
 */
object MedicalTechnologyService {
    /**
     * Application Service that has the objective of creating a [medicalTechnology] using
     * the provided [medicalTechnologyRepository].
     */
    class CreateMedicalTechnology(
        private val medicalTechnology: MedicalTechnology,
        private val medicalTechnologyRepository: MedicalTechnologyRepository
    ) : ApplicationService<MedicalTechnology?> {
        override fun execute(): MedicalTechnology? =
            if (this.medicalTechnologyRepository.findBy(medicalTechnology.id, null) == null) {
                this.medicalTechnologyRepository.createMedicalTechnology(medicalTechnology)
            } else null
    }

    /**
     * Application Service that has the objective of getting the information about a specific medical technology
     * identified by a [medicalTechnologyId] that - if specified - are respect a specific [dateTime]
     * using the provided [medicalTechnologyRepository].
     */
    class GetMedicalTechnology(
        private val medicalTechnologyId: MedicalTechnologyID,
        private val medicalTechnologyRepository: MedicalTechnologyRepository,
        private val dateTime: Instant? = null
    ) : ApplicationService<MedicalTechnology?> {
        override fun execute(): MedicalTechnology? =
            this.medicalTechnologyRepository.findBy(medicalTechnologyId, dateTime)
    }
}
