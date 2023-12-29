package ru.DmN.pht.std.imports.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeAliasType(info: INodeInfo, val type: String, val new: String) : Node(info)