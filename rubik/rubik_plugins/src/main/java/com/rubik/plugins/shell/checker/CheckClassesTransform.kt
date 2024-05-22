package com.rubik.plugins.shell.checker

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ktnail.x.toPascal
import com.rubik.plugins.basic.transform.RubikTransform
import com.rubik.plugins.basic.utility.findCtClassesInJar
import javassist.ClassPool
import org.gradle.api.Project
import java.io.File

abstract class CheckClassesTransform(
    private val project: Project,
    private val rules :Array<CheckClassesRule>
) : RubikTransform() {

    override fun getName() = toPascal("rubik","check","router","version")

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.SCOPE_FULL_PROJECT

    abstract fun onTransform(transformInvocation: TransformInvocation?)

    override fun transform(transformInvocation: TransformInvocation?) {

        onTransform(transformInvocation)

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
        rules.forEach { rule ->
            jars.forEach { jar->
                jar.findCtClassesInJar(classPool) { ctClass ->
                    rule.doCheck(project, ctClass)
                }
            }
        }
    }

}
