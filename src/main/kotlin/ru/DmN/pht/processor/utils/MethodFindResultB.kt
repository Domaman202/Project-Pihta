package ru.DmN.pht.processor.utils

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.VirtualMethod

data class MethodFindResultB(
    val args: List<Node>,
    val method: VirtualMethod,
    val compression: Boolean
)