package com.rubik.context.extra

import com.ktnail.x.uri.toSchemeAndAuthority
import com.rubik.context.Dependency
import com.rubik.context.Source
import com.rubik.context.id.ContextIdHolder
import org.gradle.api.Project

data class Context(
    val project: Project,
    val enableProvideRoute: Boolean,
    val enablePublishComponent: Boolean,
    val uri: String,
    val touching: List<Dependency>,
    val packing: List<Dependency>,
    val source: Source,
    val tags: Map<String, Source?>
) {
    val scheme: String
    val name: String
    val group: String

    init {
        uri.toSchemeAndAuthority().let { (scheme, authority) ->
            this.scheme = scheme
            this.group = authority.substringBeforeLast(".")
            this.name = authority.substringAfterLast(".")
        }
    }

    // packingLink
    var packingWho: List<Context> = emptyList()

    var whoPackingMe: List<Context> = emptyList()

    // source
    val projectPath
        get() = source.projectPath

    fun updateProjectPathIfNull(project: String) {
        if (null == source.projectPath)
            source.projectPath = project
    }

    // tags
    val tagNames
        get() = tags.keys

    // id
    val id: ContextIdHolder = ContextIdHolder(this)

}