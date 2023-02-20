/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.api.deserializer

import application.presenter.api.model.RoomApiDto
import application.presenter.api.model.RoomApiDtoType
import entity.zone.Room
import entity.zone.RoomID
import entity.zone.RoomType
import entity.zone.ZoneID

/**
 * Deserializer for data that comes from API.
 */
object ApiDeserializer {
    /**
     * Extension method to convert Room API DTO to [entity.zone.Room] class.
     */
    fun RoomApiDto.toRoom() = Room(
        id = RoomID(this.id),
        type = this.type.toRoomType(),
        zoneId = ZoneID(this.zoneId),
        name = this.name,
    )

    private fun RoomApiDtoType.toRoomType() = when (this) {
        RoomApiDtoType.OPERATING_ROOM -> RoomType.OPERATING_ROOM
        RoomApiDtoType.PRE_OPERATING_ROOM -> RoomType.PRE_OPERATING_ROOM
    }
}
