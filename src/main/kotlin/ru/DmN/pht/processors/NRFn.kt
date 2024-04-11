package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFn
import ru.DmN.pht.ast.NodeInlBodyA
import ru.DmN.pht.processor.ctx.body
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.compute
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeStringNodes
import ru.DmN.pht.utils.*
import ru.DmN.pht.utils.node.*
import ru.DmN.pht.utils.node.NodeTypes.FN_
import ru.DmN.pht.utils.node.NodeTypes.INL_BODY_A
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage.FINALIZATION
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.IPlatform.UNIVERSAL
import ru.DmN.siberia.utils.exception.pushTask
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType
import kotlin.math.absoluteValue

object NRFn : INodeProcessor<NodeNodesList>, IInlinableProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(if (node.nodes[0].isConstClass) processor.computeString(node.nodes[0], ctx) else "Any")

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeFn {
        val gctx = ctx.global
        val cctx = ctx.clazz
        val bctx = ctx.body
        val offset = if (node.nodes[0].isConstClass) 1 else 0
        val type = if (offset == 1) gctx.getType(node.nodes[0].valueAsString) else null
        val refs = processor.computeStringNodes(processor.compute(node.nodes[offset], ctx) as INodesList, ctx)
            .map { ref -> bctx[ref]?.let { NVC.of(it) } ?: NVC.of(cctx.fields.find { it.name == ref }!!) }
        val args = processor.computeStringNodes(processor.compute(node.nodes[offset + 1], ctx) as INodesList, ctx)
        val body = LazyProcessValueList(node, processor, ctx).drop(offset + 2)
        val new = NodeFn(node.info.withType(FN_), body, type, args, gctx.name("PhtLambda\$${node.info.hashCode().absoluteValue}"), refs)
        if (ctx.platform != UNIVERSAL) {
            processor.pushTask(FINALIZATION, node) {
                finalize(node.info, new, processor, ctx)
            }
        }
        return new
    }

    override fun isInlinable(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Boolean =
        true

    override fun inline(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Node =
        NodeInlBodyA(node.info.withType(INL_BODY_A), node.nodes.drop(3).toMutableList(), null)

    private fun finalize(info: INodeInfo, node: NodeFn, processor: Processor, ctx: ProcessingContext) {
        val type = node.type!!
        val method = findLambdaMethod(type)
        val defnNode = nodeDefn(
            info,
            method.name,
            method.rettype.name,
            method.argsn.mapIndexed { i, it -> Pair(it, method.argsc[i].name) },
            node.source
        )
        if (node.refs.isEmpty()) {
            node.processed = mutableListOf(
                processor.process(
                    nodeObj(
                        info,
                        node.name,
                        if (type.isInterface)
                            listOf("java.lang.Object", type.name)
                        else listOf(type.name),
                        listOf(defnNode)
                    ),
                    ctx,
                    true
                )!!
            )
        } else {
            val fields = mutableListOf<Pair<String, String>>()
            val ctorBody = mutableListOf<Node>(nodeCcall(info))
            val ctorArgs = node.refs.map {
                fields += Pair(it.name, it.type.name)
                ctorBody += nodeInitFld(info, it.name, type)
                Pair(it.name, it.type.name)
            }
            node.processed = mutableListOf(
                nodeCls(
                    info,
                    node.name,
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
                nodeNew(info, node.name, node.refs.map { nodeGetOrName(info, it.name) })
            )
            processNodesList(node, processor, ctx, true)
        }
    }
}