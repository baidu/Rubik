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
package com.rubik.route.mapping

import androidx.lifecycle.*
import com.rubik.logger.Logger
import java.lang.reflect.Type

internal fun Any.toLiveData(type: Type): Any? {
    return if (this is LiveData<*>)
        type.firstTypeArgument?.let { liveType ->
            Transformations.map(this) { value ->
                value.toType(liveType)
            }.apply {
                Logger.da(" RUBIK ") { " liveData rvalue : $this" }
            }
        }
    else
        null
}


