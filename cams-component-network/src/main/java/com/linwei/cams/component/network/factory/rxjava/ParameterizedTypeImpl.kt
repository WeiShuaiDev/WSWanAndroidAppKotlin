package com.linwei.cams.component.network.factory.rxjava

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*

class ParameterizedTypeImpl(
    private val actualTypeArguments: Array<Type>?,
    private val ownerType: Type?,
    private val rawType: Type?
) : ParameterizedType {

    override fun getActualTypeArguments(): Array<Type>? {
        return actualTypeArguments
    }

    override fun getOwnerType(): Type? {
        return ownerType
    }

    override fun getRawType(): Type? {
        return rawType
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val that = other as ParameterizedTypeImpl

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(actualTypeArguments, that.actualTypeArguments)) {
            return false
        }
        if (ownerType == null) {
            return that.ownerType == null
        }
        if (ownerType != that.ownerType) {
            return false
        }
        return if (rawType == null) {
            that.rawType == null
        } else rawType == that.rawType
    }

    override fun hashCode(): Int {
        var result = if (actualTypeArguments != null) Arrays.hashCode(
            actualTypeArguments
        ) else 0
        result = 31 * result + (ownerType?.hashCode() ?: 0)
        result = 31 * result + (rawType?.hashCode() ?: 0)
        return result
    }
}