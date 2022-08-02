package com.rubik.plugins.context.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ktnail.x.Logger
import com.ktnail.x.camelToPascal
import com.ktnail.x.copyToDir
import com.rubik.annotations.source.RContextLib
import com.rubik.annotations.source.ROriginalValue
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.transform.RubikTransform
import com.rubik.plugins.basic.utility.cleanLibClassesTmpDir
import com.rubik.plugins.basic.utility.forEachCtClasses
import com.rubik.plugins.basic.utility.getLibClassesTmpDir
import com.rubik.plugins.basic.utility.p
import com.rubik.plugins.context.model.Lib
import com.rubik.plugins.extension.context.ContextExtension
import javassist.ClassPool
import org.gradle.api.Project
import java.io.File

class MakeLibsTransform(
    private val project: Project,
    private val context: ContextExtension
) : RubikTransform() {

    override fun getName() = "rubik${context.publishLibArtifactId(Lib.Type.CONTEXT).camelToPascal()}"

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.PROJECT_ONLY

    override fun transform(transformInvocation: TransformInvocation?) {

        val variantName = transformInvocation?.context?.variantName

        Logger.p(LogTags.TRANSFORM_CLASSES, project) { " TRANSFORM START  variant:$variantName" }

        variantName?.let {
            cleanLibClassesTmpDir(project, Lib.Type.CONTEXT, variantName, context.uri)
            cleanLibClassesTmpDir(project, Lib.Type.ORIGINAL_VALUE, variantName, context.uri)
        }
        ClassPool.getDefault()?.let { classPool ->
            copyClassesAndJar(transformInvocation, { file ->
                classPool.insertClassPath(file.absolutePath)
                variantName?.let {
                    filterAndCopyLibClasses(
                        classPool,
                        project,
                        variantName,
                        file
                    )
                }
            })
        }
    }

    private fun filterAndCopyLibClasses(
        classPool:ClassPool,
        project: Project,
        variantName: String,
        classDir: File
    ){

        classDir.forEachCtClasses(classPool) { ctClass, classFiles ->
            (ctClass.getAnnotation(RContextLib::class.java) as? RContextLib)?.let {
                classFiles.forEach { file ->
                    file.copyToDir(
                        getLibClassesTmpDir(project, Lib.Type.CONTEXT, variantName, context.uri), classDir.absolutePath
                    )
                }
            }
            (ctClass.getAnnotation(ROriginalValue::class.java) as? ROriginalValue)?.let {
                classFiles.forEach { file ->
                    if (context.publishOriginalValue) {
                        file.copyToDir(
                            getLibClassesTmpDir(project, Lib.Type.ORIGINAL_VALUE, variantName, context.uri), classDir.absolutePath
                        )
                    }
                }
            }
        }

    }

}
