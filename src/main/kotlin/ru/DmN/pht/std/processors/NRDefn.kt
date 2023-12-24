package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.ctx.GlobalContext
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeAs
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.MethodModifiers
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualMethod.VirtualMethodImpl
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.VirtualType.VirtualTypeImpl

object NRDefn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeDefn {
        val gctx = ctx.global
        val type = ctx.clazz as VirtualTypeImpl
        //
        val gens = processor.computeListOr(node.nodes[0], ctx)
        val offset = if (gens == null) 0 else 1
        val generics = type.generics.toMutableList()
        gens?.forEach {
            val generic = processor.computeList(it, ctx)
            generics += Pair(processor.computeString(generic[0], ctx), processor.computeType(generic[1], ctx))
        }
        //
        val name = processor.computeString(node.nodes[offset], ctx)
        val returnGen = processor.computeGenericType(node.nodes[1 + offset], ctx)
        val returnType =
            if (returnGen == null)
                processor.computeType(node.nodes[1 + offset], ctx)
            else generics.find { it.first == returnGen }!!.second
        val args = parseArguments(node.nodes[2 + offset], generics, processor, ctx, gctx)
        //
        val method = VirtualMethodImpl(
            type,
            name,
            returnType,
            returnGen,
            args.first,
            args.second,
            args.third,
            MethodModifiers(),
            null,
            generics, // todo:
        )
        type.methods += method
        //
        val new = NodeDefn(
            node.info.withType(NodeTypes.DEFN_),
            if (node.nodes.size > 3 + offset)
                node.nodes.drop(3 + offset).toMutableList()
            else ArrayList(),
            method
        )
        if (node.nodes.size > 3) {
            processor.stageManager.pushTask(ProcessingStage.METHODS_BODY) {
                processNodes(method, new, processor, ctx.with(method).with(BodyContext.of(method)))
            }
        }
        return new
    }

    fun processNodes(method: VirtualMethod, new: NodeNodesList, processor: Processor, ctx: ProcessingContext) {
        var i = 0
        while (i < new.nodes.size.let { if (method.rettype != VirtualType.VOID) it - 1 else it }) {
            val it = processor.process(new.nodes[i], ctx, ValType.NO_VALUE)
            if (it == null) {
                new.nodes.removeAt(i)
                i--
            } else new.nodes[i] = it
            i++
        }
        //
        if (new.nodes.isNotEmpty() && method.rettype != VirtualType.VOID) {
            new.nodes[new.nodes.lastIndex] = NRAs.process(
                nodeAs(new.info, new.nodes.last(), method.rettype.name),
                processor,
                ctx,
                ValType.VALUE
            )!!
        }
    }

    fun parseArguments(list: Node, generics: List<Pair<String, VirtualType>>, processor: Processor, ctx: ProcessingContext, gctx: GlobalContext): Triple<MutableList<VirtualType>, MutableList<String>, MutableList<String?>> {
        val argsc = ArrayList<VirtualType>()
        val argsn = ArrayList<String>()
        val argsg = ArrayList<String?>()
        processor.computeList(list, ctx)
            .stream()
            .map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }
            .forEach { it ->
                argsn += it.first()
                val type = it.last()
                if (type.endsWith('^')) {
                    val generic = type.substring(0, type.length - 1)
                    argsc += generics.find { it.first == generic }!!.second
                    argsg += generic
                } else {
                    argsc += gctx.getType(type, processor.tp)
                    argsg += null
                }
            }
        return Triple(argsc, argsn, argsg)
    }
}