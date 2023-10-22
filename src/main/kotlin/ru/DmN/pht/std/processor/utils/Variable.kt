package ru.DmN.pht.std.processor.utils

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.utils.VirtualType

data class Variable(
    val name: String,
    val type: VirtualType,
    val value: Node?,
    val id: Int
)