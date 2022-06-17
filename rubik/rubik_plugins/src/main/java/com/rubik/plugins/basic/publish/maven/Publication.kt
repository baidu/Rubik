package com.rubik.plugins.basic.publish.maven

import java.io.File

interface Publication {
    val publicationName: String
    val devPublicationName: String
    val artifactId : String
    val groupId : String
    val version : String
    val devVersion : String
    val publicationFile: File
    val source: Publication?
    val addDependencyTypes: Array<String>
    val addDependencies: Array<Publication>
}