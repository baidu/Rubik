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
import com.rubik.route.Result
import com.rubik.router.navigate
import com.rubik.router.result
import com.rubik.router.uri.Path
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
class PathTest {
    @Before
    fun init(){
        TestAggregate.register()
    }

    @Test
    fun usePathQueryRouter() {
        navigate {
            uri = "test://agg.test/path-queries/one/two/xxx"
            result<String, String, String> { r1, r2, r3 ->
                Assert.assertEquals(r1, "one")
                Assert.assertEquals(r2, "two")
                Assert.assertEquals(r3, "xxx")
            }
        }
    }

    @Test
    fun usePathQueries() {
        val queries = Path("path-queries/?{name}/#{value}/action={type}.jsp").getParameters("path-queries/?zhangsan/#student123/action=get@String.jsp")
        Assert.assertEquals(queries[0].value.toString(), "zhangsan")
        Assert.assertEquals(queries[1].value.toString(), "student123")
        Assert.assertEquals(queries[2].value.toString(), "get@String")
    }

}