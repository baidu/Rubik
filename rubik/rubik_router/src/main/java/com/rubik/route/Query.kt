package com.rubik.route

/**
 * The Query of rubik router.
 * Provide the mapping of original data type.
 *
 * @since 1.0
 */
class Query(
    val name: String,
    val value: Any?,
    val type: QueryType = QueryType.ANY
)

enum class QueryType {
    ANY,
    PARCELABLE,
    SERIALIZABLE,
    VALUE
}

