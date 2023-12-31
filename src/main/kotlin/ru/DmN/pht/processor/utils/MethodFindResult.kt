package ru.DmN.pht.processor.utils

import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

data class MethodFindResult(
    val type: NodeMCall.Type,
    val args: List<Node>,
    val method: VirtualMethod,
    val generics: VirtualType?,
    val strict: Boolean
)