package ru.DmN.pht.utils

import ru.DmN.siberia.utils.IPlatform

interface IPhtPlatform : IPlatform {
    val typing: Typing
    val reflection: Reflection
    val paradigm: Paradigm
    val organization: Organization

    enum class Typing {
        STATIC,
        DYNAMIC,
        DYNAMIC_SUPPORTED
    }

    enum class Reflection {
        UNSUPPORTED,
        SUPPORTED
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