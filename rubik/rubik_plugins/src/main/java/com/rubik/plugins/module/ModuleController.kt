package com.rubik.plugins.module

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.Logger
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.utility.*
import com.rubik.plugins.module.config.ConfigPool
import com.rubik.plugins.module.relation.*
import com.rubik.plugins.module.task.ModulePublishTaskGraphic
import com.rubik.plugins.module.task.publishAllTaskName
import com.rubik.plugins.module.task.publishChangedTaskName
import com.rubik.plugins.module.task.publishSourceTaskName
import org.gradle.api.Project

class ModuleController(
    private val rootProject: Project
) {
    companion object {
        const val PUBLISH_TASK_NAME_PREFIX = "publishRubik"
        const val PUBLISH_MODULE_TASK_NAME_SUFFIX = "RModule"

        const val RUBIK_API_CONFIGURATION_NAME = "rubikApi"
        const val RUBIK_IMPLEMENTATION_CONFIGURATION_NAME = "rubikImplementation"
    }

    private val configPool = ConfigPool(rootProject)
    private val nodePool = NodePool(rootProject, configPool)
    private val taskGraphic = ModulePublishTaskGraphic(rootProject)

    fun init() {
        if (!rootProject.propertyOr(Ext.MODULES_SNAPSHOT_ENABLE, false)) return
        initConfigs()
        initRelations {
            configPool.writeModuleConfigs()
            taskGraphic.graph()
        }
    }

    private fun initRelations(complete: () -> Unit) {
        rootProject.gradle.allprojects { project ->
//            if (project != project.rubikConfig) {
//                project.createDependencyType(RUBIK_API_CONFIGURATION_NAME, DependencyType.API)
//                project.createDependencyType(RUBIK_IMPLEMENTATION_CONFIGURATION_NAME, DependencyType.IMPLEMENTATION)
//            }
            project.ifAndroidApplication {
                project.checkDependencyRecursively { rootNode ->
                    Logger.p(LogTags.MODULE_LINK, rootNode.project) { " CHECK DEPENDENCIES  FINISH ！" }
                    rootNode.project.forEachVariant { rootVariant ->
                        rootNode.linkDepNodesRecursively(rootVariant)
                    }
                }
            }
        }

        rootProject.gradle.projectsEvaluated {
            complete()
        }
    }

    private fun Project.checkDependencyRecursively(checked: (Node) -> Unit) {
        val node = nodePool.getNode(this)
        if (null != node) {
            Logger.p(LogTags.MODULE_LINK, this) { " CHECK DEPENDENCIES FROM CACHE" }
            checked(node)
            return
        }
        Logger.p(LogTags.MODULE_LINK, this) { " CHECK DEPENDENCIES  START ！" }
        whenExecuted {
            val depNodes: MutableList<Pair<Node, String>> = mutableListOf()
            findProjectDependencies(DependencyType.API, DependencyType.IMPLEMENTATION).let{ dependencies->
                Logger.p(LogTags.MODULE_LINK, this) { " CHECK DEPENDENCIES SIZE (${dependencies.size}) ！" }
                val finishAction = {
                    Logger.p(LogTags.MODULE_LINK, this) { " CHECK DEPENDENCIES TRY FINISH need:(${dependencies.size}) finish:(${depNodes.size})！" }
                    if(dependencies.size == depNodes.size){
                        checked(nodePool.addNode(this, configPool.byteCodeModeEnable(this), nodePool.changedEnable(this) , depNodes))
                    }
                }
                if(dependencies.isEmpty()){
                    finishAction()
                } else {
                    dependencies.forEach {(type, depProject ) ->
                        val depNode = nodePool.getNode(depProject)
                        if (null != depNode) {
                            depNodes.add(depNode to type)
                            finishAction()
                        }else{
                            depProject.checkDependencyRecursively{ depNodeRecursively->
                                depNodes.add(depNodeRecursively to type)
                                finishAction()
                            }
                        }

                    }
                }
            }

        }
    }

    private fun Node.linkDepNodesRecursively(rootVariant: BaseVariant, rootNode: Node = this) {
        if (project.isAndroidApplication()) { // root tasks
            taskGraphic.addRoot(project, rootVariant, rootVariant.publishAllTaskName)
            taskGraphic.addRoot(project, rootVariant, rootVariant.publishSourceTaskName)
            taskGraphic.addRoot(project, rootVariant, rootVariant.publishChangedTaskName)
        }
        depNodes.forEach { (depNode, type) ->
            val linkType = checkLinkType(rootVariant, depNode)
            project.addRubikRepository()
            Logger.p(LogTags.MODULE_LINK, project) { " LINKING ！linkType:(${linkType}) node:(${project.path}) TO depNode:(${depNode.project.path}) ON rootVariant[${rootVariant.name}]" }
            nodePool.matchModules(rootVariant, project, depNode.project) { module, depModule ->
                Logger.p(LogTags.MODULE_LINK, this.project) { " LINKING ！linkType:(${linkType}) node:(${this.project.path}) TO depModule:(${depModule.project.path}-${depModule.variant.name}) ON rootVariant[${rootVariant.name}]" }
                val linked = module.linkTo(depModule)
                if (!linked) {
                    configPool.addRecord(depModule)
                }
                if (linkType == LinkType.PUBLISH_ME || linkType == LinkType.PUBLISH_OTHER || !linked) {
                    processLink(rootNode, linkType, depNode, type, module, depModule)
                    depNode.linkDepNodesRecursively(rootVariant, rootNode)
                }

            }
        }
    }

    private fun checkLinkType(rootVariant: BaseVariant, depNode: Node): LinkType {
        return if (rootProject.isTaskTarget(rootVariant.publishAllTaskName)) {
            LinkType.PUBLISH_ME
        } else if (rootProject.isTaskTarget(rootVariant.publishSourceTaskName)) {
            if(!depNode.byteCodeMode)
                LinkType.PUBLISH_ME
            else
                LinkType.PUBLISH_OTHER
        } else if (rootProject.isTaskTarget(rootVariant.publishChangedTaskName)) {
            if(depNode.changed)
                LinkType.PUBLISH_ME
            else
                LinkType.PUBLISH_OTHER
        } else if (depNode.byteCodeMode) {
            LinkType.BYTE_CODE
        } else {
            LinkType.PROJECT
        }
    }

    private fun Node.processLink(
        rootNode: Node,
        linkType: LinkType,
        depNode: Node,
        depType: String,
        module: Module,
        depModule: PubModule
    ) {
        when (linkType) {
            LinkType.PROJECT -> {
                project.addProjectDependency(depType, module.variant.name, depModule.project)
            }
            LinkType.BYTE_CODE -> {
                project.addByteCodeDependency(rootNode, depType, module, depModule)
            }
            LinkType.PUBLISH_OTHER -> {
                project.addByteCodeDependency(rootNode, depType, module, depModule)
            }
            LinkType.PUBLISH_ME -> {
                project.addProjectDependency(depType, module.variant.name, depModule.project)
                taskGraphic.addModule(depModule, depNode.publishTaskProvider)
                (module as? PubModule)?.addPubDep(depModule)
            }
        }
        project.removeProjectDependency(depType, depNode.project)
    }

    private fun Project.addByteCodeDependency(
        rootNode: Node,
        type: String,
        module: Module,
        depModule: PubModule
    ) {
        val version = configPool.byteCodeModeVersion(depModule)
        if (version.isNullOrBlank()) {
            addProjectDependency(type, module.variant.name, depModule.project)
        } else {
            if (rootProject.localNoMavenMode) {
                addAarDependency(module, depModule, version, DependencyType.COMPILE_ONLY)
                rootNode.project.addAarDependency(module, depModule, version, DependencyType.RUNTIME_ONLY)
            } else {
                addDependency(type, module.variant.name, depModule.pub.groupId, depModule.pub.artifactId, version)
            }
        }
    }

    private fun Project.addAarDependency(module: Module, depModule: PubModule, version: String, type: String) {
        repositories.flatDir { dir ->
            dir.dirs(depModule.pub.tmpDirByVersion(version))
        }
        addDirDependency(type, module.variant.name, depModule.pub.tmpDirByVersion(version), "*.aar")
    }

    private val String.gradleDependencyType
        get() = when (this) {
            RUBIK_API_CONFIGURATION_NAME -> DependencyType.API
            RUBIK_IMPLEMENTATION_CONFIGURATION_NAME -> DependencyType.IMPLEMENTATION
            else -> DependencyType.IMPLEMENTATION
        }

    private fun initConfigs() {
        configPool.loadFile(rootProject)
    }

    enum class LinkType{
        PUBLISH_ME,
        PUBLISH_OTHER,
        PROJECT,
        BYTE_CODE
    }
}

