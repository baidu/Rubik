package com.rubik.plugins.root

import com.rubik.plugins.RubikPlugin
import com.rubik.plugins.basic.exception.RubikPluginNotApplyException
import com.rubik.plugins.basic.exception.RubikTestMavenVersionNotSetException
import com.rubik.plugins.basic.exception.RubikTestNotRubikRootException
import com.rubik.plugins.basic.utility.addRubikRouterDependency
import com.rubik.plugins.basic.utility.firstPriorityMavenVersion
import com.rubik.plugins.basic.utility.isRubikRoot
import com.rubik.plugins.context.model.Lib
import com.rubik.plugins.root.pick.ContextPicker
import org.gradle.api.Project

/**
 *  The the test plugin of rubik.
 *  Provide dependencies on all context which RootPlugin picked,
 *  in order to test all contexts.
 *
 *  @see com.rubik.plugins.root.RootPlugin
 *
 *  @since 1.6
 */
class TestPlugin : RubikPlugin() {
    private var _picker: ContextPicker? = null
    private val picker: ContextPicker
        get() = _picker ?: throw RubikPluginNotApplyException()

    override fun apply(project: Project) {
        super.apply(project)
        if(!project.isRubikRoot) throw RubikTestNotRubikRootException(
            project.name
        )
        _picker = ContextPicker(project)

        project.afterEvaluate {
            picker.pick { picked, context, tagSource ->
                if(context.enableProvideRoute){
                    val version =
                        context.firstPriorityMavenVersion(picked, tagSource) ?: throw RubikTestMavenVersionNotSetException(context.uri)
                    if (null != picked.forFlavor){
                        addAndroidTestImplementationDependency(picked.forFlavor, context.publishGroupId, context.publishLibArtifactId(Lib.Type.CONTEXT), version)
                        if(context.publishOriginalValue){
                            addAndroidTestCompileOnlyDependency(picked.forFlavor, context.publishGroupId, context.publishLibArtifactId(Lib.Type.ORIGINAL_VALUE), version)
                        }
                    }else{
                        addAndroidTestImplementationDependency(context.publishGroupId, context.publishLibArtifactId(Lib.Type.CONTEXT), version)
                        if(context.publishOriginalValue){
                            addAndroidTestCompileOnlyDependency(context.publishGroupId, context.publishLibArtifactId(Lib.Type.ORIGINAL_VALUE), version)
                        }
                    }
                }
            }
            project.addRubikRouterDependency()
        }
    }

}

