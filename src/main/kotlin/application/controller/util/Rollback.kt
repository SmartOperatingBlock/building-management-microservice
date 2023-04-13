/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.controller.util

/**
 * Extension method used with methods that return a boolean result status
 * that allows to perform [rollbackActions] when it returns false.
 */
fun Boolean.rollback(rollbackActions: () -> Unit): Boolean =
    if (!this) {
        rollbackActions()
        false
    } else {
        true
    }
