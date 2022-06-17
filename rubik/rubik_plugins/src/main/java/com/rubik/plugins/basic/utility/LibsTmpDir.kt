package com.rubik.plugins.basic.utility

import com.ktnail.x.uriToSnake
import java.io.File

fun getLibsTmpDirPath(libTmpDirRoot: File, uri: String, libType: String) =
    "${libTmpDirRoot.absolutePath}${File.separator}${uri.uriToSnake()}${File.separator}$libType"

fun getLibsTmpDir(libTmpDirRoot: File, uri: String, libType: String) =
    File(
        getLibsTmpDirPath(
            libTmpDirRoot,
            uri,
            libType
        )
    )

fun cleanLibsTmpDir(libTmpDirRoot: File, uri: String, libType: String) =
    getLibsTmpDir(
        libTmpDirRoot,
        uri,
        libType
    ).apply {
        deleteRecursively()
        mkdirs()
    }

