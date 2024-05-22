package com.rubik.plugins.shell.checker

import javassist.CtClass
import org.gradle.api.Project

interface CheckClassesRule {
    fun doCheck(project: Project, ctClass: CtClass)
}