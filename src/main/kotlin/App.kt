/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

import infrastructure.api.APIController
import infrastructure.events.KafkaClient
import infrastructure.provider.ManagerProviderImpl

/**
 * Template for kotlin projects.
 */
fun main() {
    val provider = ManagerProviderImpl()
    APIController(provider).start()
    KafkaClient(provider).start()
}
