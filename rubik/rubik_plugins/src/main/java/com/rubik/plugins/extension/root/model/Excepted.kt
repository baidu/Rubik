package com.rubik.plugins.extension.root.model


class Excepted(
    val forFlavor: String?,
    val who: PickWho
) {
    override fun toString() = "Excepted forFlavor:${forFlavor} who:${who}"
}