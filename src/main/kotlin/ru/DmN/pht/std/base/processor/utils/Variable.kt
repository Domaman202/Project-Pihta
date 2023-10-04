package ru.DmN.pht.std.base.processor.utils

import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.VirtualType

data class Variable(
    val name: String,
    val type: VirtualType,
    val value: Node?,
    val id: Int
)