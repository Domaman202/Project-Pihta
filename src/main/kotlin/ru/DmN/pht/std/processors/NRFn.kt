package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.Platform
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.platform
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeDefn
import ru.DmN.pht.std.processor.utils.nodeObj
import ru.DmN.pht.std.ups.NUPDefn
import ru.DmN.pht.std.utils.compute
import kotlin.math.absoluteValue

object NRFn : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType {
        val itf = processor.compute(node.nodes[0], ctx)
        return ctx.global.getType(
            if (itf.isConstClass())
                itf.getConstValueAsString()
            else "kotlin.jvm.functions.Function${NUPDefn.parseArguments(node.nodes[0], processor, ctx).first.size}",
            processor.tp
        )
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        if (ctx.platform == Platform.JAVA) {
            val gctx = ctx.global
            //
            val itfN = processor.compute(node.nodes[0], ctx)
            val offset = if (itfN.isConstClass()) 1 else 0
            val args = NUPDefn.parseArguments(node.nodes[offset], processor, ctx).second
            val itf = gctx.getType(
                if (offset == 1)
                    itfN.getConstValueAsString()
                else "kotlin.jvm.functions.Function${args.size}",
                processor.tp
            )
            //
            val method = itf.methods
                .asSequence()
                .filter { it.declaringClass == itf }
                .filter { it.argsc.size == args.size || it.modifiers.varargs && args.size > it.argsc.size }
                .first()
            //
            val line = node.token.line
            NRClass.process(
                nodeObj(
                    line,
                    gctx.name("Lambda${node.hashCode().absoluteValue}"),
                    listOf("java.lang.Object", itf.name),
                    mutableListOf(
                        nodeDefn(
                            line,
                            method.name,
                            method.rettype.name,
                            method.argsn.mapIndexed { i, it -> Pair(it, method.argsc[i].name) },
                            node.nodes.drop(offset + 1)
                        )
                    )
                ), processor, ctx, mode
            )
        } else node
}