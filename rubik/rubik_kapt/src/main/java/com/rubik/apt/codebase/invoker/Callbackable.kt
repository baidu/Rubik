package com.rubik.apt.codebase.invoker


interface Callbackable {
    val isResult: Boolean
    val transformRMirror: Boolean
    val transformRMirrorResult: Boolean
    val functions: List<InvokeFunctionCodeBase>
}
