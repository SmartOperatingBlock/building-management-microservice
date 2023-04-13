/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.digitaltwins.adtpresentation

import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.medicaltechnology.MedicalTechnologyType

/**
 * Module to wrap all the presentation-related stuff for Azure Digital Twins
 * with [entity.medicaltechnology.MedicalTechnology].
 */
object MedicalTechnologyAdtPresentation {
    private const val MEDICAL_TECHNOLOGY_MODEL = "dtmi:io:github:smartoperatingblock:MedicalTechnology;1"
    private const val NAME_PROPERTY = "name"
    private const val DESCRIPTION_PROPERTY = "description"
    private const val TYPE_PROPERTY = "type"
    private const val TYPE_ENDOSCOPE = "0"
    private const val TYPE_XRAY = "1"
    private const val IS_IN_USE_PROPERTY = "is_in_use"

    /** "Is located in operating room" relationship name in Azure Digital Twins. */
    const val IS_LOCATED_IN_OPERATING_ROOM_RELATIONSHIP = "rel_is_located"

    /**
     * Convert a [MedicalTechnology] to a Digital Twin.
     * Specifically this extension method will convert it into the Azure Digital Twins SDK [BasicDigitalTwin].
     * @return the corresponding [BasicDigitalTwin].
     */
    fun MedicalTechnology.toDigitalTwin(): BasicDigitalTwin =
        BasicDigitalTwin(this.id.value)
            .setMetadata(BasicDigitalTwinMetadata().setModelId(MEDICAL_TECHNOLOGY_MODEL))
            .addToContents(NAME_PROPERTY, this.name)
            .addToContents(DESCRIPTION_PROPERTY, this.description)
            .addToContents(
                TYPE_PROPERTY,
                when (this.type) {
                    MedicalTechnologyType.ENDOSCOPE -> TYPE_ENDOSCOPE
                    MedicalTechnologyType.XRAY -> TYPE_XRAY
                },
            )
            .addToContents(IS_IN_USE_PROPERTY, this.isInUse)

    /**
     * Obtain a [MedicalTechnology] instance from its Azure Digital Twins' [BasicDigitalTwin].
     * @return the corresponding twin.
     */
    fun BasicDigitalTwin.toMedicalTechnology(): MedicalTechnology =
        MedicalTechnology(
            id = MedicalTechnologyID(this.id),
            name = this.contents[NAME_PROPERTY].propertyAs(defaultValue = ""),
            description = this.contents[DESCRIPTION_PROPERTY].propertyAs(defaultValue = ""),
            type = when (this.contents[TYPE_PROPERTY].propertyAs(defaultValue = "not supported")) {
                TYPE_ENDOSCOPE -> MedicalTechnologyType.ENDOSCOPE
                TYPE_XRAY -> MedicalTechnologyType.XRAY
                else -> throw IllegalArgumentException("medical technology type not supported")
            },
            isInUse = this.contents[IS_IN_USE_PROPERTY].propertyAs(defaultValue = false),
        )

    /**
     * Obtain the relationship id of the [IS_LOCATED_IN_OPERATING_ROOM_RELATIONSHIP] between
     * the Medical Technology Digital Twin - identified by its [medicalTechnologyID]
     * and the Operating Room Digital Twin.
     * @return the relationship id.
     */
    fun isLocatedRelationshipId(medicalTechnologyID: MedicalTechnologyID): String =
        "${medicalTechnologyID.value}-located"
}
