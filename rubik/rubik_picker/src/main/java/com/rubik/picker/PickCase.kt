package com.rubik.picker

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.findFirstOrNull
import com.ktnail.x.firstOrNull
import com.rubik.context.router.RouterRegister
import com.rubik.pick.*


data class PickCase(
    val what: PickWhat
){
    val flavorHows: MutableMap<String?, PickHow> = mutableMapOf()

     fun addPickHows(hows: FlavorHows) {
         flavorHows.putAll(hows)
    }

    fun addDefaultHow(how: PickHow) {
        flavorHows[null] = how
    }

    fun addFlavorHow(flavor: String?, how: PickHow) {
        flavorHows[flavor] = how
    }

    fun getDefaultHow(): PickHow? = flavorHows[null]

    fun getFlavorHow(flavorName: String?): PickHow? = flavorHows[flavorName]

    fun removeFlavor(forFlavor: String?) {
        flavorHows.remove(forFlavor)
    }

    val hasHow: Boolean
        get() = flavorHows.isNotEmpty()

    val defaultPickOnly: Boolean
        get() = flavorHows.defaultPickOnly

    val pickLibOnly
        get() = what.itemsOnlyLib

    val projectMode
        get() = flavorHows.any { (_, how) -> how is ProjectMode}

    val noFlavorNeedMode
        get() = flavorHows.any { (_, how) -> how is ProjectMode || how is TmpDirMode || how is NoSourceMode }

    val mavenMode
        get() = !noFlavorNeedMode

    fun filter(variant: BaseVariant) =
        PickCase(what).also { newCase ->
            flavorHows.forEach{ (flavor, how) ->
                if (flavor == null || flavor in variant.productFlavors.mapNotNull { productFlavor -> productFlavor.name })
                    if (null == flavor) newCase.addDefaultHow(how)
                    else newCase.addFlavorHow(flavor, how)
            }
        }

    fun toLog(): String = "\n   >> WHAT: $what${flavorHows.toLog("   >> HOWS: ")}"

    val register: RouterRegister
        get() = flavorHows.findFirstOrNull { how -> null != how.register }?.register ?: RouterRegister.NEW_INSTANCE

    val firstHow: PickHow?
        get() = flavorHows.firstOrNull()
}