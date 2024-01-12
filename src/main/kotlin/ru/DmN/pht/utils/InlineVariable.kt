package ru.DmN.pht.utils

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.Variable

class InlineVariable(name: String, id: Int, val value: Node) : Variable(name, null, id, false)