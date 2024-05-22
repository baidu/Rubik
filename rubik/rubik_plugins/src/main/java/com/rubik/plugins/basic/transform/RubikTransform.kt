package com.rubik.plugins.basic.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ktnail.x.Logger
import com.ktnail.x.copyToDir
import com.ktnail.x.findFileRecursively
import java.io.File

abstract class RubikTransform : Transform() {

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun isIncremental(): Boolean = false

    protected fun copyClassesAndJar(
        transformInvocation: TransformInvocation?,
        afterCopyDir: (File) -> Unit,
        afterCopyJar: ((File) -> Unit)? = null
    ) {
        transformInvocation?.outputProvider?.deleteAll()

        transformInvocation?.inputs?.forEachIndexed { index, input ->
            copyJar(transformInvocation, index, input.jarInputs, afterCopyJar)
            copyDir(transformInvocation, input.directoryInputs, afterCopyDir)
        }
    }


    private fun copyDir(
        transformInvocation: TransformInvocation,
        input: Collection<DirectoryInput>,
        afterCopy: ((File) -> Unit)?
    ) {
        input.forEach { dir ->
            transformInvocation.outputProvider.getContentLocation(
                dir.name,
                dir.contentTypes,
                dir.scopes,
                Format.DIRECTORY
            ).let { out ->
                out.deleteRecursively()
                dir.file.copyRecursively(out,true)
                afterCopy?.invoke(out)
            }
        }
    }

    private fun copyJar(
        transformInvocation: TransformInvocation,
        index: Int,
        input: Collection<JarInput>,
        afterCopy: ((File) -> Unit)?
    ) {
        input.forEach { jar ->
            transformInvocation.outputProvider.getContentLocation(
                "${index}_${jar.file.nameWithoutExtension}",
                jar.contentTypes,
                jar.scopes,
                Format.JAR
            ).let { out ->
                out.delete()
                jar.file.copyTo(out, true)
                afterCopy?.invoke(out)
            }
        }
    }
}
