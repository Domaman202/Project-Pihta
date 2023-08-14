package ru.DmN.pht.std.ast

interface IVarargsVariantNode {
    var varargs: Boolean
        get() = false
        set(value) = throw UnsupportedOperationException()
}