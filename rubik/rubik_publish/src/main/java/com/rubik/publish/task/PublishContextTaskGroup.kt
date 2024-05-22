package com.rubik.publish.task

import com.rubik.publish.task.both.ComponentAndContextLibsTask
import com.rubik.publish.task.both.PackingLinkComponentAndContextLibsTask
import com.rubik.publish.task.component.ComponentTask
import com.rubik.publish.task.component.PackingLinkComponentTask
import com.rubik.publish.task.lib.ContextLibTask

class PublishContextTaskGroup {
    var libTasks: PublishTaskPair<PublishContextTask>? = null

    var componentTasks: PublishTaskPair<PublishContextTask>? = null

    private var componentAndContextLibsTasks: PublishTaskPair<PublishContextTask>? = null

    val bothTasks
        get() = componentAndContextLibsTasks ?: componentTasks ?: libTasks

    var packingLinkComponentTasks: PublishTaskPair<PublishContextTask>? = null

    private var packingLinkComponentAndContextLibsTasks: PublishTaskPair<PublishContextTask>? = null

    val packingLinkBothTasks
        get() = packingLinkComponentAndContextLibsTasks ?: packingLinkComponentTasks

    val context get() = bothTasks?.first?.context

    val tags get() = context?.tagNames

    val uri get() = context?.uri

    fun put(tasks: PublishTaskPair<PublishContextTask>) {
        when (tasks.first) {
            is ContextLibTask -> libTasks = tasks
            is ComponentTask -> componentTasks = tasks
            is ComponentAndContextLibsTask -> componentAndContextLibsTasks = tasks
            is PackingLinkComponentTask -> packingLinkComponentTasks = tasks
            is PackingLinkComponentAndContextLibsTask -> packingLinkComponentAndContextLibsTasks = tasks
        }
    }
}