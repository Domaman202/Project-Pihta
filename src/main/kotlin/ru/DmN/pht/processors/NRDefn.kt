package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.ast.NodeInlBodyB
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.processor.utils.*
import ru.DmN.pht.processor.utils.PhtProcessingStage.METHODS_BODY
import ru.DmN.pht.utils.dropMutable
import ru.DmN.pht.utils.meta.MetadataKeys
import ru.DmN.pht.utils.node.NodeTypes.DEFN_
import ru.DmN.pht.utils.node.NodeTypes.INL_BODY_A
import ru.DmN.pht.utils.node.nodeAs
import ru.DmN.pht.utils.vtype.PhtVirtualMethod
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.pushTask
import ru.DmN.siberia.utils.vtype.MethodModifiers
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

object NRDefn : INodeProcessor<INodesList> {
    override fun process(node: INodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeDefn {
        val type = ctx.clazz as PhtVirtualType.Impl
        val typeName = type.name
        //
        val gens = processor.computeListOr(node.nodes[0], ctx)
        val offset = if (gens == null) 0 else 1
        val name = processor.computeString(node.nodes[offset], ctx)
        //
        val generics =
            if (node.getMetadata(MetadataKeys.STATIC) != true)
                type.genericsDefine.toMutableMap()
            else HashMap()
        gens?.forEach {
            val generic = processor.computeList(it, ctx)
            generics += Pair(
                "${processor.computeString(generic[0], ctx)}$$typeName",
                processor.computeType(generic[1], ctx)
            )
        }
        //
        val returnGen = processor.computeGenericType(node.nodes[1 + offset], ctx)?.let { "$it$$typeName" }
        val returnType =
            if (returnGen == null)
                processor.computeType(node.nodes[1 + offset], ctx)
            else generics[returnGen]!!
        val args = parseArguments(node.nodes[2 + offset], typeName, generics, processor, ctx)
        //
        val method = PhtVirtualMethod.Impl(
            type,
            name,
            returnType,
            returnGen,
            args.first,
            args.second,
            args.third,
            MethodModifiers(final = true),
            generator = null,
            extension = null,
            inline = null,
            generics = generics
        )
        type.methods += method
        //
        val new = NodeDefn(
            node.info.withType(DEFN_),
            if (node.nodes.size > 3 + offset)
                node.nodes.dropMutable(3 + offset)
            else ArrayList(),
            method
        )
        //
        if (node.nodes.size > 3) {
            processor.pushTask(METHODS_BODY, node) {
                processMethodBody(new, method, processor, ctx)
            }
        }
        return new
    }

    fun processMethodBody(new: NodeNodesList, method: PhtVirtualMethod.Impl, processor: Processor, ctx: ProcessingContext) {
        val info = new.info
        val ret = method.rettype
        val void = ret != VirtualType.VOID
        //
        if (void)
            new.nodes[new.nodes.lastIndex] = nodeAs(info, new.nodes.last(), ret.name)
        val context = ctx.with(method).with(BodyContext.of(method))
        if (method.modifiers.inline)
            method.inline = NodeInlBodyB(info.withType(INL_BODY_A), new.copyNodes(), ret, context)
        processNodes(method, void, new, processor, context)
    }

    private fun processNodes(method: VirtualMethod, void: Boolean, new: NodeNodesList, processor: Processor, ctx: ProcessingContext) {
        var i = 0
        while (i < new.nodes.size.let { if (void) it - 1 else it }) {
            val it = processor.process(new.nodes[i], ctx, false)
            if (it == null) {
                new.nodes.removeAt(i)
                i--
            } else new.nodes[i] = it
            i++
        }
        //
        if (new.nodes.isNotEmpty() && void) {
            new.nodes[new.nodes.lastIndex] = NRAs.process(nodeAs(new.info, new.nodes.last(), method.rettype.name), processor, ctx, true)!!
        }
    }

    fun parseArguments(list: Node, decl: String, generics: Map<String, VirtualType>, processor: Processor, ctx: ProcessingContext): Triple<MutableList<VirtualType>, MutableList<String>, MutableList<String?>> {
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
                    val generic = "${type.substring(0, type.length - 1)}$$decl"
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