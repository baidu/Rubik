package com.rubik.plugins.module.relation

import com.rubik.plugins.basic.publish.maven.PublishingTaskProvider
import org.gradle.api.Project

data class Node(
    val project: Project,
    val byteCodeMode: Boolean,
    val changed: Boolean,
    val depNodes: List<Pair<Node, String>>,
    val modules: MutableMap<String, Module> = mutableMapOf()
) {
    val publishTaskProvider = PublishingTaskProvider(project)
}
