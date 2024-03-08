package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.ast.NodeInlBodyB
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.utils.*
import ru.DmN.pht.utils.node.NodeTypes.DEFN_
import ru.DmN.pht.utils.node.NodeTypes.INL_BODY_A
import ru.DmN.pht.utils.node.nodeAs
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage.METHODS_BODY
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.MethodModifiers
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualMethod.VirtualMethodImpl
import ru.DmN.siberia.utils.vtype.VirtualType
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.VOID
import ru.DmN.siberia.utils.vtype.VirtualType.VirtualTypeImpl

object NRDefn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeDefn {
        val type = ctx.clazz as VirtualTypeImpl
        //
        val gens = processor.computeListOr(node.nodes[0], ctx)
        val offset = if (gens == null) 0 else 1
        val generics = type.generics.toMutableMap()
        gens?.forEach {
            val generic = processor.computeList(it, ctx)
            generics[processor.computeString(generic[0], ctx)] = processor.computeType(generic[1], ctx)
        }
        //
        val name = processor.computeString(node.nodes[offset], ctx)
        val returnGen = processor.computeGenericType(node.nodes[1 + offset], ctx)
        val returnType =
            if (returnGen == null)
                processor.computeType(node.nodes[1 + offset], ctx)
            else generics[returnGen]!!
        val args = parseArguments(node.nodes[2 + offset], generics, processor, ctx)
        //
        val method = VirtualMethodImpl(
            type,
            name,
            returnType,
            returnGen,
            args.first,
            args.second,
            args.third,
            MethodModifiers(final = true),
            null,
            null,
            generics
        )
        type.methods += method
        //
        val new = NodeDefn(
            node.info.withType(DEFN_),
            if (node.nodes.size > 3 + offset)
                node.nodes.drop(3 + offset).toMutableList()
            else ArrayList(),
            method
        )
        //
        if (node.nodes.size > 3) {
            processor.stageManager.pushTask(METHODS_BODY) {
                val context = ctx.with(method).with(BodyContext.of(method))
                if (method.modifiers.inline)
                    method.inline = NodeInlBodyB(node.info.withType(INL_BODY_A), new.nodes.toMutableList(), method.rettype, context)
                processNodes(method, new, processor, context)
            }
        }
        return new
    }

    private fun processNodes(method: VirtualMethod, new: NodeNodesList, processor: Processor, ctx: ProcessingContext) {
        var i = 0
        while (i < new.nodes.size.let { if (method.rettype != VOID) it - 1 else it }) {
            val it = processor.process(new.nodes[i], ctx, false)
            if (it == null) {
                new.nodes.removeAt(i)
                i--
            } else new.nodes[i] = it
            i++
        }
        //
        if (new.nodes.isNotEmpty() && method.rettype != VOID) {
            new.nodes[new.nodes.lastIndex] = NRAs.process(
                nodeAs(new.info, new.nodes.last(), method.rettype.name),
                processor,
                ctx,
                true
            )!!
        }
    }

    fun parseArguments(list: Node, generics: Map<String, VirtualType>, processor: Processor, ctx: ProcessingContext): Triple<MutableList<VirtualType>, MutableList<String>, MutableList<String?>> {
        val argsc = ArrayList<VirtualType>()
        val argsn = ArrayList<String>()
        val argsg = ArrayList<String?>()
        processor.computeList(list, ctx)
            .stream()
            .map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }
            .forEach {
                argsn += it.first()
                val type = it.last()
                if (type.endsWith('^')) {
                    val generic = type.substring(0, type.length - 1)
                    argsc += generics[generic]!!
                    argsg += generic
                } else {
                    argsc += NRValue.computeType(type, processor, ctx)
                    argsg += null
                }
            }
        return Triple(argsc, argsn, argsg)
    }
}