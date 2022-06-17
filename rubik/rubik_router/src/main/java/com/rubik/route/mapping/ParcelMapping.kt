package com.rubik.route.mapping

import android.os.Parcel
import android.os.Parcelable
import com.rubik.logger.Logger
import com.rubik.route.exception.ParcelableNoCreatorException
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.Array as RefArray

internal fun Any.toParcelable(type: Type): Any? {
    return toParcelableRValue(type) ?: toParcelableList(type) ?: toParcelableArray(type)
}

private fun Any.toParcelableRValue(type: Type): Any? {
    if (this is Parcelable) {
        val creator = type.parcelableCreator
        if (null != creator) {
            Parcel.obtain().let { parcel ->
                parcel.setDataPosition(0)
                this.writeToParcel(parcel, 0)
                parcel.setDataPosition(0)
                return (creator.createFromParcel(parcel)).apply {
                    parcel.recycle()
                    Logger.da(" RUBIK ") { " parcel rvalue : $this" }
                }
            }
        } else {
            throw ParcelableNoCreatorException(type.toString())
        }
    }
    return null
}


private fun Any.toParcelableArray(type: Type): Any? {
    if (this is Array<*> && type is GenericArrayType) {
        val genericType = type.genericComponentType as? Class<*>
        if (null != genericType) {
            val creator = genericType.parcelableCreator
            if (null != creator) {
//                val result = RefArray.newInstance(genericType, size)
                val result = creator.newArray(size)
                Parcel.obtain().let { parcel ->
                    this.forEachIndexed { index, value ->
                        parcel.setDataPosition(index)
                        (value as? Parcelable)?.writeToParcel(parcel, 0)
                        parcel.setDataPosition(index)
                        creator.createFromParcel(parcel)?.let {
                            RefArray.set(result, index, it)
                        }
                    }
                    return result.apply {
                        parcel.recycle()
                        Logger.da(" RUBIK ") { " parcel array(${this.size}): $this" }
                    }
                }
            }
        }
    }
    return null
}


private fun Any.toParcelableList(type: Type): Any? {
    if (this is List<*> && type is ParameterizedType) {
        val creator = type.firstTypeArgument?.parcelableCreator
        if (null != creator) {
            val result = mutableListOf<Any?>()
            Parcel.obtain().let { parcel ->
                this.forEachIndexed { index, value ->
                    parcel.setDataPosition(index)
                    (value as? Parcelable)?.writeToParcel(parcel, 0)
                    parcel.setDataPosition(index)
                    creator.createFromParcel(parcel)?.let {
                        result.add(index, it)
                    }
                }
                return result.apply {
                    parcel.recycle()
                    Logger.da(" RUBIK ") { " parcel list(${this.size}): $this" }
                }
            }
        }
    }
    return null
}

private val Type.parcelableCreator: Parcelable.Creator<*>?
    get() {
        val creator = try {
            (this as? Class<*>)?.getField("CREATOR")
        } catch (e: NoSuchFieldException) {
            null
        }
        return if (null != creator && Modifier.isStatic(creator.modifiers)) {
            creator.get(null) as? Parcelable.Creator<*>
        } else {
            null
        }
    }