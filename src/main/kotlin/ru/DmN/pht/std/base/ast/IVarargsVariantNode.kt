package ru.DmN.pht.std.base.ast

interface IVarargsVariantNode {
    var varargs: Boolean
        get() = false
        set(value) = throw UnsupportedOperationException()
}