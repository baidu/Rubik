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
import com.rubik.router.navigateForResult
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
class RouterTest {
    @Before
    fun init(){
        TestAggregate.register()
    }

    @Test
    fun useBadUri() {
        val result = navigateForResult<Int> {
            uri = "a://b/c"
            query {
                "p1" with 1
            }
        }
        Assert.assertSame(result, 2)
    }

    @Test
    fun useRouterAdd1() {
        navigate {
            uri = "test://agg.test/int/add1"
            query {
                "p1" with 1
            }
            result<Int> { result ->
                Assert.assertSame(result, 2)
            }
        }
    }

    @Test
    fun useRouterAdd2() {
        navigate {
            uri = "test://agg.test/int/add2"
            query {
                "p1" with 1
            }
            result<Int> { result ->
                Assert.assertSame(result, 3)
            }
        }
    }

    @Test
    fun useRouterAdd1ForResult() {
        val result = navigateForResult<Int> {
            uri = "test://agg.test/int/add1"
            query {
                "p1" with 1
            }
        }
        Assert.assertSame(result, 2)
    }

    @Test
    fun useRouterAdd2ForResult() {
        val result = navigateForResult<Int> {
            uri = "test://agg.test/int/add2"
            query {
                "p1" with 1
            }
        }
        Assert.assertSame(result, 3)
    }
}