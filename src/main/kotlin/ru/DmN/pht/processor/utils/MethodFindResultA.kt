package ru.DmN.pht.processor.utils

import ru.DmN.pht.ast.NodeMCall
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

data class MethodFindResultA(
    val type: NodeMCall.Type,
    val args: List<Node>,
    val method: VirtualMethod,
    val generics: VirtualType?,
    val strict: Boolean,
    val compression: Boolean
)