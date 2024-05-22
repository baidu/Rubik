package com.rubik.identity

import com.rubik.identity.exception.*
import com.rubik.logger.Logger
import com.rubik.router.annotations.RInvariant

class IdentityChecker {
    private val contexts = mutableMapOf<String,RContextId>()
    private val components = mutableMapOf<String,Pair<RComponentId,RAggregateId?>>()

    fun addContextId(contextId: RContextId) {
        if(contexts.containsKey(contextId.uri))
            throw DuplicateContextException(contextId.uri)
        contexts[contextId.uri] = contextId
    }

    fun addComponentId(componentId: RComponentId, aggregateId: RAggregateId?) {
        aggregateId?.let {
            if (componentId.uri != aggregateId.uri)
                throw UriNotConformException(componentId, aggregateId)
        }
        if(components.containsKey(componentId.uri))
            throw DuplicateComponentException(componentId.uri)
        components[componentId.uri] = componentId to aggregateId
    }


    @RInvariant
    fun addContextId(uri: String, contextClassName: String) {
        val context = try {
            Class.forName(contextClassName).newInstance() as? RContextId
        } catch (e: ClassNotFoundException) {
            Logger.e(" RUBIK IdentityChecker addContext className: $contextClassName with exception : $e", e)
            null
        } ?: RContextId(uri, MayBeNotSupport())
        addContextId(context)
    }

    @RInvariant
    fun addComponentId(uri: String, componentClassName: String, aggregateClassName: String?) {
        val component =  try {
            Class.forName(componentClassName).newInstance() as? RComponentId
        } catch (e: ClassNotFoundException) {
            Logger.e(" RUBIK IdentityChecker addComponent className: $componentClassName with exception : $e", e)
            null
        } ?: RComponentId(uri, MayBeNotSupport(), listOf(), listOf())
        val aggregate = aggregateClassName?.let {
            try {
                Class.forName(aggregateClassName).newInstance() as? RAggregateId
            } catch (e: ClassNotFoundException) {
                Logger.e(" RUBIK IdentityChecker addComponent className: $aggregateClassName with exception : $e", e)
                null
            } ?: RAggregateId(uri, MayBeNotSupport())
        }
        addComponentId(component, aggregate)
    }

    fun check(
        checkAggregateAndContextLib: Boolean = true,
        checkComponentAndAggregate: Boolean = true,
        checkTouching: Boolean = true,
        checkPacking: Boolean = true
    ) {
        Logger.d(" ==============================================================")
        Logger.d(" RUBIK IdentityChecker START :")
        Logger.d(" checkAggregateAndContextLib : $checkAggregateAndContextLib")
        Logger.d(" checkComponentAndAggregate : $checkComponentAndAggregate")
        Logger.d(" checkTouching : $checkTouching")
        Logger.d(" checkPacking : $checkPacking")

        val exceptions = mutableListOf<RIdentityCheckFailedException>()

        components.forEach { (_, entry) ->
            val component = entry.first
            val aggregate = entry.second

            Logger.d(" ==============================================================")
            Logger.d(" ---------  COMPONENT :${component.uri } mark:${component.mark}")

            aggregate?.let {
                if (checkAggregateAndContextLib) checkAggregateAndContextLib(exceptions, aggregate)

                if (checkComponentAndAggregate) checkComponentAndAggregate(exceptions, component, aggregate)
            }

            if (checkTouching) checkTouchingOfComponent(exceptions, component)

            if (checkPacking) checkPackingOfComponent(exceptions, component)

            Logger.d(" ==============================================================")
        }

        Logger.d(" ==============================================================")
        if (exceptions.isEmpty()) {
            Logger.d(" RUBIK IdentityChecker CHECK FINISH !")
        } else {
            Logger.e(" RUBIK IdentityChecker CHECK FAILED !", null)
            exceptions.forEach { exception->
                Logger.e(" RUBIK IdentityChecker CHECK FAILED ON :", exception)
                throw exception
            }
        }
        Logger.d(" ==============================================================")
    }

    private fun checkComponentAndAggregate(
        exceptions: MutableList<RIdentityCheckFailedException>,
        component: RComponentId,
        aggregate: RAggregateId
    ) {
        // check component and aggregate
        Logger.d(" ---------  AGGREGATE :${aggregate.uri} mark:${aggregate.mark.toStringWithToken()}")
        Logger.d(" ------------  ! CHECKING - COMPONENT and AGGREGATE !")

        if (!component.checkVersion(aggregate))
            RIdentityCheckFailedException(component, aggregate).let { exception ->
                Logger.e(" ------------ --- ! FAILED - $exception FAILED!", null)
                exceptions.add(exception)
            }
    }

    private fun checkAggregateAndContextLib(
        exceptions: MutableList<RIdentityCheckFailedException>,
        aggregate: RAggregateId
    ) {
        // check aggregates and context lib
        val context =
            contexts[aggregate.uri] ?: throw RIdentityNotFoundException(aggregate.uri, "Context")

        Logger.d(" ---------  --- with CONTEXT :${context.uri} mark:${context.mark.toStringWithToken()}")
        Logger.d(" ------------  ! CHECKING - AGGREGATE and CONTEXT !")

        if (!context.checkToken(aggregate))
            RIdentityCheckFailedException(aggregate, context).let { exception ->
                Logger.e(" ------------ --- ! FAILED - $exception FAILED!", null)
                exceptions.add(exception)
            }
    }

    private fun checkTouchingOfComponent(
        exceptions: MutableList<RIdentityCheckFailedException>,
        component: RComponentId
    ) {
        // check component touching
        component.dependencies.forEach { dependencyContext ->
            val defContext = contexts[dependencyContext.uri] ?: throw RIdentityNotFoundException(dependencyContext.uri, "Context")

            Logger.d(" ---------  --- DEPENDENCY :${dependencyContext.uri } mark:${dependencyContext.mark}")
            Logger.d(" ---------  --- --- with CONTEXT :${defContext.uri } mark:${defContext.mark}")
            Logger.d(" ------------  ! CHECKING - DEPENDENCY !")

            if (!dependencyContext.checkVersion(defContext))
                RIdentityCheckFailedException(component, defContext).let { exception ->
                    Logger.e(" ------------ --- ! FAILED - $exception FAILED!", null)
                    exceptions.add(exception)
                }
        }
    }

    private fun checkPackingOfComponent(
        exceptions: MutableList<RIdentityCheckFailedException>,
        component: RComponentId
    ) {
        // check component packed
        component.packed.forEach { packedComponent ->
            val defComponent = components[packedComponent.uri]?.first ?: throw RIdentityNotFoundException(packedComponent.uri, "Component")

            Logger.d(" ---------  --- PACKED :${packedComponent.uri } mark:${packedComponent.mark}")
            Logger.d(" ---------  --- --- with COMPONENT :${defComponent.uri } mark:${defComponent.mark}")
            Logger.d(" ------------  ! CHECKING CHECKING - PACKED !")

            if (!packedComponent.checkVersion(defComponent))
                RIdentityCheckFailedException(component, defComponent).let { exception ->
                    Logger.e(" ------------ --- ! FAILED - $exception FAILED!", null)
                    exceptions.add(exception)
                }
        }
    }

    private fun RIdentity.preCheck(): Boolean {
        return when {
            mark is MayBeNotSupport -> {
                Logger.e(" RUBIK IdentityChecker [$uri] maybe not Support Identity or not Packing", null)
                true
            }
            mark.version.isCodeVersion() -> {
                Logger.e(" RUBIK IdentityChecker [$uri] is code version:[${mark.version}] MayBe in ProjectMode", null)
                false
            }
            else -> false
        }
    }

    private fun RIdentity.checkVersion(other: RIdentity) =
        this.preCheck() || other.preCheck() || mark.checkVersion(other.mark)

    private fun RIdentity.preCheckVersion(other: RIdentity): Boolean {
        if (!mark.checkVersion(other.mark))
            Logger.e(" RUBIK IdentityChecker [$uri] version (${mark.version}) and (${other.mark.version}) was not match", null)
        return false // always false just warming
    }


    private fun RIdentity.checkToken(other: RIdentity) =
        this.preCheck() || other.preCheck() || preCheckVersion(other) || mark.checkToken(other.mark)

}