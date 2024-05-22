package com.rubik.context.folder

import com.ktnail.x.uriToSnake
import java.io.File

fun getLibsTmpDirPath(libTmpDirRoot: File, uri: String, typeName: String) =
    "${libTmpDirRoot.absolutePath}${File.separator}${uri.uriToSnake()}${File.separator}$typeName"

fun getLibsTmpDir(libTmpDirRoot: File, uri: String, typeName: String) =
    File(
        getLibsTmpDirPath(
            libTmpDirRoot,
            uri,
            typeName
        )
    )

fun cleanLibsTmpDir(libTmpDirRoot: File, uri: String, typeName: String) =
    getLibsTmpDir(
        libTmpDirRoot,
        uri,
        typeName
    ).apply {
        deleteRecursively()
        mkdirs()
    }

