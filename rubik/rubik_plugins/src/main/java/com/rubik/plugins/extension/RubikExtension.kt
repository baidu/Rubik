package com.rubik.plugins.extension

import com.ktnail.gradle.isGradleRoot
import com.rubik.context.extra.Context
import com.rubik.dsl.DSLRubik
import com.rubik.pick.Excepted
import com.rubik.pick.FlavorHows
import com.rubik.pick.PickWhat
import com.rubik.plugins.basic.exception.RubikPackingNotSupportException
import com.rubik.plugins.basic.extra.rubikSingleton
import com.rubik.dsl.global.GlobalExtension
import com.rubik.context.extra.contextsContainer
import com.rubik.picker.extra.pickedContextsContainer
import com.rubik.picker.extra.sourcePickHow
import com.rubik.publish.extension.ContextRecordExtension
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil
import java.io.File

/**
 *  Rubik extension of gradle plugins.
 *
 *  @since 1.3
 */
open class RubikExtension(project: Project) : DSLRubik(project) {
    // config
    private val configExtension
        get() = project.rubikSingleton

    fun global(closure: Closure<GlobalExtension>) {
        if (!project.isGradleRoot) throw RubikPackingNotSupportException(project.path)
        ConfigureUtil.configure(closure, configExtension.globalExtension)
    }

    // publication_record
    fun publications(uri: String, closure: Closure<ContextRecordExtension>) {
        configExtension.publicationRecords[uri] =
            ConfigureUtil.configure(closure, ContextRecordExtension(project)).records
    }

    // aggregate set
    private val aggregateSetChangedListeners = mutableListOf<(String) -> Unit>()

    open var aggregateSet: String =
        "${project.projectDir}${File.separator}src${File.separator}main${File.separator}aggregate"
        set(value) {
            field = value
            aggregateSetChangedListeners.forEach { action ->
                action(value)
            }
        }

    fun listenAggregateSetChanged(action: (String) -> Unit) {
        action(aggregateSet)
        aggregateSetChangedListeners.add(action)
    }

    override fun onDefineContext(context: Context, hows: FlavorHows?) {
        if (!project.isGradleRoot)
            context.updateProjectPathIfNull(project.path)

        contextsContainer.register(context)

        context.sourcePickHow = hows
    }

    override fun onDefinePacking(what: PickWhat, hows: FlavorHows?) {
        if (!project.isGradleRoot) throw RubikPackingNotSupportException(project.path)
        pickedContextsContainer.register(what, hows)
    }

    override fun onDefineExcept(what: Excepted) {
        pickedContextsContainer.unRegister(what)
    }
}