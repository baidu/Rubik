package com.rubik.plugins.extension.root.model


class Picked(
    val forFlavor: String?,
    val who: PickWho,
    val mode : PickMode
) {
    override fun toString() = "mode: ${mode::class.java.simpleName} forFlavor:${forFlavor} who:${who}"
}