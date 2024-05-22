package com.rubik.publish.task.component

import com.ktnail.gradle.task.TaskGraphic
import com.rubik.publish.task.PublishContextTask

/**
 * Task graphic of business code of contexts,
 * graph publish tasks.
 *
 * @since 1.9
 */
class ComponentTaskGraphic(
    componentTask: ComponentTask
) : TaskGraphic() {
    init {
        whenGraph {
            componentTask.variantTasks.fold<PublishContextTask,PublishContextTask>(componentTask) { acc, task ->
                task.apply {
                    acc.linkExecuteDependsOn(this)
                }
            }
        }
    }
}