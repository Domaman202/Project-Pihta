package ru.DmN.pht.utils

import ru.DmN.pht.jvm.utils.vtype.JRTP
import ru.DmN.pht.utils.IPhtPlatform.Organization.FILES
import ru.DmN.pht.utils.IPhtPlatform.Organization.PACKAGES
import ru.DmN.pht.utils.IPhtPlatform.Paradigm.FUNCTIONAL
import ru.DmN.pht.utils.IPhtPlatform.Paradigm.OBJECTIVE
import ru.DmN.pht.utils.IPhtPlatform.Reflection.SUPPORTED
import ru.DmN.pht.utils.IPhtPlatform.Typing.STATIC
import ru.DmN.siberia.utils.IPlatform
import ru.DmN.siberia.utils.vtype.TypesProvider
import ru.DmN.siberia.utils.vtype.TypesProvider.VoidTypesProvider

/**
 * Стандартные целевые платформы.
 */
enum class Platforms(
    override val typing: IPhtPlatform.Typing,
    override val reflection: IPhtPlatform.Reflection,
    override val paradigm: IPhtPlatform.Paradigm,
    override val organization: IPhtPlatform.Organization
) : IPhtPlatform {
    /**
     * Java Virtual Machine (Java / Kotlin / Groovy / Scala)
     */
    JVM(STATIC, SUPPORTED, OBJECTIVE, PACKAGES),

    /**
     * Lua
     */
    LUA(STATIC, SUPPORTED, FUNCTIONAL, FILES);

    companion object {
        init {
            IPlatform.PLATFORMS += JVM
            IPlatform.PLATFORMS += LUA
            TypesProvider.add(JVM, ::JRTP)
            TypesProvider.add(LUA, ::VoidTypesProvider)
        }
    }
}