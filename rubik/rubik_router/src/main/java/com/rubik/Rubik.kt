package com.rubik

import com.rubik.context.Aggregatable
import com.rubik.context.AggregateFactory
import com.rubik.context.RouteActions
import com.rubik.logger.Logger
import com.rubik.route.exception.BadAggregatableClassException
import com.rubik.route.exception.DuplicateRegisteredUriException
import com.rubik.router.annotations.RInvariant
import com.rubik.router.exception.BadRubikVersionException
import com.rubik.router.exception.RContextNotFoundException
import com.rubik.router.exception.RubikNotInitException
import kotlin.reflect.full.companionObjectInstance

/**
 *  The rubik router object.
 *
 *  @since 1.0
 */
object Rubik {
    /**
     * The logger of rubik router.
     */
    fun logger(block: Logger.() -> Unit) {
        block(Logger)
    }

    private var aggregateFactories: MutableMap<String, () -> Aggregatable> = mutableMapOf()
    private var aggregateFactoriesByMsg: MutableMap<String, MutableSet<() -> Aggregatable>> = mutableMapOf()

    /**
     * register AggregateFactory to rubik router.
     */
   internal fun registerAggregateFactory(factory: AggregateFactory) {
        if (!aggregateFactories.containsKey(factory.URI)) {
            aggregateFactories[factory.URI] = factory.CREATOR
            factory.EVENT_MSGS.forEach { msg ->
                aggregateFactoriesByMsg.getOrPut(msg) {
                    mutableSetOf()
                }.add(factory.CREATOR)
            }
        } else {
            throw DuplicateRegisteredUriException(factory.URI)
        }
    }

    /**
     * register Aggregatable to rubik router by name.
     */
    @RInvariant
    fun registerAggregatable(className: String) {
        try {
            (Class.forName(className).kotlin.companionObjectInstance as? AggregateFactory).let {
                    companion ->
                if (null == companion)
                    throw BadAggregatableClassException(className)
                else
                    companion.register()
            }
        } catch (e: Exception) {
            Logger.e(" RUBIK register on className: $className with exception : $e", e)
        }
    }

    @RInvariant
    fun createAggregate(uri: String): Aggregatable? {
        if (aggregateFactories.isEmpty()) {
            throw RubikNotInitException()
        }
        return aggregateFactories[uri]?.invoke()
    }

    internal fun createAggregatesByMsg(msg: String): List<Aggregatable> {
        if (aggregateFactories.isEmpty()) {
            throw RubikNotInitException()
        }
        return aggregateFactoriesByMsg[msg]?.map { creator ->
            creator()
        } ?: listOf()
    }

    @RInvariant
    inline fun <reified T : RouteActions> findActions(uri: String): T {
        return (createAggregate(uri) as? T) ?: throw RContextNotFoundException(uri)
    }

    internal fun touchUri(uri: String): Boolean {
        return aggregateFactories.containsKey(uri)
    }

    internal fun checkRouterVersionLogic(checkRouterVersion: Int) {
        if (checkRouterVersion < 900) {
            throw BadRubikVersionException("low version context or code [$checkRouterVersion] using a higher version router , this router version is 1.9.0 [900] + !")
        }
    }
}
