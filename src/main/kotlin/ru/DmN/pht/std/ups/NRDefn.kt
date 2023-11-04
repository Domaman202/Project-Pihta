package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ProcessingStage
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.MethodModifiers
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeAs
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.processors.NRAs
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line

object NRDefn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeDefn {
        val gctx = ctx.global
        val type = ctx.clazz
        //
        val name = processor.computeString(node.nodes[0], ctx)
        val returnType = processor.computeString(node.nodes[1], ctx)
        val args = parseArguments(node.nodes[2], processor, ctx)
        //
        val method = VirtualMethod(
            type,
            name,
            gctx.getType(returnType, processor.tp),
            args.first.map { gctx.getType(it, processor.tp) },
            args.second,
            MethodModifiers()
        )
        type.methods += method
        //
        val line = node.line
        val new = NodeDefn(
            node.token.processed(),
            mutableListOf(
                if (method.rettype == VirtualType.VOID)
                    node.nodes.last()
                else NRAs.process(nodeAs(line, node.nodes.last(), method.rettype.name), processor, ctx, ValType.VALUE)!!
            ),
            method
        )
        processor.pushTask(ctx, ProcessingStage.METHODS_BODY) {
            NRDefault.process(
                new,
                processor,
                ctx.with(method).with(BodyContext.of(method)),
                if (method.rettype == VirtualType.VOID)
                    ValType.NO_VALUE
                else ValType.VALUE
            )
        }
        return new
    }

    fun parseArguments(list: Node, processor: Processor, ctx: ProcessingContext): Pair<MutableList<String>, MutableList<String>> {
        val argsn = ArrayList<String>()
        val argsc = ArrayList<String>()
        processor.computeList(list, ctx)
            .stream()
            .map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }
            .forEach {
                argsn += it.first()
                argsc += it.last()
            }
        return Pair(argsc, argsn)
    }
}