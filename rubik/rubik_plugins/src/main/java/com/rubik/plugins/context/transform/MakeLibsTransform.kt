package com.rubik.plugins.context.transform

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ktnail.x.Logger
import com.ktnail.x.camelToPascal
import com.ktnail.x.copyToDir
import com.ktnail.x.findFileRecursively
import com.rubik.annotations.source.RContextLib
import com.rubik.annotations.source.ROriginalValue
import com.rubik.plugins.basic.LogTags
import com.rubik.plugins.basic.utility.cleanLibClassesTmpDir
import com.rubik.plugins.basic.utility.forEachCtClasses
import com.rubik.plugins.basic.utility.getLibClassesTmpDir
import com.rubik.plugins.basic.utility.p
import com.rubik.plugins.context.model.Lib
import com.rubik.plugins.extension.context.ContextExtension
import org.gradle.api.Project
import java.io.File

class MakeLibsTransform(
    private val project: Project,
    private val context: ContextExtension
) : Transform() {

    override fun getName() = "rubik${context.publishLibArtifactId(Lib.Type.CONTEXT).camelToPascal()}"

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun isIncremental(): Boolean = false

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.PROJECT_ONLY

    override fun transform(transformInvocation: TransformInvocation?) {

        val variantName = transformInvocation?.context?.variantName
        val outs = mutableListOf<File>()

        Logger.p(LogTags.TRANSFORM_CLASSES, project) { " TRANSFORM START  variant:$variantName" }

        transformInvocation?.inputs?.forEachIndexed { index, input ->
            input.jarInputs.forEach { jar ->
                transformInvocation.outputProvider.getContentLocation(
                    "${index}_${jar.file.nameWithoutExtension}",
                    jar.contentTypes,
                    jar.scopes,
                    Format.JAR
                ).let { out ->
                    out.delete()
                    jar.file.copyTo(out)
                }
            }

            input.directoryInputs.forEach { dir ->
                transformInvocation.outputProvider.getContentLocation(
                    dir.name,
                    dir.contentTypes,
                    dir.scopes,
                    Format.DIRECTORY
                ).let { out ->
                    out.deleteRecursively()
                    dir.file.findFileRecursively {
                        it.copyToDir(out, dir.file.absolutePath)
                    }
                    outs.add(out)
                }
            }
        }

        variantName?.let {
            filterAndCopyLibClasses(
                project,
                variantName,
                outs
            )
        }

    }

    private fun filterAndCopyLibClasses(
        project: Project,
        variantName: String,
        buildDirs: List<File>
    ){
        cleanLibClassesTmpDir(project, Lib.Type.CONTEXT, variantName, context.uri)
        cleanLibClassesTmpDir(project, Lib.Type.ORIGINAL_VALUE, variantName, context.uri)

        buildDirs.forEach { classDir ->
            classDir.forEachCtClasses { ctClass, classFiles ->
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
                            file.copyToDir(getLibClassesTmpDir(project, Lib.Type.ORIGINAL_VALUE, variantName, context.uri), classDir.absolutePath)
                        }
                    }
                }
            }
        }

    }

}
