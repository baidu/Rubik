package com.rubik.plugins.shell.checker.router

import com.ktnail.x.Logger
import com.rubik.annotations.source.RContextLib
import com.rubik.annotations.source.RGenerated
import com.rubik.plugins.basic.Constants
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.exception.RubikRouterAndContextVersionsNotMatchException
import com.rubik.context.Ext
import com.ktnail.gradle.*
import com.rubik.plugins.shell.checker.CheckClassesRule
import javassist.CtClass
import org.gradle.api.Project

class CheckLowerThan1Dot9Rule : CheckClassesRule {
    override fun doCheck(project: Project, ctClass: CtClass){
        if (ctClass.packageName.startsWith("${Constants.Router.GENERATE_BASIC_PACKAGE_NAME}.context")){
            val routerVersion = project.propertyOr(Ext.RUBIK_ROUTER_VERSION) { "not-set" }
            val kaptVersion = project.propertyOr(Ext.RUBIK_KAPT_VERSION) { "not-set" }
            (ctClass.getAnnotation(RGenerated::class.java) as? RGenerated)?.let { generated ->
                if (generated.kind == "context") {
                    val version = generated.by.removePrefix("rubik-kapt:").split(".")
                    Logger.p(LogTags.CHECK_CLASS, project) { " find RGenerated context class:${ctClass.name} by:${generated.by}" }
                    if (version[0].toIntOrNull() ?: -1 <= 1 && version[1].toIntOrNull() ?: -1 < 9) {
                        val uri = (ctClass.getAnnotation(RContextLib::class.java) as? RContextLib)?.uri
                        throw RubikRouterAndContextVersionsNotMatchException(generated.by, uri,
                            routerVersion,"please re-publish context by [${kaptVersion}] or higher version!")
                    }
                }
            }
        }
    }
}