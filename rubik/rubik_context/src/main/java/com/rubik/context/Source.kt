package com.rubik.context

import com.ktnail.x.command.Command

data class Source(
    var projectPath: String?,
    val publishVersion: String?,
    val publishOriginalValue: Boolean,
    val allowAutoVersion: Boolean,
    val maven: MavenSource,
    val codeSyncer: (() -> Command?)?
)