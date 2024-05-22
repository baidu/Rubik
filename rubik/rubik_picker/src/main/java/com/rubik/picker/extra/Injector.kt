package com.rubik.picker.extra

import com.ktnail.gradle.maven.MavenDependency
import com.rubik.context.extra.Context
import com.rubik.pick.FlavorHows
import com.rubik.pick.PickHow
import com.rubik.pick.What
import com.rubik.picker.container.ContextPickHowContainer
import com.rubik.picker.container.PickedContextsContainer
import com.rubik.context.utility.Module
import com.rubik.context.utility.ModuleInjector
import com.rubik.picker.ContextPickHow


interface PickerModuleInjector : ModuleInjector {
    val pickedContextsContainer: PickedContextsContainer
    val pickHowContainer: ContextPickHowContainer

    fun contextToMavenDependency(
        context: Context,
        item: What,
        maven: PickHow,
        dev: Boolean?,
        forFlavor: String?
    ): MavenDependency
}

object PickerModule : Module<PickerModuleInjector>()

val pickedContextsContainer: PickedContextsContainer
    get() = PickerModule.content.pickedContextsContainer

val Context.pickCase: ContextPickHow
    get() = PickerModule.content.pickHowContainer.pickCase(this)

var Context.sourcePickHow: FlavorHows?
    set(value) {
        pickCase.sourcePickHow = value
    }
    get() = pickCase.sourcePickHow

fun Context.toMavenDependency(
    item: What,
    maven: PickHow,
    dev: Boolean?,
    forFlavor: String?
): MavenDependency = PickerModule.content.contextToMavenDependency(this, item, maven, dev, forFlavor)