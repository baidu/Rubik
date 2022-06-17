package com.rubik.plugins.module.relation

import com.android.build.gradle.api.BaseVariant
import com.rubik.plugins.basic.exception.module.RubikModuleProjectNodeNotFoundException
import com.rubik.plugins.basic.utility.forEachVariant
import com.rubik.plugins.basic.utility.isContextLibBuildType
import com.rubik.plugins.basic.utility.match
import com.rubik.plugins.basic.utility.whenExecuted
import com.rubik.plugins.module.config.ConfigPool
import org.gradle.api.Project

class NodePool(rootProject: Project, private val configs: ConfigPool) {

    private val nodes: NodeMap = nodeMapOf()

    fun getNode(project: Project): Node? = nodes[project.path]

    fun addNode(project: Project, byteCodeMode: Boolean, changed: Boolean, depNodes: List<Pair<Node, String>>): Node {
        val byteCodeModeEnable = depNodes.fold(byteCodeMode) { acc, dep ->
            acc && dep.first.byteCodeMode
        }
        val changedEnable = depNodes.fold(changed) { acc, dep ->
            acc || dep.first.changed
        }
        return Node(project, byteCodeModeEnable, changedEnable, depNodes).apply {
            nodes[project.path] = this
        }
    }

    private fun createModuleInPool(
        project: Project,
        variant: BaseVariant
    ): Module =
        (nodes[project.path] ?: throw RubikModuleProjectNodeNotFoundException(project.path)).modules.getOrPut(variant.name) {
            Module(project, variant, configs)
        }

    private fun modules(project: Project, action: (Module) -> Unit) {
        project.whenExecuted {
            forEachVariant { variant ->
                if (!variant.buildType.isContextLibBuildType()) {
                    action(createModuleInPool(project, variant))
                }
            }
        }
    }

    fun matchModules(
        rootVariant: BaseVariant,
        project: Project,
        depProject: Project,
        action: (Module, PubModule) -> Unit
    ) {
        modules(project) { startModel ->
            modules(depProject) { endModule ->
                if (rootVariant.match(startModel.variant) &&
                    rootVariant.match(endModule.variant) &&
                    startModel.variant.match(endModule.variant) &&
                    endModule is PubModule
                ) {
                    action(startModel, endModule)
                }
            }
        }
    }

    fun matchModules(
        rootVariant: BaseVariant,
        depProject: Project,
        action: (PubModule) -> Unit
    ) {
        modules(depProject) { endModule ->
            if (rootVariant.match(endModule.variant) && endModule is PubModule) {
                action(endModule)
            }
        }
    }

    fun changedEnable(project: Project): Boolean {
        return true // todo
    }

}