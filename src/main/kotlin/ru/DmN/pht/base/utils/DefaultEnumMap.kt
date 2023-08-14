package ru.DmN.pht.base.utils

import java.util.EnumMap

class DefaultEnumMap<K : Enum<K>, V>(keyType: Class<K>, val default: () -> V) : EnumMap<K, V>(keyType) {
    override fun get(key: K): V =
        super.get(key) ?: default().apply { set(key, this) }
}