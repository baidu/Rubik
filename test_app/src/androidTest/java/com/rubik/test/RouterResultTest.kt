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
import com.rubik.router.navigate
import com.rubik.router.result
import com.rubik.test.router.TestAggregate
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RouterResultTest {
    @Before
    fun init(){
        TestAggregate.register()
    }

    @Test
    fun useRouterResult3() {
        navigate {
            uri = "test://agg.test/result/get3"
            result<String, String, String> { r1, r2, r3 ->
                Assert.assertSame(r1, "r1")
                Assert.assertSame(r2, "r2")
                Assert.assertSame(r3, "r3")
            }
        }
    }

    @Test
    fun useRouterResult4() {
        navigate {
            uri = "test://agg.test/result/get4"
            result<String, String, String, String> { r1, r2, r3, r4->
                Assert.assertSame(r1, "r1")
                Assert.assertSame(r2, "r2")
                Assert.assertSame(r3, "r3")
                Assert.assertSame(r4, "r4")
            }
        }
    }

    @Test
    fun useRouterResult5() {
        navigate {
            uri = "test://agg.test/result/get5"
            result<String, String, String, String, String> { r1, r2, r3, r4, r5 ->
                Assert.assertSame(r1, "r1")
                Assert.assertSame(r2, "r2")
                Assert.assertSame(r3, "r3")
                Assert.assertSame(r4, "r4")
                Assert.assertSame(r5, "r5")
            }
        }
    }
}