package com.rubik.apt.namebox

import com.blueprint.kotlin.lang.type.KbpType
import com.ktnail.x.Logger
import com.ktnail.x.NameBox
import com.rubik.apt.codebase.ClassMirrorable
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.contextClassName
import com.rubik.apt.codebase.originalClassName
import com.rubik.apt.utility.bestGuessNameOrNull
import com.rubik.apt.utility.noSpaces
import com.rubik.apt.utility.toRMirrorName
import com.squareup.kotlinpoet.*

class FileNameBox(val builder: FileSpec.Builder, val uri: String) {
    private val numberNameBox = NameBox()
    private val consumedClassNames = mutableMapOf<String, String>()
    private val mappingMatrix = MappingMatrix()

    /*
    * kotlin.collections.Map.Entry ->  Map1.Entry
    * kotlin.collections.Map ->  Map1
    * kotlin.collections.getOrPut -> getOrPut
    */
    fun closeSimpleName(
        name: String,
        asName: String? = null,
        suffix: String? = null,
        addNullableSuffix: Boolean = true
    ): String =
        bestGuessNameOrNull(name)?.let { className ->
            closeSimpleName(
                className,
                asName,
                suffix,
                addNullableSuffix
            )
        } ?: name

    fun closeSimpleName(
        name: TypeName,
        asName: String? = null,
        suffix: String? = null,
        addNullableSuffix: Boolean = true
    ): String =
        when (name) {
            is ClassName -> closeClass(name, asName, suffix, addNullableSuffix)
            is ParameterizedTypeName -> closeClass(name.rawType, asName, suffix, addNullableSuffix).let { simpleName ->
                if (name.typeArguments.isEmpty()) simpleName
                else "$simpleName<${
                    name.typeArguments.joinToString(", ") { argument -> closeSimpleName(argument, asName, suffix, addNullableSuffix) }
                }>".noSpaces() + if (addNullableSuffix && name.isNullable) "?" else ""
            }
            else -> name.toString()
        }

    fun close(
        name: TypeName,
        asName: String? = null,
        suffix: String? = null,
        addNullableSuffix: Boolean = true
    ): TypeName {
        when (name) {
            is ClassName -> closeClass(name, asName, suffix, addNullableSuffix)
            is ParameterizedTypeName -> {
                closeClass(name.rawType, asName, suffix, addNullableSuffix)
                name.typeArguments.map { typeName ->
                    close(typeName, asName, suffix, addNullableSuffix)
                }
            }
            else -> { }
        }
        return name
    }

    /*
     * kotlin.collections.Map.Entry? ->  Map1.Entry?
     */
    private fun closeClass(name: ClassName, asName: String?, suffix: String?, addNullableSuffix: Boolean): String =
        closeTopLevelClass(name.topLevelClassName(), asName, suffix).toMultiLayerSimpleName(name, addNullableSuffix)

    /*
     * kotlin.collections.Map.Entry ->  Map1
     */
    private fun closeTopLevelClass(name: ClassName, asName: String?, suffix: String?): String { // like kotlin.collections.Map
        return consumedClassNames.getOrPut(name.canonicalName) {
            numberNameBox.useName(asName ?: (name.simpleName + suffix.orEmpty()))
                .also { useSimpleName -> // like Map1
                    if (useSimpleName == name.simpleName)
                    //  import kotlin.collections.Map
                        builder.addImport(name.packageName, name.simpleName)
                    else
                    //  import kotlin.collections.Map as Map1
                        builder.addAliasedImport(MemberName(name.packageName, name.simpleName), useSimpleName)
                }
        }
    }

    /*
     *  Map1? ->  Map1.Entry?
     */
    private fun String.toMultiLayerSimpleName(name: ClassName, addNullableSuffix: Boolean): String =
        (if (name.simpleNames.size > 1)
            mutableListOf(this).apply { addAll(name.simpleNames.subList(1, name.simpleNames.size)) }.joinToString(".")
        else this) + if (addNullableSuffix && name.isNullable) "?" else ""

    fun closeClassMirrorable(uri: String, context: ContextCodeBase) {
        context.values.forEach { value ->
            closeMaybeCrashClass(uri, value)
            if (value.createByConstructor) {
                mappingMatrix.register(closeTypeName(value.originalType))
            }
        }
        context.objects.forEach { (_, objekt) ->
            closeMaybeCrashClass(uri, objekt)
            mappingMatrix.register(closeTypeName(objekt.originalType))
        }
        context.callbacks.forEach { callback ->
            closeMaybeCrashClass(uri, callback)
            mappingMatrix.register(closeTypeName(callback.originalType))
        }
    }

    private fun closeMaybeCrashClass(uri: String, mirror: ClassMirrorable) {
        close(mirror.originalClassName, suffix = "Org")
        close(mirror.contextClassName(uri), suffix = "Ctx")
    }

    fun import(packageName: String, vararg names: String) {
        builder.addImport(packageName, *names)
    }

    private fun closeTypeName(kbpType: KbpType) =
        closeSimpleName(kbpType.toTypeName(), addNullableSuffix = false)

    fun closeTypeName(kbpType: KbpType, toOriginal: Boolean) = if (toOriginal)
        closeTypeName(kbpType)
    else
        closeSimpleName(kbpType.toRMirrorName(uri), addNullableSuffix = false)

    fun useMapping(type: KbpType, toOriginal: Boolean): Boolean =
        mappingMatrix.use(closeTypeName(type), toOriginal, type.nullable)

    fun needAddMapping(
        type: KbpType,
        toContext: () -> Unit,
        toOriginal: () -> Unit,
        toContextNullable: () -> Unit,
        toOriginalNullable: () -> Unit
    ) = mappingMatrix.addMappingAction(
        closeTypeName(type),
        toContext,
        toOriginal,
        toContextNullable,
        toOriginalNullable
    )

}

fun FileSpec.Builder.closeInNameBox(uri: String, action: FileSpec.Builder.(FileNameBox) -> Unit) =
    apply {
        closeIn(FileNameBox(this, uri), action)
    }

fun <T> T.closeIn(nameBox: FileNameBox, action: T.(FileNameBox) -> Unit) =
    apply {
        action(nameBox)
    }