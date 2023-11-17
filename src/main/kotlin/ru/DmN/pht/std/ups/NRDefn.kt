package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ProcessingStage
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.MethodModifiers
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualMethod.VirtualMethodImpl
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.VirtualType.VirtualTypeImpl
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
        val type = ctx.clazz as VirtualTypeImpl
        //
        val name = processor.computeString(node.nodes[0], ctx)
        val returnType = processor.computeString(node.nodes[1], ctx)
        val args = parseArguments(node.nodes[2], processor, ctx)
        //
        val method = VirtualMethodImpl(
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
            if (node.nodes.size > 3)
                node.nodes.drop(3).toMutableList()
            else ArrayList(),
            method
        )
        if (node.nodes.size > 3) {
            processor.pushTask(ctx, ProcessingStage.METHODS_BODY) {
                processNodes(method, new, processor, ctx.with(method).with(BodyContext.of(method)))
            }
        }
        return new
    }

    fun processNodes(method: VirtualMethod, new: NodeNodesList, processor: Processor, ctx: ProcessingContext) {
        var i = 0
        while (i < new.nodes.size - 1) {
            val it = processor.process(new.nodes[i], ctx, ValType.NO_VALUE)
            if (it == null) {
                new.nodes.removeAt(i)
                i--
            } else new.nodes[i] = it
            i++
        }
        if (new.nodes.isNotEmpty()) {
            if (method.rettype == VirtualType.VOID) {
                val result = processor.process(new.nodes.last(), ctx, ValType.NO_VALUE)
                if (result == null)
                    new.nodes.removeLast()
                else new.nodes[new.nodes.lastIndex] = result
            } else {
                new.nodes[new.nodes.lastIndex] = NRAs.process(
                    nodeAs(new.line, new.nodes.last(), method.rettype.name),
                    processor,
                    ctx,
                    ValType.VALUE
                )!!
            }
        }
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