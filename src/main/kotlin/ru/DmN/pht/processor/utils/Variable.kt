package ru.DmN.pht.processor.utils

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.VirtualType

data class Variable(
    val name: String,
    val type: VirtualType,
    val value: Node?,
    val id: Int
)