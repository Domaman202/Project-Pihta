package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeInlBodyB(info: INodeInfo, nodes: MutableList<Node>, type: VirtualType?, val ctx: ProcessingContext) : NodeInlBodyA(info, nodes, type) {
    override fun copy(): NodeInlBodyB =
        NodeInlBodyB(info, copyNodes(), type, ctx)
}