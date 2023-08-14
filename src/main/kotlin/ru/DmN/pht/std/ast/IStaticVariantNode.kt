package ru.DmN.pht.std.ast

interface IStaticVariantNode {
    var static: Boolean
        get() = false
        set(value) = throw UnsupportedOperationException()
}