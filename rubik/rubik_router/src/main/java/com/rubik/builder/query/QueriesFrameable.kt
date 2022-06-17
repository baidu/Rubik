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
package com.rubik.builder.query

import android.app.Activity
import android.content.Context
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.rubik.route.Queries

interface QueriesBuildable {
    fun buildQueries(): Queries
}

interface QueriesFrameable<T> {
    infix fun String.with(value: Any?): T
}

interface BundleQueriesFrameable<T> : QueriesFrameable<T> {
    infix fun String.withValue(value: Any?): T
    infix fun String.withSerializable(value: Any?): T
    infix fun String.withParcelable(value: Parcelable?): T
    infix fun String.withParcelable(value: Array<out Parcelable>?): T
    infix fun String.withParcelable(value: ArrayList<out Parcelable>?): T
}

interface DSLLaunchQueriesFrameable : BundleQueriesFrameable<Unit> {
    var requestCode: Int?
    var flags: Int?
}

interface LinkedLaunchQueriesFrameable<T> : BundleQueriesFrameable<T> {
    fun launchBy(context: Context): T
    fun launchBy(activity: Activity): T
    fun launchBy(fragment: Fragment): T
    fun launchRequestCode(requestCode: Int): T
    fun launchFlags(flags: Int): T
}