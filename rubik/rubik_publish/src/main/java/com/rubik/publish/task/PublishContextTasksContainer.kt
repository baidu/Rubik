package com.rubik.publish.task

class PublishContextTasksContainer {
    // lib & component tasks
    private val allTasks = mutableMapOf<String, PublishContextTaskGroup>()
    private val tasksListeners = mutableMapOf<String?, MutableList<(PublishContextTaskGroup) -> Unit>>()

    fun registerContextTasks(
        taskGroups: Map<String, PublishContextTaskGroup>
    ) {
        taskGroups.forEach { (uri, tasks) ->
            if (!allTasks.containsKey(uri)) {
                allTasks[uri] = tasks
                tasksListeners[null]?.forEach { action ->
                    action(tasks)
                }
                tasksListeners[uri]?.forEach { action ->
                    action(tasks)
                }
                tasksListeners.remove(uri)
            }
        }
    }

    fun listenTasksRegistered(action: (PublishContextTaskGroup) -> Unit) {
        allTasks.forEach { (_, task) ->
            action(task)
        }
        tasksListeners.getOrPut(null){
            mutableListOf()
        }.add(action)
    }

    fun listenTasksRegistered(uris: List<String>, action: (PublishContextTaskGroup) -> Unit) {
        uris.forEach { uri ->
            val task = allTasks[uri]
            if (null != task)
                action(task)
            else
                tasksListeners.getOrPut(uri) {
                    mutableListOf()
                }.add(action)
        }
    }

}