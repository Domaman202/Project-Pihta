package ru.DmN.pht.utils

import ru.DmN.pht.jvm.utils.vtype.JRTP
import ru.DmN.siberia.utils.IPlatform
import ru.DmN.siberia.utils.vtype.TypesProvider
import ru.DmN.siberia.utils.vtype.TypesProvider.VoidTypesProvider

/**
 * Стандартные целевые платформы.
 */
enum class Platforms : IPlatform {
    /**
     * Java Virtual Machine (Java / Kotlin / Groovy / Scala)
     */
    JVM,

    /**
     * Lua
     */
    LUA;

    companion object {
        init {
            IPlatform.PLATFORMS += JVM
            IPlatform.PLATFORMS += LUA
            TypesProvider.add(JVM, ::JRTP)
            TypesProvider.add(LUA, ::VoidTypesProvider)
        }
    }
}