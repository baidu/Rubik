package com.rubik.plugins.module.config.extension

import com.rubik.plugins.module.config.record.VariantConfigRecord

/**
 *  Rubik module extension of gradle plugins.
 *
 *  @since 1.8
 */
open class RecordExtension(private val variantRecord: VariantConfigRecord) {

    fun token(value: String) {
        variantRecord.token = value
    }

}