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
package com.rubik.plugins.basic.utility

import com.android.SdkConstants
import com.ktnail.x.findFileRecursively
import javassist.ClassPool
import javassist.CtClass
import java.io.File
import java.util.jar.JarFile

fun File.forEachCtClasses(classPool: ClassPool, action: (CtClass, List<File>) -> Unit) {
    if (this.isDirectory && exists()) {
        classPool.insertClassPath(absolutePath)
        findFileRecursively { file ->
            file.absolutePath.removePrefix(absolutePath).filePathToClassName()?.let { className ->
                try {
                    action(classPool.getCtClass(className), file.findInnerClassFiles(true))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

fun File.findCtClassesInJar(classPool: ClassPool, action: (CtClass) -> Unit) {
    if (!this.isDirectory && exists()) {
        val jar = JarFile(this)
        val classes = jar.entries()
        classes.asIterator().forEach { entry ->
            if (entry.name.endsWith(SdkConstants.DOT_CLASS)) {
                val className = entry.name.filePathToClassName()
                try {
                    action(classPool.getCtClass(className))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

fun File.findInnerClassFiles(containsThis: Boolean = true) =
    (parentFile?.listFiles { file ->
        file.absolutePath.startsWith(absolutePath.substringBeforeLast('.') + "$")
    }?.toMutableList() ?: mutableListOf()).also { list ->
        if (containsThis) list.add(this)
    }

fun String.filePathToClassName() =
    if (endsWith(SdkConstants.DOT_CLASS, ignoreCase = true))
        substringBefore(".")
            .removePrefix(File.separator)
            .replace(File.separatorChar, '.')
//                        .replace('$', '.')
    else null