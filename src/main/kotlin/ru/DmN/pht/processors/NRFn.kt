package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeFn
import ru.DmN.pht.std.node.*
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.*
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType
import kotlin.math.absoluteValue

object NRFn : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(if (node.nodes[0].isConstClass) processor.computeString(node.nodes[0], ctx) else "Any", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFn {
        val gctx = ctx.global
        val cctx = ctx.clazz
        val bctx = ctx.body
        val offset = if (node.nodes[0].isConstClass) 1 else 0
        val type = if (offset == 1) gctx.getType(node.nodes[0].valueAsString, processor.tp) else null
        val refs = processor.computeStringNodes(processor.compute(node.nodes[offset], ctx) as INodesList, ctx)
            .map { ref -> bctx[ref]?.let { NVC.of(it) } ?: NVC.of(cctx.fields.find { it.name == ref }!!) }
        val args = processor.computeStringNodes(processor.compute(node.nodes[offset + 1], ctx) as INodesList, ctx)
        val body = LazyProcessValueList(node, processor, ctx).drop(offset + 2)
        val new = NodeFn(node.info.withType(NodeTypes.FN_), NodeFn.Source(body, type, args, gctx.name("PhtLambda\$${node.info.hashCode().absoluteValue}"), refs))
        if (ctx.platform == Platforms.JAVA) {
            processor.stageManager.pushTask(ProcessingStage.FINALIZATION) {
                finalize(node.info, new, processor, ctx)
            }
        }
        return new
    }

    private fun finalize(info: INodeInfo, node: NodeFn, processor: Processor, ctx: ProcessingContext) {
        node.source.run {
            val type = type!!
            val method = findLambdaMethod(type)
            val defnNode = nodeDefn(
                info,
                method.name,
                method.rettype.name,
                method.argsn.mapIndexed { i, it -> Pair(it, method.argsc[i].name) },
                nodes
            )
            if (refs.isEmpty()) {
                node.processed = mutableListOf(
                    NRClass.process(
                        nodeObj(
                            info,
                            name,
                            if (type.isInterface)
                                listOf("java.lang.Object", type.name)
                            else listOf(type.name),
                            listOf(defnNode)
                        ), processor, ctx, ValType.VALUE
                    )
                )
            } else {
                val fields = mutableListOf<Pair<String, String>>()
                val ctorBody = mutableListOf<Node>(nodeCcall(info))
                val ctorArgs = refs.map {
                    fields += Pair(it.name, it.type.name)
                    ctorBody += nodeInitFld(info, it.name)
                    Pair(it.name, it.type.name)
                }
                node.processed = mutableListOf(
                    nodeCls(
                        info,
                        name,
                        if (type.isInterface)
                            listOf("java.lang.Object", type.name)
                        else listOf(type.name),
                        mutableListOf<Node>(
                            nodeDef(info, fields),
                            nodeCtor(
                                info,
                                ctorArgs,
                                ctorBody
                            ),
                            defnNode
                        )
                    ),
                    nodeNew(info, name, refs.map { nodeGetOrName(info, it.name) })
                )
                processNodesList(node, processor, ctx, ValType.VALUE)
            }
        }
    }
}