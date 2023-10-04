package ru.DmN.pht.base.utils

data class MethodModifiers(
    var varargs: Boolean = false,
    var ctor: Boolean = false,
    var static: Boolean = false,
    var abstract: Boolean = false,
    var extend: Boolean = false
)
