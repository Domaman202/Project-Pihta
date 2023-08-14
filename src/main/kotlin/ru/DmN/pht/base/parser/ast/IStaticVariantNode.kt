package ru.DmN.pht.base.parser.ast

interface IStaticVariantNode {
    var static: Boolean
        get() = false
        set(value) = throw UnsupportedOperationException()
}