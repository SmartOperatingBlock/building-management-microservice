/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.api.util

import kotlinx.serialization.Serializable

/** Module that wraps the Api Responses in order to cope with the REST API responses. */
object ApiResponses {
    /**
     * Class that represents an [entry] returned as response by an API.
     * It needs to include the entry's [url].
     */
    @Serializable
    data class ResponseEntry<out T>(val entry: T, val url: String)

    /**
     * Class that represents an [entry] returned as response by an API.
     * Each entry is associated with its [date].
     */
    @Serializable
    data class ResponseTimedEntry<out T>(val entry: T, val date: String)

    /**
     * Class that represents a list of [entries] returned as response to an API request.
     * As the REST API best-practise recommend it is included also the [total] number of the entries.
     */
    @Serializable
    data class ResponseEntryList<out T>(val entries: List<T>, val total: Int = entries.count())
}
