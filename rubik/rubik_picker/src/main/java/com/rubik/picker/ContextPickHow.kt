package com.rubik.picker

import com.android.build.gradle.api.BaseVariant
import com.ktnail.x.Logger
import com.ktnail.x.mergeAll
import com.rubik.context.extra.Context
import com.rubik.context.MavenSource
import com.rubik.global.GlobalConfig
import com.rubik.pick.*
import com.rubik.context.extra.globalConfig
import com.rubik.picker.extra.configPickParameters
import com.rubik.picker.extra.sourcePickHows
import com.rubik.picker.extra.toPickParameters
import com.rubik.picker.log.LogTags

class ContextPickHow(val context: Context) {

    private var shellPacking: PickCase? = null
    private var shellPickCaseCache: PickCase? = null

    var sourcePickHow: FlavorHows? = null

    private val configByTag = mutableMapOf<String?, FlavorHows>()

    fun hold(hows: FlavorHows?, what: PickWhat) {
        shellPacking = PickCase(what).apply {
            hows?.let { addPickHows(hows) }
        }
    }

    fun release(exc: Excepted): Boolean {
        return shellPacking?.let { case ->
            case.removeFlavor(exc.forFlavor)
            !case.hasHow
        } ?: false
    }

    fun pickCase(what: PickWhat) = PickCase(what).apply {
        if (null != what.forFlavor) {
            shellPickCases()?.getFlavorHow(what.forFlavor)?.let { how ->
                addFlavorHow(what.forFlavor, how)
            }
        } else {
            shellPickCases()?.flavorHows?.let { hows->
                addPickHows(hows)
            }
        }

        if (!hasHow) {
            val tagConfig = tagConfig(what.where.tagName)
            if (null != what.forFlavor)
                tagConfig[what.forFlavor]?.let { how ->
                    addFlavorHow(what.forFlavor, final(how))
                }
            else
                tagConfig.forEach { (forFlavor, how) ->
                    if (null != forFlavor) addFlavorHow(forFlavor, final(how))
                    else addDefaultHow(final(how))
                }
        }

    }

    fun shellPickCases(variant: BaseVariant): PickCase? = shellPickCases()?.filter(variant)

    fun shellPickCases(): PickCase? {
        // SHELL (packing) >  SOURCE  > CONFIG
        return shellPickCaseCache ?:shellPacking?.let { shell -> // HAS Shell packing
            PickCase(shell.what).apply {
                val sourcePacking = context.sourcePickHows
                val tagConfigs = tagConfig(shell.what.where.tagName)
                if (shell.defaultPickOnly) {  // SHELL undefine flavor , Subject to SOURCE and CONFIG
                    val shellHow = shell.getDefaultHow()
                    if (sourcePacking.defaultPickOnly) {   // SOURCE undefine flavor , Subject to CONFIG
                        val sourceHow = sourcePacking[null]
                        tagConfigs.forEachByFlavorOrDefault(
                            byFlavor = { flavor, how ->
                                addFlavorHow(flavor, final(shellHow.mergeLowerPriority(sourceHow, how)))
                            },
                            default = { how ->
                                addDefaultHow(final(shellHow.mergeLowerPriority(sourceHow, how)))
                            }
                        )
                    } else { // SOURCE has different flavor , Subject to SOURCE
                        sourcePacking.forEachByFlavorOrDefault(
                            byFlavor = { flavor, how ->
                                addFlavorHow(flavor, final(shellHow.mergeLowerPriority(how, tagConfigs[flavor])))
                            }
                        )
                    }
                } else { // SHELL has different flavor , Subject to SHELL
                    shell.flavorHows.forEachByFlavorOrDefault(
                        byFlavor = { flavor, how ->
                            val shellHow = shell.getFlavorHow(flavor)
                            addFlavorHow(flavor, final(shellHow.mergeLowerPriority(how, sourcePacking[flavor], tagConfigs[flavor])))
                        }
                    )
                }
                Logger.dta(LogTags.PICK_CASE) { " <${context.uri}> SHELL PICK CASES : " + this.toLog() }
                shellPickCaseCache = this
            }
        }
    }

    private fun tagConfig(tagName: String?): FlavorHows =
        configByTag[tagName] ?: if (null != tagName) {
            val sourceConfig = context.tags[tagName]?.configPickParameters
            val globalConfig = globalConfig.tagConfigPickParameters(tagName)
            val tagConfig = sourceConfig?.mergeAll(globalConfig) { left, right ->
                left.mergeLowerPriority(right)
            } ?: globalConfig
            tagConfig.mergeAll(config()) { left, right ->
                left.mergeLowerPriority(right)
            }.flavorMergeDefault().apply {
                configByTag[tagName] = this
                Logger.dta(LogTags.PICK_CASE) { " <${context.uri}> TAG($tagName) HOWS CONFIG :"+
                        sourceConfig.toLog("   >> SOURCE ") +
                        globalConfig.toLog("   >> GLOBAL ") +
                        this.toLog("   >> MERGED ")
                }
            }
        } else {
            config()
        }

    private fun config(): FlavorHows = configByTag[null] ?: let {
        val sourceConfig = context.source.configPickParameters
        val globalConfig = globalConfig.configPickParameters(context.uri)
        sourceConfig.mergeAll(globalConfig) { left, right ->
            left.mergeLowerPriority(right)
        }.flavorMergeDefault().apply {
            configByTag[null] = this
            Logger.dta(LogTags.PICK_CASE) { " <${context.uri}> HOWS CONFIG : " +
                    sourceConfig.toLog("   >> SOURCE ") +
                    globalConfig.toLog("   >> GLOBAL ") +
                    this.toLog("   >> MERGED ")
            }
        }
    }


    private fun final(how: PickHow?): PickHow {
        val result = when (how) {
            null -> ProjectMode()
            is ImperfectParameters -> ProjectMode().mergeHigherPriority(how)
            else -> how
        }
//        Logger.da(LogTags.PICK) { " FINAL PICK HOW:[${context.uri}]  \n   >> $result" }
        return result
    }

    fun GlobalConfig.configPickParameters(uri: String): FlavorHows =
        mutableMapOf<String?, PickHow>().apply {
            val config = uris[uri] ?: MavenSource(null, null, null)
            put(null, config.toPickParameters(defaultVariant))
            config.flavors?.forEach { (flavor, maven) ->
                put(flavor, maven.toPickParameters(defaultVariant))
            }
        }

    fun GlobalConfig.tagConfigPickParameters(tagName: String): FlavorHows =
        mutableMapOf<String?, PickHow>().apply {
            tags[tagName]?.let { config ->
                put(null, config.toPickParameters(defaultVariant))
                config.flavors?.forEach { (flavor, maven) ->
                    put(flavor, maven.toPickParameters(defaultVariant))
                }
            }
        }
}