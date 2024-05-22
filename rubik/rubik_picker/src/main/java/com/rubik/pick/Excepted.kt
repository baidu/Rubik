package com.rubik.pick


class Excepted(
    val forFlavor: String?,
    val where: PickWhere
) {
    override fun toString() = "Excepted forFlavor:${forFlavor} who:${where}"
}