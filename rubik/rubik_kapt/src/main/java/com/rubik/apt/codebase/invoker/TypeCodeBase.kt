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
package com.rubik.apt.codebase.invoker

import com.blueprint.kotlin.lang.type.KbpType
import com.rubik.annotations.route.RCallback
import com.rubik.annotations.route.RObject
import com.rubik.annotations.route.RValue
import com.rubik.apt.Constants
import com.rubik.apt.codebase.RToken
import com.rubik.apt.codebase.TokenList
import com.rubik.apt.namebox.FileNameBox
import com.rubik.apt.utility.getRMirrorType
import com.rubik.apt.utility.isSupportMappingRMirror
import com.rubik.apt.utility.noSpaces
import com.rubik.apt.utility.toRMirrorName
import com.squareup.kotlinpoet.TypeName

open class TypeCodeBase(
    val originalType: KbpType
) : RToken {
    companion object {
        fun castToTypeCode(name: String, toType: String? = null): String =
            "$name.${Constants.Aggregate.CAST_TO_TYPE_OF_T_FUNCTION_NAME}${if (null != toType) "<$toType>" else ""}()".noSpaces()
    }

    fun toContextTypeName(uri: String): TypeName = originalType.toRMirrorName(uri)

    private val valueType = originalType.getRMirrorType(RValue::class.java)

    private val objectType = originalType.getRMirrorType(RObject::class.java)

    private val callbackType = originalType.getRMirrorType(RCallback::class.java)

    val isRMirror
        get() = null != valueType || null != objectType || null != callbackType

    val nullable
        get() = originalType.nullable

    private fun toTypeOfTCode(
        code: String,
        nameBox: FileNameBox,
        toOriginal: Boolean,
        toTypeName: String? = null,
        castIfNot: Boolean = false
    ): String{
        val startCode = if (originalType.nullable) "$code?" else code
        return  when {
            null != objectType && nameBox.useMapping(objectType, toOriginal) ->
                "$startCode.${Constants.Aggregate.MAP_TO_TYPE_FUNCTION_NAME}(${
                    mappingName(toOriginal, objectType, nameBox)
                })".addComment(toOriginal, nameBox).noSpaces()
            null != callbackType && nameBox.useMapping(callbackType, toOriginal) ->
                "$startCode.${Constants.Aggregate.MAP_TO_TYPE_FUNCTION_NAME}(${
                    mappingName(toOriginal, callbackType, nameBox)
                })".addComment(toOriginal, nameBox).noSpaces()
            null != valueType && nameBox.useMapping(valueType, toOriginal) && originalType.isSupportMappingRMirror() ->
                "$startCode.${Constants.Aggregate.MAP_TO_TYPE_FUNCTION_NAME}(${
                    mappingName(toOriginal, valueType, nameBox)
                })".addComment(toOriginal, nameBox).noSpaces()
            null != valueType ->
                "$startCode.${Constants.Aggregate.TO_TYPE_OF_T_FUNCTION_NAME}${
                    if (null != toTypeName) "<$toTypeName>" else ""
                }()".addComment(toOriginal, nameBox).noSpaces()
            castIfNot -> castToTypeCode(startCode, toTypeName).addComment(null, nameBox)
            else -> code.addComment(null, nameBox)
        }
    }

    private fun String.addComment(toOriginal: Boolean?, nameBox: FileNameBox) = this + when (toOriginal) {
        true -> " /* -> TO ORIGINAL TYPE :[${originalType.toTypeName()}] */ "
        false -> " /* -> TO CONTEXT TYPE :[${originalType.toRMirrorName(nameBox.uri)}] */ "
        null-> " /* -> NO NEED TO MAPPING :[${originalType.toTypeName()}] */ "
    }.noSpaces()

    fun toOriginalCode(
        code: String,
        nameBox: FileNameBox,
        toTypeName: String? = null,
        castIfNot: Boolean = false
    ): String = toTypeOfTCode(code, nameBox, true, toTypeName, castIfNot)

    fun toContextCode(
        code: String,
        nameBox: FileNameBox,
        toTypeName: String? = null,
        castIfNot: Boolean = false
    ): String = toTypeOfTCode(code, nameBox, false, toTypeName, castIfNot)

    private fun mappingName(toOriginal: Boolean, rType: KbpType, nameBox: FileNameBox) =
        Constants.Object.makeToTypeMappingFunctionName(nameBox.closeTypeName(rType, toOriginal), rType.nullable)

    override fun toString(): String = originalType.toString()

    override val tokenList
        get() = TokenList(originalType, key = "T", warp = false)
}