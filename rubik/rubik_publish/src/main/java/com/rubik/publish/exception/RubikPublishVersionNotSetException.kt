package com.rubik.publish.exception


/**
 * Thrown when publishing but no publish_version set.
 *
 * @since 1.7
 */
internal class RubikPublishVersionNotSetException(val uri: String) : RuntimeException() {
    override fun toString() =
        "RubikPublishVersionNotSetException  component<${uri}> no publish_version set , you " +
                "may be trying to publish component, or your component is auto " +
                "generating Aggregate/ComponentId ! "
}