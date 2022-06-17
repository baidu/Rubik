package com.rubik.plugins.basic.task

import com.ktnail.x.Logger
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.utility.p
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider

/**
 * Custom task graphic,
 * In order to monitor the correlation of tasks.
 *
 * @since 1.4
 */
open class TaskGraphic {
    private var graphicActions = mutableSetOf<() -> Unit>()

    fun whenGraph(condition: Boolean, action: () -> Unit) {
        if(condition){
            graphicActions.add(action)
        }
    }

    fun whenGraph(action: () -> Unit) {
        graphicActions.add(action)
    }

    fun graph() {
        graphicActions.forEach { action -> action() }
    }
}

fun Task.graphicFinalizedWith(other: Task){
    Logger.p(LogTags.TASK_GRAPHIC,null) { " GRAPHIC  (${this.toLog()}) ->>FINALIZE>  (${other.toLog()})" }
    finalizedBy(other)
}

fun Task.graphicDependsOn(other: Task){
    Logger.p(LogTags.TASK_GRAPHIC,null) { " GRAPHIC  (${other.toLog()}) <DEPEND<<-  (${this.toLog()})" }
    dependsOn(other)
}

fun Task.graphicDependsOn(other: TaskProvider<Task>){
    Logger.p(LogTags.TASK_GRAPHIC,null) { " GRAPHIC  (${other.toLog()}) <DEPEND<<-  (${this.toLog()})" }
    dependsOn(other)
}

fun TaskProvider<Task>.graphicDependsOn(other: Task) {
    Logger.p(LogTags.TASK_GRAPHIC, null) { " GRAPHIC  (${other.toLog()}) <DEPEND<<-  (${this.toLog()})" }
    get().dependsOn(other)
}

fun Task.graphicFinalizedWith(other: TaskProvider<Task>) {
    Logger.p(LogTags.TASK_GRAPHIC,null) { " GRAPHIC  (${this.toLog()}) ->>FINALIZE>  (${other.toLog()})" }
    finalizedBy(other)
}

fun TaskProvider<Task>.graphicFinalizedWith(other: Task) {
    Logger.p(LogTags.TASK_GRAPHIC,null) { " GRAPHIC  (${this.toLog()}) ->>FINALIZE>  (${other.toLog()})" }
    get().finalizedBy(other)
}

fun TaskProvider<Task>.graphicFinalizedWith(other: TaskProvider<Task>) {
    Logger.p(LogTags.TASK_GRAPHIC,null) { " GRAPHIC  (${this.toLog()}) ->>FINALIZE>  (${other.toLog()})" }
    get().finalizedBy(other)
}

fun Task.doLog(msg: String = "") = apply {
    doFirst {
        Logger.p(LogTags.TASK_GRAPHIC,null) { " TASK START (${this.toLog()}) msg:$msg" }
    }
    doLast {
        Logger.p(LogTags.TASK_GRAPHIC,null) { " TASK FINISH (${this.toLog()}) msg:$msg" }
    }
}

fun Task.toLog() = "[${this.name}(${this::class.java.simpleName})]"

private fun TaskProvider<Task>.toLog() = "[${this.name}(${this::class.java.simpleName})]"