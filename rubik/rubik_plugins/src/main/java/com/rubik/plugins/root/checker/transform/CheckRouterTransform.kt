package com.rubik.plugins.root.checker.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ktnail.x.Logger
import com.ktnail.x.toPascal
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.transform.RubikTransform
import com.rubik.plugins.basic.utility.findCtClassesInJar
import com.rubik.plugins.basic.utility.p
import com.rubik.plugins.root.checker.CheckRouterRule
import javassist.ClassPool
import org.gradle.api.Project
import java.io.File

class CheckRouterTransform(
    private val project: Project
) : RubikTransform() {

    override fun getName() = toPascal("rubik","check","router","version")

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.SCOPE_FULL_PROJECT

    override fun transform(transformInvocation: TransformInvocation?) {

        Logger.p(LogTags.CHECK_ROUTER_VERSION, project) { " TRANSFORM START " }

        val jars = mutableSetOf<File>()

        ClassPool.getDefault()?.let { classPool ->
            copyClassesAndJar(transformInvocation, { dir ->
                classPool.insertClassPath(dir.absolutePath)
            }, { jar ->
                classPool.insertClassPath(jar.absolutePath)
                jars.add(jar)
            })
            check(jars, classPool)
        }
    }

    private fun check(
        jars: Set<File>,
        classPool: ClassPool
    ) {
        CheckRouterRule.RULES.forEach { rule ->
            jars.forEach { jar->
                jar.findCtClassesInJar(classPool) { ctClass ->
                    rule.doCheck(project, ctClass)
                }
            }
        }
    }

}
