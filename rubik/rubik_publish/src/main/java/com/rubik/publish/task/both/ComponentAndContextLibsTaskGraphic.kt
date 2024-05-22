package com.rubik.publish.task.both

import com.ktnail.gradle.task.TaskGraphic

/**
 * Task graphic of business code of contexts,
 * graph publish tasks.
 *
 * @since 1.9
 */
class ComponentAndContextLibsTaskGraphic(
    tasks: ComponentAndContextLibsTask
) : TaskGraphic() {
    init {
        whenGraph {
            tasks.componentTask.variantTasks.lastOrNull()?.linkExecuteDependsOn(tasks.libTask)
            tasks.linkExecuteDependsOn(tasks.componentTask)
            tasks.linkExecuteDependsOn(tasks.libTask)
        }
    }
}