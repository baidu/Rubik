/**
 * Copyright (C) Baidu Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rubik.router

import com.rubik.Rubik
import com.rubik.router.annotations.RInvariant

/**
 * A Holder for rubik context to touch other.
 *
 * @since 1.6
 */
@RInvariant
open class TouchHolder(private val uri: String) {
    private var touched: Boolean? = null

    private fun tryTouch() {
        if (null == touched)
            touched = Rubik.touchUri(uri)
    }

    /**
     * Do touch other context action.
     *
     * @param action invoke if can touch this context.
     */
    @RInvariant
    fun touch(action: () -> Unit) = apply {
        tryTouch()
        if (touched == true) {
            action()
        }
    }

    /**
     * Do touch other context action.
     *
     * @param action invoke if can not touch this context.
     */
    @RInvariant
    fun miss(action: () -> Unit) = apply {
        tryTouch()
        if (touched == false) {
            action()
        }
    }
}