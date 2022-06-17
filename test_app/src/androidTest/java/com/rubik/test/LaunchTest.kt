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

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.rubik.activity.Launcher
import com.rubik.route.LaunchQueries
import com.rubik.route.Query
import com.rubik.router.navigate
import com.rubik.test.router.TestAggregate
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LaunchTest {

    @get:Rule
    var activityRule = ActivityTestRule(TestActivity::class.java)

    @Before
    fun init(){
        TestAggregate.register()
    }

    @Test
    fun useLaunchQueries() {
        activityRule.activity.navigate {
            uri = "test://agg.test/activity/launch"
            query {
                "p1" with 1
            }
        }
    }

    @Test
    fun useLaunch(){
        Launcher().launch(
            TestActivity::class.java,
            LaunchQueries().apply {
                context = activityRule.activity.applicationContext
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            },
            null,
            null
        )
    }

    @Test
    fun useLaunchQuery(){
        Launcher().launch(
            TestActivity::class.java,
            LaunchQueries().apply {
                context = activityRule.activity.applicationContext
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                add(Query("from", "useLaunchQuery"))
            },
            null,
            null
        )
    }

    @Test
    fun useLaunchPathQuery(){
        Launcher().launch(
            TestActivity::class.java,
            LaunchQueries().apply {
                context = activityRule.activity.applicationContext
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            },
            listOf(Query("from", "useLaunchPathQuery")),
            null
        )
    }
}