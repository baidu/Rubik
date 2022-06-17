package com.rubik.plugins.module.config.extension

import com.rubik.plugins.module.config.record.ProjectConfigRecord
import com.rubik.plugins.module.config.record.ConfigRecords
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

/**
 *  Rubik modules extension of gradle plugins.
 *
 *  @since 1.8
 */
open class ModulesExtension(val project: Project) {

    val extensionRecords: ConfigRecords = mutableMapOf()

    fun path(value: String, closure: Closure<PathExtension>) {
        ProjectConfigRecord(false).let { projectRecord->
            PathExtension(projectRecord).apply {
                ConfigureUtil.configure(closure, this)
            }
            extensionRecords[value] = projectRecord
        }
    }

}