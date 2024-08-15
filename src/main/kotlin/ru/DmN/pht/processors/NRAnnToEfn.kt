package ru.DmN.pht.processors

import ru.DmN.pht.ast.IMetaNodesList
import ru.DmN.pht.ast.IVMProviderNode
import ru.DmN.pht.ast.NodeMetaNodesList
import ru.DmN.pht.utils.node.NodeTypes.PROGN_B_
import ru.DmN.pht.utils.node.nodeToEFn
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

object NRAnnToEfn : INodeProcessor<NodeMetaNodesList> {
    override fun process(
        node: NodeMetaNodesList,
        processor: Processor,
        ctx: ProcessingContext,
        valMode: Boolean
    ): Node {
        val nodes = ArrayList<Node>()
        val methods = ArrayList<VirtualMethod>()
//        node.nodes.forEach { it ->
//            if (it is IVMProviderNode<*>) {
//                methods += it.method
//                processor.process(it, ctx, false)?.let(nodes::add)
//            } else {
//                processor.process(it, ctx, false)?.let {
//                    if (it is IVMProviderNode<*>)
//                        methods += it.method
//                    nodes += it
//                }
//            }
//        }
        forEachAllMetaNodesListA(node, nodes, processor, ctx, valMode) {
            if (it is IVMProviderNode<*>) {
                methods += it.method
                false
            } else true
        }
        val info = node.info
        methods.forEach {
            processor.process(
                nodeToEFn(info, it.declaringClass.name, it.name, it.argsc.map(VirtualType::name)),
                ctx,
                false
            )?.let(nodes::add)
        }
        return NodeMetaNodesList(info.withType(PROGN_B_), node.metadata, nodes)
    }

    fun forEachAllMetaNodesListA(node: IMetaNodesList, nodes: MutableList<Node>, processor: Processor, ctx: ProcessingContext, valMode: Boolean, block: (Node) -> Boolean) {
        node.nodes.forEach { it ->
            if (it is IMetaNodesList) {
                var flag = false
                it.nodes.forEach {
                    if (forEachAllMetaNodesListB(it, block)) {
                        flag = true
                    }
                }
                processor.process(it, ctx, valMode)?.let {
                    nodes += it
                    if (flag) {
                        forEachAllMetaNodesListB(it, block)
                    }
                }
            } else {
                val flag = block(it)
                processor.process(it, ctx, valMode)?.let {
                    nodes += it
                    if (flag) {
                        block(it)
                    }
                }
            }
        }
    }

    fun forEachAllMetaNodesListB(node: Node, block: (Node) -> Boolean): Boolean =
        if (node is IMetaNodesList) {
            var flag = false
            node.nodes.forEach {
                if (forEachAllMetaNodesListB(it, block)) {
                    flag = true
                }
            }
            flag
        } else block(node)
}