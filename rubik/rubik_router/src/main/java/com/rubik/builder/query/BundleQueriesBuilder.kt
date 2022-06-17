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

import android.os.Parcelable
import com.rubik.route.QueryType

open class BundleQueriesBuilder : BundleQueriesFrameable<Unit>, QueriesBuilder() {
    override fun String.withValue(value: Any?) {
        addQuery(this, value, QueryType.VALUE)
    }

    override infix fun String.withSerializable(value: Any?) {
        addQuery(this, value, QueryType.SERIALIZABLE)
    }

    override infix fun String.withParcelable(value: Parcelable?) {
        addQuery(this, value, QueryType.PARCELABLE)
    }

    override infix fun String.withParcelable(value: Array<out Parcelable>?) {
        addQuery(this, value, QueryType.PARCELABLE)
    }

    override infix fun String.withParcelable(value: ArrayList<out Parcelable>?) {
        addQuery(this, value, QueryType.PARCELABLE)
    }
}
