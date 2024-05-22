package com.rubik.apt

import com.blueprint.kotlin.pool.ElementPool
import com.ktnail.x.Logger
import com.ktnail.x.replaceDir
import com.rubik.apt.annotation.*
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.files.source.AggregateSourceFile
import com.rubik.apt.files.source.ContextSourceFiles
import com.rubik.apt.plugin.PluginArguments
import com.rubik.apt.utility.makeAggregateGeneratedDir
import com.rubik.apt.utility.makeDefaultGeneratedDir
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

class RContextProcessor {

    private val processingContexts: MutableMap<String, ContextCodeBase> = mutableMapOf()
    private var defaultContexts: ContextCodeBase? = null

    private val elementPool = ElementPool()

    private var _args: PluginArguments? = null
    private val args: PluginArguments
        get() = _args ?: throw RuntimeException("RContextProcessor is not init !")

    @Synchronized
    fun init(processingEnv: ProcessingEnvironment) {
        Logger.d = { msg ->
            processingEnv.messager.printMessage(Diagnostic.Kind.OTHER, "$msg\n ")
        }
        Logger.e = { msg ->
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "$msg\n ")
        }
        _args = PluginArguments(processingEnv)
        Logger.e(" APT DBG RContextProcessor init args:$args ")
    }

    fun process(
        annotations: MutableSet<out TypeElement>?,
        processingEnv: ProcessingEnvironment,
        roundEnv: RoundEnvironment?
    ): Boolean {
        if (!args.contextLibsEnable && !args.aggregateEnable) return true
        if (roundEnv?.processingOver() == false) {
            processContexts(roundEnv)
            Logger.e(" APT DBG RContextProcessor processing ")
        } else {
            if (processingContexts.isNotEmpty()) {
                processingContexts.forEach { (_, context) ->
                    defaultContexts?.let { default -> context.merge(default) }
                    context.compose()
                }
                if (args.aggregateEnable) {
                    Logger.e(" APT DBG RContextProcessor aggregateEnable ")
                    (makeAggregateGeneratedDir(args.aggregateGenerated) ?: makeDefaultGeneratedDir(processingEnv))?.let { directory->
                        directory.replaceDir { dir->
                            processingContexts.forEach { (uri, context) ->
                                if (context.generatedAggregateEnable){
                                    AggregateSourceFile(
                                        dir,
                                        args.aggregateMethodSize,
                                        args.aggregateKDocUserAndTime
                                    ).generate(uri, context)
                                }
                            }
                        }
                    }
                }
                if (args.contextLibsEnable) {
                    Logger.e(" APT DBG RContextProcessor contextLibsEnable ")
                    makeDefaultGeneratedDir(processingEnv)?.let { directory ->
                        processingContexts.forEach { (uri, context) ->
                            if (context.generatedContextLibsEnable){
                                ContextSourceFiles(directory).generate(
                                    uri,
                                    context,
                                    args.routerContextEnable
                                )
                            }
                        }
                    }
                }
            }
            Logger.e(" APT DBG RContextProcessor process over ")
        }
        return true
    }

    private fun processContexts(
        roundEnv: RoundEnvironment
    ) {
        args.contexts?.forEach { context-> addContextByUri(context) }

        // value
        Value.OBTAINER.addToContext(roundEnv, elementPool, args, ::getContextByUri)

        // event
        Event.OBTAINER.addToContext(roundEnv, elementPool, args, ::getContextByUri)
        EventInstance.OBTAINER.addToContext(roundEnv, elementPool, args, ::getContextByUri)

        // route
        Route.OBTAINER.addToContext(roundEnv, elementPool, args, ::getContextByUri)
        RouteInstance.OBTAINER.addToContext(roundEnv, elementPool, args, ::getContextByUri)

        // object
        Object.OBTAINER.addToContext(roundEnv, elementPool, args, ::getContextByUri)

        // callback
        Callback.OBTAINER.addToContext(roundEnv, elementPool, args, ::getContextByUri)

    }


    private fun addContextByUri(arguments: PluginArguments.ContextArguments) {
        val uri = arguments.context.uri
        val name  = arguments.context.name
        val version = arguments.context.version

        if (uri.isNotBlank()) {
            processingContexts[uri] = getContextByUri(uri).apply {
                this.name = name
                this.version = version
                this.generatedContextLibsEnable = arguments.contextLibsEnable
                this.generatedAggregateEnable = arguments.aggregateEnable
            }
        }
    }

    private fun getContextByUri(uri: String): ContextCodeBase {
        return if (uri.isBlank()) {
            return defaultContexts ?: ContextCodeBase().apply {
                defaultContexts = this
            }
        } else {
            processingContexts.getOrPut(uri) { ContextCodeBase() }
        }
    }

}