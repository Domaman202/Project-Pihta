package ru.DmN.pht.utils

import ru.DmN.siberia.utils.IPlatform

interface IPhtPlatform : IPlatform {
    val typing: Typing
    val reflection: Boolean
    val paradigm: Paradigm
    val organization: Organization

    enum class Typing(val isStatic: Boolean, val isDynamic: Boolean) {
        STATIC(true, false),
        DYNAMIC(false, true),
        DYNAMIC_SUPPORTED(true, true)
    }

    enum class Paradigm {
        FUNCTIONAL,
        OBJECTIVE
    }

    enum class Organization {
        FILES,
        MODULES,
        PACKAGES
    }
}