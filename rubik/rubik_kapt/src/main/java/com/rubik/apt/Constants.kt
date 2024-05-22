package com.rubik.apt

import com.ktnail.x.*
import com.rubik.apt.utility.noSpaces

object Constants {

    const val ANDROIDX_PACKAGE_NAME = "androidx"

    object Router {
        const val PACKAGE_NAME = "com.rubik"
        const val CHECK_ROUTER_VERSION = "900"
    }

    object Annotations {
        const val R_EVENT = "${Router.PACKAGE_NAME}.annotations.context.REvent"
        const val R_EVENT_REPEATABLE = "${Router.PACKAGE_NAME}.annotations.context.REventRepeatable"
        const val R_EVENT_INSTANCE = "${Router.PACKAGE_NAME}.annotations.context.instance.REventInstance"
        const val R_EVENT_INSTANCE_REPEATABLE = "${Router.PACKAGE_NAME}.annotations.context.instance.REventInstanceRepeatable"
        const val R_EVENT_ASSIST = "${Router.PACKAGE_NAME}.annotations.context.assist.REventAssist"
        const val R_EVENT_ASSIST_REPEATABLE = "${Router.PACKAGE_NAME}.annotations.context.assist.REventAssistRepeatable"
        const val R_ROUTE = "${Router.PACKAGE_NAME}.annotations.route.RRoute"
        const val R_ROUTE_REPEATABLE = "${Router.PACKAGE_NAME}.annotations.route.RRouteRepeatable"
        const val R_ROUTE_FUNCTION = "${Router.PACKAGE_NAME}.annotations.route.function.RFunction"
        const val R_ROUTE_FUNCTION_REPEATABLE = "${Router.PACKAGE_NAME}.annotations.route.function.RFunctionRepeatable"
        const val R_ROUTE_PAGE = "${Router.PACKAGE_NAME}.annotations.route.page.RPage"
        const val R_ROUTE_PAGE_REPEATABLE = "${Router.PACKAGE_NAME}.annotations.route.page.RPageRepeatable"
        const val R_ROUTE_INSTANCE = "${Router.PACKAGE_NAME}.annotations.route.instance.RRouteInstance"
        const val R_ROUTE_INSTANCE_REPEATABLE = "${Router.PACKAGE_NAME}.annotations.route.instance.RRouteInstanceRepeatable"
        const val R_ROUTE_ASSIST = "${Router.PACKAGE_NAME}.annotations.route.assist.RRouteAssist"
        const val R_ROUTE_ASSIST_REPEATABLE = "${Router.PACKAGE_NAME}.annotations.route.assist.RRouteAssistRepeatable"
        const val R_INSTANCE = "${Router.PACKAGE_NAME}.annotations.route.RInstance"
        const val R_INSTANCE_REPEATABLE = "${Router.PACKAGE_NAME}.annotations.route.RInstanceRepeatable"
        const val R_VALUE = "${Router.PACKAGE_NAME}.annotations.route.RValue"
        const val R_OBJECT = "${Router.PACKAGE_NAME}.annotations.route.RObject"
    }

    object Aggregate {
        private const val GENERATE_PACKAGE_NAME = "rubik.generate"

        object Declare{
            fun makeAggregatePackageName(uri: String): String = "$GENERATE_PACKAGE_NAME.aggregate.${uri.uriToSnake()}"
            fun makeAggregateClassName(name: String) = toPascal(name, "Aggregate")
        }

        const val INTERFACE_NAME = "${Router.PACKAGE_NAME}.context.Aggregatable"

        const val PROPERTY_URI_NAME = "URI"
        const val PROPERTY_EVENT_MSGS_NAME = "EVENT_MSGS"

        const val COMPANION_SUPER_NAME = "${Router.PACKAGE_NAME}.context.AggregateFactory"
        const val PROPERTY_CREATOR_NAME = "CREATOR"

        const val ROUTE_PACKAGE_NAME = "${Router.PACKAGE_NAME}.route"
        const val RESULT_CLASS_NAME = "Result"
        val RESULT_CLASS_NAME_AS = RESULT_CLASS_NAME.toLegalClassName()

        const val QUERIES_CLASS_NAME = "Queries"
        val QUERIES_CLASS_NAME_AS = QUERIES_CLASS_NAME.toLegalClassName()
        const val RESULTS_CLASS_NAME = "ResultGroups"
        val RESULTS_CLASS_NAME_AS = RESULTS_CLASS_NAME.toLegalClassName()

        const val METHOD_ON_EVENT_NAME = "onEvent"
        const val METHOD_ON_ROUTE_NAME = "onRoute"

        const val ROUTE_PARAMETER_PATH_NAME = "path"
        const val ROUTE_PARAMETER_QUERIES_NAME = "queries"
        const val ROUTE_PARAMETER_RESULTS_NAME = "results"
        const val EVENT_PARAMETER_MSG_NAME = "msg"

        const val PATH_PACKAGE_NAME = "${Router.PACKAGE_NAME}.router.uri"
        const val PATH_CLASS_NAME = "Path"
        val PATH_CLASS_NAME_AS = PATH_CLASS_NAME.toLegalClassName()

        const val METHOD_MATCHING_NAME = "matching"
        const val METHOD_SET_PARAMETERS_NAME = "setParameters"
        private const val METHOD_GET_PARAMETERS_NAME = "getParameters"
        const val PARAMETER_VALUES_NAME = "values"

        const val LAUNCHER_PACKAGE_NAME = "${Router.PACKAGE_NAME}.activity"
        const val LAUNCHER_CLASS_NAME = "Launcher"
        val LAUNCHER_CLASS_NAME_AS = LAUNCHER_CLASS_NAME.toLegalClassName()
        const val METHOD_LAUNCH_NAME = "launch"

        const val MAPPING_PACKAGE_NAME = "${Router.PACKAGE_NAME}.route.mapping"
        const val TO_TYPE_OF_T_FUNCTION_NAME = "toTypeOfT"
        const val CAST_TO_TYPE_OF_T_FUNCTION_NAME = "castToTypeOfT"
        const val MAP_TO_TYPE_FUNCTION_NAME = "mapToType"

        private val PATH_PARAMETER_REGEX = Regex("\\{([^}]*)\\}")
        fun isParameterPath(path: String) = path.contains(PATH_PARAMETER_REGEX)
        fun makeGetPathQueriesCode(originPath: String): String = "$PATH_CLASS_NAME_AS(\"$originPath\").$METHOD_GET_PARAMETERS_NAME($ROUTE_PARAMETER_PATH_NAME)"
        fun makeAddPathQueriesCode(originPath: String): String = "$ROUTE_PARAMETER_QUERIES_NAME.addAll(${makeGetPathQueriesCode(originPath)})"

        fun makeRouteExceptionCode() = "${Router.PACKAGE_NAME}.route.exception.BadPathOrVersionException($ROUTE_PARAMETER_PATH_NAME)"

        fun makeGetQueryCode(name: String, index: Int): String = "${ROUTE_PARAMETER_QUERIES_NAME}.value($index, $name)".noSpaces()

        fun makeResultsCode(name: String): String = "$RESULT_CLASS_NAME_AS($name)"
        fun makeSetResultsCode(code: String, index: Int): String = "${ROUTE_PARAMETER_RESULTS_NAME}.set($index, $code)".noSpaces()
    }

    object Contexts {
        private const val GENERATE_PACKAGE_NAME = "rubik.generate"

        object Declare {
            fun makeContextPackageName(
                uri: String,
                subPackage: String? = null
            ): String = listOfNotNull(
                GENERATE_PACKAGE_NAME,
                "context",
                uri.uriToSnake(),
                subPackage
            ).joinToString(".")
        }
        const val CONTEXT_BASE_CLASS_NAME = "Context"
        const val CONSTANTS_URI_NAME = "URI"
        const val OBJECT_URIS_NAME = "Uris"
        const val CLASS_TOUCH_HOLDER = "${Router.PACKAGE_NAME}.router.TouchHolder"
        const val CLASS_TOUCHER = "Toucher"

        const val PARCELABLE_CREATOR_CLASS_NAME = "android.os.Parcelable.Creator"
        const val COMPANION_CLASS_NAME = ".Companion"

        const val KEEP_ANNOTATION_CLASS_NAME = "$ANDROIDX_PACKAGE_NAME.annotation.Keep"
    }

    object Apis {
        const val NAVIGATE_FUNCTION_PACKAGE_NAME = "${Router.PACKAGE_NAME}.router"
        const val TOUCH_FUNCTION_NAME = "touch"
        const val MISS_FUNCTION_NAME = "miss"
        const val NAVIGATE_FUNCTION_NAME = "navigate"
        const val NAVIGATE_FOR_RESULT_FUNCTION_NAME = "navigateForResult"
        const val URI_DSL_NAME = "uri"
        const val QUERY_DSL_NAME = "query"
        const val RESULT_DSL_NAME = "result"
        const val CHECK_ROUTER_VERSION_DSL_NAME = "checkRouterVersion"
        const val PARAMETER_NAME_PREFIX = "rubik"
        const val PARAMETER_NAME_INSTANCE_PREFIX = "${PARAMETER_NAME_PREFIX}Instance"
        fun toResultTransformerName(index: Int, baseName: String?): String = if (null == baseName || baseName.isBlank()) "routeResultTransformer${if (index>0) index.toString() else ""}" else baseName
        fun makeAddToQueryCode(keyName: String, filedName: String): String = "\"$keyName\" with $filedName"

        const val FUNCTION_ARGUMENTS = "lambdaArg"

        private val PARAMETER_KEYWORDS = arrayOf("navigate", "query", "uri", "flags", "requestCode", "result", "path", "queries", "results")
        fun toLegalParameterName(name: String) = if (PARAMETER_KEYWORDS.contains(name)) toCamel(name, "parameter") else name
        fun toCallbackTransformerName(name: String) = toCamel(name, "callback", "transformer")
        fun toCallbackName(name: String) = toCamel(name, "callback")
    }

    object Activities {
        const val ACTIVITY_CLASS_NAME = "android.app.Activity"
        const val CONTEXT_CLASS_NAME = "android.content.Context"
        const val FRAGMENT_CLASS_NAME = "$ANDROIDX_PACKAGE_NAME.fragment.app.Fragment"
        const val PROPERTY_INTENT_FLAGS = "${Apis.PARAMETER_NAME_PREFIX}IntentFlags"
        const val PROPERTY_REQUEST_CODE = "${Apis.PARAMETER_NAME_PREFIX}RequestCode"
        const val PROPERTY_LAUNCHER = "${Apis.PARAMETER_NAME_PREFIX}Launcher"
        const val REQUEST_CODE_DSL_NAME = "requestCode"
        const val FLAGS_DSL_NAME = "flags"
    }

    object RouteActions {
        const val CONTEXT_ROUTE_ACTIONS_BASE_CLASS_NAME = "RouteActions"
        const val INTERFACE_NAME = "${Router.PACKAGE_NAME}.context.RouteActions"
        const val PROPERTY_ROUTE_ACTION = "rubikRouteAction"
    }

    object ContextRouters {
        const val ROUTE_CONTEXT_BASE_CLASS_NAME = "RouteContext"
        const val RUBIK_CLASS_NAME = "Rubik"
        const val FIND_ACTIONS_FUNCTION_NAME = "safeFindActions"
        const val ROUTE_FUNCTION_NAME = "safeRoute"
    }

    object Object {
        const val SUPER_NAME = "${Router.PACKAGE_NAME}.route.SubObject"
        const val OBJECT_MAPPINGS_NAME = "Mappings"
        const val SUB_OBJECT_ORIGINAL_FILED_NAME = "originalObject"
        fun makeToTypeMappingFunctionName(name: String, nullable: Boolean = false): String = if (nullable) "toNullable${name}Mapping" else "to${name}Mapping"
        const val OBJECT_INSTANCE_PARAMETER_NAME = "objectInstance"

    }

    object Identity {
        object Declare {
            const val CONTEXT_ID_BASE_CLASS_NAME = "ContextId"
            const val AGGREGATE_ID_BASE_CLASS_NAME = "AggregateId"
        }
        const val CONTEXT_ID_SUPER_CLASS_NAME = "${Router.PACKAGE_NAME}.identity.RContextId"
        const val AGGREGATE_ID_SUPER_CLASS_NAME = "${Router.PACKAGE_NAME}.identity.RAggregateId"
    }

    private const val CLASS_NAME_PREFIX = "Rubik"
    fun String.toLegalClassName() = if (!this.startsWith(CLASS_NAME_PREFIX)) toPascal(CLASS_NAME_PREFIX, this) else this

    object KDoc {
        fun function(
            function: String,
            queries: List<String>,
            result: String? = null
        ) = mutableListOf<String>().apply {
            add(function)
            if (queries.isNotEmpty()) {
                add("- parameters:")
                queries.forEach { query ->
                    add("--- $query".noSpaces())
                }
            }
            if (null != result) {
                add("- resultType:")
                add("--- $result".noSpaces())
            }
        }

        fun functionRouter(
            uri: String,
            originFunction: String,
            queries: List<String>,
            result: String? = null
        ) =
            """
            |encapsulate Rubik DSL Router.
            |
            |from uri: 
            |[$uri]
            |
            |route to:
            |@see ${function(originFunction, queries, result).joinToString("\n")}
            """.trimMargin()

        private fun contextKDoc(
            title: String,
            uri: String,
            version: String,
            enableUserAndTime: Boolean = true
        ): String {
            val des = "uri" to "[$uri]"
            return if (enableUserAndTime)
                updateFileKDoc(title, version, des)
            else
                updateFileKDocWithoutUserAndTime(title, version, des)
        }

        private fun contextIdKDoc(
            title: String,
            uri: String,
            version: String,
            originalToken: String,
            enableUserAndTime: Boolean = true
        ): String {
            val uriDes = "uri" to "[$uri]"
            val tokenDes = "originalToken" to originalToken.noSpaces()
            return if (enableUserAndTime)
                updateFileKDoc(title, version, uriDes, tokenDes)
            else
                updateFileKDocWithoutUserAndTime(title, version, uriDes, tokenDes)
        }

        fun context(uri: String, version: String) =
            contextKDoc("generated Rubik Context.", uri, version)

        fun routerContext(uri: String, version: String) =
            contextKDoc("generated Rubik Router Context.", uri, version)

        fun value(uri: String, version: String) =
            contextKDoc("generated Rubik Context Value class.", uri, version)

        fun aggregate(uri: String, version: String, enableUserAndTime: Boolean) =
            contextKDoc("aggregate router function and router event of Rubik Context.", uri, version, enableUserAndTime)

        fun routeActions(uri: String, version: String) =
            contextKDoc("generated Rubik RouteActions.", uri, version)

        fun contextId(uri: String, version: String, originalToken: String) =
            contextIdKDoc("generated Rubik ContextId.", uri, version, originalToken)

        fun aggregateId(
            uri: String,
            version: String,
            originalToken: String,
            enableUserAndTime: Boolean
        ) = contextIdKDoc("generated Rubik AggregateId.", uri, version, originalToken,enableUserAndTime )

        fun objekt(uri: String, version: String) =
            contextKDoc("generated Rubik Context Object class.", uri, version)

        fun callback(uri: String, version: String) =
            contextKDoc("generated Rubik Context Callback interface.", uri, version)
    }
}

enum class InvokeElementType {
    METHOD, HIGHER_ORDER_FUNC, PROPERTY, CONSTRUCTOR
}

enum class CallbackType {
    HIGHER_ORDER_FUNC, OPEN_CLASS, INTERFACE
}