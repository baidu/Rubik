package com.rubik.plugins.shell.checker.context

import com.rubik.plugins.shell.checker.CheckClassesRule
import javassist.CtClass
import org.gradle.api.Project

class CheckContextVersionRule : CheckClassesRule {
    override fun doCheck(project: Project, ctClass: CtClass){
        // todo 实现规则
    }
}