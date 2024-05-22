package com.rubik.plugins.shell.checker.router

import com.android.build.api.transform.TransformInvocation
import com.ktnail.x.Logger
import com.rubik.plugins.basic.LogTags
import com.ktnail.gradle.*
import com.rubik.plugins.shell.checker.CheckClassesTransform
import com.rubik.plugins.shell.checker.CheckClassesRule
import org.gradle.api.Project

class CheckRouterVersionTransform(
    private val project: Project
) : CheckClassesTransform(project, RULES) {

    companion object {
        val RULES = arrayOf<CheckClassesRule>(CheckLowerThan1Dot9Rule())
    }

    override fun onTransform(transformInvocation: TransformInvocation?) {
        Logger.p(LogTags.CHECK_CLASS, project) { " CheckRouterVersion TRANSFORM START " }
    }
}
