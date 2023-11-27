package ru.DmN.siberia.utils

import java.util.EnumMap

/**
 * EnumMap, но с предоставлением результата по умолчанию.
 */
class DefaultEnumMap<K : Enum<K>, V>(keyType: Class<K>, val default: () -> V) : EnumMap<K, V>(keyType) {
    override fun get(key: K): V =
        super.get(key) ?: default().apply { set(key, this) }
}