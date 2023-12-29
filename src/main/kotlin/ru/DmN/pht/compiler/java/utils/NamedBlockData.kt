package ru.DmN.pht.std.compiler.java.utils

import org.objectweb.asm.Label

data class NamedBlockData(
    val start: Label,
    val stop: Label
)
