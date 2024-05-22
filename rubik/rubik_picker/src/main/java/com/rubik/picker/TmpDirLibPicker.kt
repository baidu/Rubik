package com.rubik.picker

import com.ktnail.gradle.addDirDependency
import com.rubik.context.extra.Context
import com.rubik.context.extra.libTmpDirRoot
import com.rubik.pick.PickHow
import com.rubik.pick.PickWhat
import com.rubik.pick.What
import com.rubik.context.folder.getLibsTmpDir
import org.gradle.api.Project

class TmpDirLibPicker(
    override val what: PickWhat,
    override val how: PickHow?,
    override val context: Context
): Picker {
    override fun pick(forProject: Project, forFlavor: String?) {
        what.items.filter { what -> what.condition(context) }.forEach { item ->
            if (item is What.Lib) {
                doPick(item, forProject, context)
            }
        }
    }

    private fun doPick(
        lib: What.Lib, forProject: Project, context: Context
    ) {
        forProject.addDirDependency(
            lib.dependencyType, getLibsTmpDir(forProject.libTmpDirRoot, context.uri, lib.type)
        )
    }

}