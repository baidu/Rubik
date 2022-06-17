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
package com.rubik.logger

object Logger {
    var bigLogEnable = false
    var d: (String) -> Unit = {}
    var e: (String, Throwable?) -> Unit = { _, _ -> }
    var da: (String, () -> String) -> Unit = { tag, msgAction ->
        if (bigLogEnable){
            d(tag + "|" + msgAction())
        }
    }
    var ea: (String, () -> String, (() -> Throwable)?) -> Unit = { tag, msgAction, ecptAction ->
        e(tag + "|" + if (bigLogEnable){msgAction()} else {" bigLogDisable "}, ecptAction?.let { ecptAction() })
    }
}