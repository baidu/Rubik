package com.rubik.apt

import com.blueprint.kotlin.lang.utility.hasSuperType
import com.blueprint.kotlin.lang.utility.toKbpClassElement
import com.blueprint.kotlin.lang.utility.toKbpElement
import com.blueprint.kotlin.pool.ElementPool
import com.ktnail.x.Logger
import com.ktnail.x.find
import com.ktnail.x.uri.buildVersionPath
import com.rubik.annotations.context.REvent
import com.rubik.annotations.context.REventRepeatable
import com.rubik.annotations.context.assist.REventAssist
import com.rubik.annotations.context.assist.REventAssistRepeatable
import com.rubik.annotations.context.instance.REventInstance
import com.rubik.annotations.context.instance.REventInstanceRepeatable
import com.rubik.annotations.route.RRoute
import com.rubik.annotations.route.RRouteRepeatable
import com.rubik.annotations.route.RValue
import com.rubik.annotations.route.assist.RRouteAssist
import com.rubik.annotations.route.assist.RRouteAssistRepeatable
import com.rubik.annotations.route.function.RFunction
import com.rubik.annotations.route.function.RFunctionRepeatable
import com.rubik.annotations.route.instance.RRouteInstance
import com.rubik.annotations.route.instance.RRouteInstanceRepeatable
import com.rubik.annotations.route.page.RPage
import com.rubik.annotations.route.page.RPageRepeatable
import com.rubik.apt.codebase.activity.ActivityCodeBase
import com.rubik.apt.codebase.api.ApiCodeBase
import com.rubik.apt.codebase.api.ApiInstanceCodeBase
import com.rubik.apt.codebase.context.ContextCodeBase
import com.rubik.apt.codebase.event.EventCodeBase
import com.rubik.apt.codebase.event.EventInstanceCodeBase
import com.rubik.apt.codebase.value.ValueCodeBase
import com.rubik.apt.files.source.AggregateSourceFiles
import com.rubik.apt.files.source.ContextSourceFiles
import com.rubik.apt.plugin.PluginArguments
import com.rubik.apt.utility.cleanAggregateGeneratedDir
import com.rubik.apt.utility.contextUri
import com.rubik.apt.utility.makeDefaultGeneratedDir
import com.rubik.apt.utility.typeToStringInAnnotations
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
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
        if (roundEnv?.processingOver() == false) {
            processContext(roundEnv)
            Logger.e(" APT DBG RContextProcessor processing ")
        } else {
            if (processingContexts.isNotEmpty()) {
                processingContexts.forEach { (_, context) ->
                    defaultContexts?.let { default -> context.merge(default) }
                    context.compose()
                }
                if (args.aggregateEnable) {
                    Logger.e(" APT DBG RContextProcessor aggregateEnable ")
                    (cleanAggregateGeneratedDir(args) ?: makeDefaultGeneratedDir(
                        processingEnv
                    ))?.let { directory ->
                        AggregateSourceFiles(directory).generate(processingContexts)
                    }
                }
                makeDefaultGeneratedDir(processingEnv)?.let { directory ->
                    processingContexts.find { value -> value.generatedEnable }.let { contexts->
                        ContextSourceFiles(directory).generate(contexts)
                    }
                }
            }
            Logger.e(" APT DBG RContextProcessor process over ")
        }
        return true
    }

    private fun processContext(
        roundEnv: RoundEnvironment
    ) {
        val defaultScheme = args.defaultScheme

       args.argumentContexts { uri, name, dependencies, version, generated ->
            addContextByUri(
                uri, name, dependencies, version, generated
            )
        }

        // value
        roundEnv.getElementsAnnotatedWith(RValue::class.java)?.forEach { element ->
            val contextUri = element.getAnnotation(RValue::class.java).contextUri(defaultScheme)
            if (element is TypeElement) {
                element.toKbpClassElement(elementPool)?.let { kbpClassElement ->
                    addValueByUri(contextUri, ValueCodeBase(kbpClassElement))
                }
            }
        }

        // event
        roundEnv.getElementsAnnotatedWith(REventInstance::class.java)?.forEach { element ->
            processEventInstance(element, element.getAnnotation(REventInstance::class.java), defaultScheme)
        }

        roundEnv.getElementsAnnotatedWith(REventInstanceRepeatable::class.java)?.forEach { element ->
            element.getAnnotation(REventInstanceRepeatable::class.java).value.forEach { annotation ->
                processEventInstance(element, annotation, defaultScheme)
            }
        }

        roundEnv.getElementsAnnotatedWith(REventAssist::class.java)?.forEach { element ->
            processEventInstance(element, element.getAnnotation(REventAssist::class.java), defaultScheme)
        }

        roundEnv.getElementsAnnotatedWith(REventAssistRepeatable::class.java)?.forEach { element ->
            element.getAnnotation(REventAssistRepeatable::class.java).value.forEach { annotation ->
                processEventInstance(element, annotation, defaultScheme)
            }
        }

        roundEnv.getElementsAnnotatedWith(REvent::class.java)?.forEach { element ->
            processLife(element, element.getAnnotation(REvent::class.java), defaultScheme)
        }

        roundEnv.getElementsAnnotatedWith(REventRepeatable::class.java)?.forEach { element ->
            element.getAnnotation(REventRepeatable::class.java).value.forEach { annotation ->
                processLife(element, annotation, defaultScheme)
            }
        }

        // route
        roundEnv.getElementsAnnotatedWith(RRouteInstance::class.java)?.forEach { element ->
            processRouteInstance(element, element.getAnnotation(RRouteInstance::class.java), defaultScheme)
        }

        roundEnv.getElementsAnnotatedWith(RRouteInstanceRepeatable::class.java)?.forEach { element ->
            element.getAnnotation(RRouteInstanceRepeatable::class.java).value.forEach { annotation ->
                processRouteInstance(element, annotation, defaultScheme)
            }
        }

        roundEnv.getElementsAnnotatedWith(RRouteAssist::class.java)?.forEach { element ->
            processRouteInstance(element, element.getAnnotation(RRouteAssist::class.java), defaultScheme)
        }

        roundEnv.getElementsAnnotatedWith(RRouteAssistRepeatable::class.java)?.forEach { element ->
            element.getAnnotation(RRouteAssistRepeatable::class.java).value.forEach { annotation ->
                processRouteInstance(element, annotation, defaultScheme)
            }
        }

        roundEnv.getElementsAnnotatedWith(RRoute::class.java)?.forEach { element ->
            processRoute(element, element.getAnnotation(RRoute::class.java), defaultScheme)
        }

        roundEnv.getElementsAnnotatedWith(RRouteRepeatable::class.java)?.forEach { element ->
            element.getAnnotation(RRouteRepeatable::class.java).value.forEach { annotation ->
                processRoute(element, annotation, defaultScheme)
            }
        }

        roundEnv.getElementsAnnotatedWith(RFunction::class.java)?.forEach { element ->
            processRoute(element, element.getAnnotation(RFunction::class.java), defaultScheme)
        }

        roundEnv.getElementsAnnotatedWith(RFunctionRepeatable::class.java)?.forEach { element ->
            element.getAnnotation(RFunctionRepeatable::class.java).value.forEach { annotation ->
                processRoute(element, annotation, defaultScheme)
            }
        }

        roundEnv.getElementsAnnotatedWith(RPage::class.java)?.forEach { element ->
            processRoute(element, element.getAnnotation(RPage::class.java), defaultScheme)
        }

        roundEnv.getElementsAnnotatedWith(RPageRepeatable::class.java)?.forEach { element ->
            element.getAnnotation(RPageRepeatable::class.java).value.forEach { annotation ->
                processRoute(element, annotation, defaultScheme)
            }
        }
    }

    private fun processLife(element: Element, annotation: REvent, defaultScheme: String?) {
        element.toKbpElement(elementPool)?.let { kbpElement ->
            EventCodeBase(
                elementPool,
                kbpElement,
                annotation.msg,
                annotation.tag
            )?.let { codeBase ->
                addLifeByUri(annotation.contextUri(defaultScheme), codeBase)
            }
        }
    }

    private fun processEventInstance(element: Element, annotation: REventAssist, defaultScheme: String?) {
        element.toKbpElement(elementPool)?.let { kbpElement ->
            EventInstanceCodeBase(
                elementPool,
                kbpElement,
                annotation.assistForTag
            )?.let { codeBase ->
                addEventInstanceByUri(annotation.contextUri(defaultScheme), codeBase)
            }
        }
    }

    private fun processEventInstance(element: Element, annotation: REventInstance, defaultScheme: String?) {
        element.toKbpElement(elementPool)?.let { kbpElement ->
            EventInstanceCodeBase(
                elementPool,
                kbpElement,
                annotation.provideForTag
            )?.let { codeBase ->
                addEventInstanceByUri(annotation.contextUri(defaultScheme), codeBase)
            }
        }
    }

    private fun processRoute(element: Element, annotation: RRoute, defaultScheme: String?) {
        if ((element as? TypeElement)?.hasSuperType(Constants.Activities.ACTIVITY_CLASS_NAME) == true) {
            processPageRoute(
                element,
                annotation.contextUri(defaultScheme),
                annotation.path,
                annotation.version,
                annotation.navigationOnly,
                false // compatible old version
            )
        } else {
            processFunctionRoute(
                element,
                annotation.contextUri(defaultScheme),
                annotation.path,
                annotation.version,
                annotation.navigationOnly,
                typeToStringInAnnotations {  annotation.resultType.qualifiedName },
                annotation.forResult,
                false  // compatible old version
            )
        }
    }

    private fun processRoute(element: Element, annotation: RFunction, defaultScheme: String? ) {
        processFunctionRoute(
            element,
            annotation.contextUri(defaultScheme),
            annotation.path,
            annotation.version,
            annotation.navigationOnly,
            typeToStringInAnnotations {  annotation.resultType.qualifiedName },
            annotation.forResult,
            true
        )
    }

    private fun processRoute(element: Element, annotation: RPage, defaultScheme: String?) {
        if ((element as? TypeElement)?.hasSuperType(Constants.Activities.ACTIVITY_CLASS_NAME) == true) {
            processPageRoute(
                element,
                annotation.contextUri(defaultScheme),
                annotation.path,
                annotation.version,
                annotation.navigationOnly,
                true
            )
        }
    }

    private fun processFunctionRoute(
        element: Element,
        uri: String,
        path: String,
        version: String,
        navigationOnly: Boolean,
        resultType: String,
        forResult: Boolean,
        pathSectionOptimize :Boolean
    ) {
        element.toKbpElement(elementPool)?.let { kbpElement ->
            ApiCodeBase(
                elementPool,
                kbpElement,
                path,
                version,
                resultType.let { if (it == Object::class.java.name) null else it },
                navigationOnly = navigationOnly,
                forResult = forResult,
                pathSectionOptimize = pathSectionOptimize
            )?.let { codeBase ->
                addApiByUri(uri, codeBase)
            }
        }
    }

    private fun processPageRoute(
        element: TypeElement,
        uri: String,
        path: String,
        version: String,
        navigationOnly: Boolean,
        pathSectionOptimize :Boolean
    ) {
        element.toKbpClassElement(elementPool)?.let { kbpClassElement ->
            ActivityCodeBase(
                kbpClassElement,
                path,
                version,
                navigationOnly = navigationOnly,
                pathSectionOptimize = pathSectionOptimize
            ).let { codeBase ->
                addActivityByUri(uri, codeBase)
            }
        }
    }

    private fun processRouteInstance(element: Element, annotation: RRouteAssist, defaultScheme: String?) {
        element.toKbpElement(elementPool)?.let { kbpElement ->
            ApiInstanceCodeBase(
                elementPool,
                kbpElement,
                annotation.assistForPath,
                annotation.version
            )?.let { codeBase ->
                addApiInstanceByUri(annotation.contextUri(defaultScheme), codeBase)
            }
        }
    }

    private fun processRouteInstance(element: Element, annotation: RRouteInstance, defaultScheme: String?) {
        element.toKbpElement(elementPool)?.let { kbpElement ->
            ApiInstanceCodeBase(
                elementPool,
                kbpElement,
                annotation.provideForPath,
                annotation.version
            )?.let { codeBase ->
                addApiInstanceByUri(annotation.contextUri(defaultScheme), codeBase)
            }
        }
    }

    private fun addContextByUri(
        uri: String,
        name: String?,
        dependencies: List<String>?,
        version: String?,
        generatedEnable: Boolean
    ) {
        if (uri.isNotBlank()) {
            processingContexts[uri] = getContextByUri(uri).apply {
                name?.let {
                    this.name = name
                }
                dependencies?.let {
                    this.dependencies = dependencies
                }
                version?.let{
                    this.version = version
                }
                this.generatedEnable = generatedEnable
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

    private fun addValueByUri(uri: String, codebase: ValueCodeBase) {
        getContextByUri(uri).values.add(codebase)
    }

    private fun addLifeByUri(uri: String, codeBase: EventCodeBase) {
        if (codeBase.msg.isNotBlank()) {
            getContextByUri(uri).apply {
                events.getOrPut(codeBase.msg) { mutableListOf() }.add(codeBase)
            }
        }
    }

    private fun addEventInstanceByUri(uri: String, codeBase: EventInstanceCodeBase) {
        if (codeBase.forTag.isNotBlank()) {
            getContextByUri(uri).apply {
                eventAssistants[codeBase.forTag] = codeBase
            }
        }
    }

    private fun addActivityByUri(uri: String, codeBase: ActivityCodeBase) {
        if (codeBase.path.isNotBlank()) {
            getContextByUri(uri).addActivity(codeBase)
        }
    }

    private fun addApiByUri(uri: String, codeBase: ApiCodeBase) {
        if (codeBase.path.isNotBlank()) {
            getContextByUri(uri).addApi(codeBase)
        }
    }

    private fun addApiInstanceByUri(uri: String, codeBase: ApiInstanceCodeBase) {
        if (codeBase.forPath.isNotBlank()) {
            getContextByUri(uri).apply {
                apiAssistants[buildVersionPath(codeBase.forPath, codeBase.version)] = codeBase
            }
        }
    }

}