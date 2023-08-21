package ru.DmN.pht.std.base.ast

interface IStaticVariantNode {
    var static: Boolean
        get() = false
        set(value) = throw UnsupportedOperationException()
}