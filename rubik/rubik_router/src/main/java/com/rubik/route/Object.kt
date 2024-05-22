package com.rubik.route

/**
 * The Object of rubik router.
 * Provide the mapping of original data type.
 *
 * @since 1.10
 */

interface Object

abstract class SubObject(
    val originalObject: Any?
) : Object

