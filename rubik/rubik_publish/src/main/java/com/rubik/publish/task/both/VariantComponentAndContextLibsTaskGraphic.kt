package com.rubik.publish.task.both

import com.ktnail.gradle.task.TaskGraphic

/**
 * Task graphic of business code of contexts,
 * graph publish tasks.
 *
 * @since 1.9
 */
class VariantComponentAndContextLibsTaskGraphic(
    tasks: VariantComponentAndContextLibsTask
) : TaskGraphic() {
    init {
        whenGraph {
            tasks.componentTask.linkExecuteDependsOn(tasks.libTask)
            tasks.linkExecuteDependsOn(tasks.componentTask)
            tasks.linkExecuteDependsOn(tasks.libTask)
        }
    }
}