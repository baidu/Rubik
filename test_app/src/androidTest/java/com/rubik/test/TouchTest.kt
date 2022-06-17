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
package com.rubik.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rubik.router.*
import com.rubik.test.router.TestAggregate
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TouchTest {

    @Before
    fun init(){
        TestAggregate.register()
    }

    @Test
    fun useTouch() {
        touch("test://agg.test") {
            log("touched")
        }
    }

    @Test
    fun useMiss() {
        touch("test://agg.miss") {
            log("touched")
        }.miss {
            log("missed")
        }
    }

    @Test
    fun useRouterTouch() {
        Router.touch("test://agg.test") {
            log("Router touched")
        }
    }

    @Test
    fun useRouterMiss() {
        Router.touch("test://agg.miss") {
            log("Router touched")
        }.miss {
            log("Router missed")
        }
    }

    @Test
    fun useTouchHolder() {
        TouchHolder("test://agg.test").touch{
            log("Holder touched")
        }
    }

    @Test
    fun useTouchHolderMiss() {
        TouchHolder("test://agg.miss").touch{
            log("Holder touched")
        }.miss {
            log("Holder missed")
        }
    }


}
