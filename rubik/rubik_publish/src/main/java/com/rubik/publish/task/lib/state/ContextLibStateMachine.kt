package com.rubik.publish.task.lib.state

import com.ktnail.x.Logger
import com.ktnail.gradle.*
import com.ktnail.gradle.task.TaskStateMachine
import com.rubik.context.Ext
import com.rubik.publish.exception.RubikTaskStateError
import org.gradle.api.Project

class ContextLibStateMachine(private val project: Project) : TaskStateMachine {
    object States {
        const val READY = "READY"
        const val ASSEMBLE_FINISH = "ASSEMBLE_FINISH"
        const val PUBLISHING = "PUBLISHING"
        const val PUBLISH_FINISH = "PUBLISH_FINISH"

        object ByCompiler {
            const val ANNOTATION_PROCESSING = "StateByCompiler_ANNOTATION_PROCESSING"
            const val MOVING_SOURCE = "StateByCompiler_MOVING_SOURCE"
            const val COMPILING = "StateByCompiler_COMPILING"
            const val PACKING_JAR = "StateByCompiler_PACKING_JAR"
        }

        object ByTransform {
            const val ASSEMBLE_AND_TRANSFORM = "StateByTransform_ASSEMBLE_AND_TRANSFORM"
            const val PACKING_JAR = "StateByTransform_PACKING_JAR"
        }
    }

    private var state = States.READY

    override fun turnToNextState(nextState: String) {
        if (project.propertyOr(Ext.RUBIK_CHECK_CONTEXT_LIB_TASKS_STATE, true) && !checkNextState(nextState)) {
            throw RubikTaskStateError(state, nextState)
        } else {
            Logger.p(LogTags.TASK_GRAPHIC, null) {
                " CONTEXT_LIB TASK SWITCH from STATE:$state to STATE:$nextState !"
            }
            state = nextState
        }
    }

   private fun checkNextState(nextState: String) :Boolean{
        return when (state) {
            States.READY -> {
                nextState in arrayOf(
                    States.ByCompiler.ANNOTATION_PROCESSING,
                    States.ByTransform.ASSEMBLE_AND_TRANSFORM,
                    States.ByCompiler.MOVING_SOURCE  // FROM-CACHE
                )
            }

            States.ByCompiler.ANNOTATION_PROCESSING -> {
                nextState == States.ByCompiler.MOVING_SOURCE
            }
            States.ByCompiler.MOVING_SOURCE -> {
                nextState in arrayOf(
                    States.ByCompiler.COMPILING,
                    States.ByCompiler.PACKING_JAR,  // FROM-CACHE
                    States.ASSEMBLE_FINISH // UP-TO-DATE
                )

            }
            States.ByCompiler.COMPILING -> {
                nextState in arrayOf(
                    States.ByCompiler.PACKING_JAR,
                    States.ASSEMBLE_FINISH // UP-TO-DATE
                )
            }
            States.ByCompiler.PACKING_JAR -> {
                nextState == States.ASSEMBLE_FINISH
            }


            States.ByTransform.ASSEMBLE_AND_TRANSFORM -> {
                nextState in arrayOf(
                    States.ByTransform.PACKING_JAR,
                    States.ASSEMBLE_FINISH // UP-TO-DATE
                )
            }
            States.ByTransform.PACKING_JAR -> {
                nextState == States.ASSEMBLE_FINISH
            }

            States.ASSEMBLE_FINISH -> {
                nextState == States.PUBLISHING
            }
            States.PUBLISHING -> {
                nextState == States.PUBLISH_FINISH
            }
            else -> false
        }
    }
}