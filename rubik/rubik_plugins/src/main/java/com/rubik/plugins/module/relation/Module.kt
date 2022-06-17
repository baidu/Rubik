package com.rubik.plugins.module.relation

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.*
import com.rubik.plugins.basic.exception.module.RubikModuleMavenGroupNotSetException
import com.rubik.plugins.basic.publish.maven.Publication
import com.rubik.plugins.basic.utility.*
import com.rubik.plugins.context.model.Lib.Companion.versionToDevVersion
import com.rubik.plugins.module.config.ConfigPool
import com.rubik.plugins.module.token.GitToken
import org.gradle.api.Project
import java.io.File

 open class Module(
    val project: Project,
    val variant: BaseVariant
)  {
     companion object{
         operator fun invoke(
             project: Project,
             variant: BaseVariant,
             configs: ConfigPool
         ) = variant.outputAar.let { outputAar ->
             if (project.isAndroidApplication() || null == outputAar)
                 Module(project, variant)
             else
                 PubModule(project, variant, configs, outputAar)
         }
     }

     private val linkedModules = mutableSetOf<Module>()

     fun linkTo(module: Module): Boolean = linkedModules.contains(module).also { linked ->
        if(!linked)  this.linkedModules.add(module)
     }

     override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Module
        if (project.path != other.project.path) return false
        if (variant.name != other.variant.name) return false
        return true
    }

    override fun hashCode(): Int {
        var result = project.path.hashCode()
        result = 31 * result + variant.name.hashCode()
        return result
    }

}

class PubModule(
    project: Project,
    variant: BaseVariant,
    val configs: ConfigPool,
    outputAar: File
) : Module(project, variant) {

    val publishArtifactName
        get() = project.snackPath

    val pub = Pub(project, variant, outputAar)

    val depModule: MutableSet<Module> = mutableSetOf()

    fun addPubDep(module: Module) {
        depModule.add(module)
    }

    inner class Pub(
        val project: Project,
        val variant: BaseVariant,
        override val publicationFile: File
    ) : Publication {
        private val gitTokenMode = false // todo
        override val publicationName = toPascal(publishArtifactName, "module", variant.name.camelToPascal())
        override val devPublicationName = toPascal(publishArtifactName, "dev", "module", variant.name.camelToPascal())
        override val artifactId = toSnake(false, publishArtifactName, "module", variant.name.pascalToSnake(false)).replace("_", "-")
        override val groupId
            get() = project.propertyOr(Ext.MODULES_SNAPSHOT_MAVEN_DEFAULT_GROUP_ID) { throw RubikModuleMavenGroupNotSetException() }
        override val version: String
            get() = if (gitTokenMode) GitToken.token else timeStamp
        override val devVersion
            get() = version.versionToDevVersion()
        override val source: Publication? = null
        override val addDependencyTypes = arrayOf(
            DependencyType.API,
            DependencyType.IMPLEMENTATION,
            variant.name.flavorDependencyType(DependencyType.API),
            variant.name.flavorDependencyType(DependencyType.IMPLEMENTATION)
        )
        override val addDependencies: Array<Publication>
            get() = depModule.mapNotNull { dep -> (dep as? PubModule)?.pub }.toTypedArray()

        private var _timeStamp: String? = null
        private val timeStamp
            get() =
                _timeStamp ?: (now() formatBy "yyyy-MM-dd-HH-mm-ss-SSS").apply { _timeStamp = this }

        val storeToken
            get() = if (project.localMode) devVersion else version

        fun tmpDirByVersion(version: String) = File(
            project.libTmpDirRoot.absolutePath + File.separator +
                    "module_aar" + File.separator +
                    artifactId + File.separator +
                    version
        )

        private val tmpDir
            get() = tmpDirByVersion(storeToken).apply {
                mkdirs()
            }

        val tmpDirFile
            get() = File(
                tmpDir.absolutePath + File.separator + publicationFile.name
            )

    }
}

val Project.localMode
    get() = rootProject.propertyOr(Ext.MODULES_SNAPSHOT_LOCAL_MODE, true)

val Project.localNoMavenMode
    get() = rootProject.propertyOr(Ext.MODULES_SNAPSHOT_LOCAL_NO_MAVEN_MODE, false)
