package ru.DmN.pht.utils

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

class InlineVariable(name: String, id: Int, val value: Node) : Variable(name, VirtualType.VOID, id, false)