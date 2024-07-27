package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.utils.*
import ru.DmN.pht.processors.NRDefn.parseArguments
import ru.DmN.pht.utils.dropMutable
import ru.DmN.pht.utils.meta.MetadataKeys
import ru.DmN.pht.utils.node.NodeTypes.GFN_
import ru.DmN.pht.utils.vtype.GeneratorVirtualMethod
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.MethodModifiers

object NRGFn : INodeProcessor<INodesList> {
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
        val method = GeneratorVirtualMethod(
            type,
            name,
            returnType,
            returnGen,
            args.first,
            args.second,
            args.third,
            MethodModifiers(final = true, generator = true),
            extension = null,
            generator = node.nodes.dropMutable(3 + offset),
            generics,
        )
        type.methods += method
        //
        return NodeDefn(node.info.withType(GFN_), node.nodes.dropMutable(3 + offset), method) // Fake Nodes List =)
    }
}