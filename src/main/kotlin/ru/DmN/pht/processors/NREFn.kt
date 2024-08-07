package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.ast.NodeInlBodyB
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.processor.utils.*
import ru.DmN.pht.processor.utils.PhtProcessingStage.METHODS_BODY
import ru.DmN.pht.utils.node.NodeTypes.EFN_
import ru.DmN.pht.utils.node.NodeTypes.INL_BODY_A
import ru.DmN.pht.utils.vtype.PhtVirtualMethod
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.pushOrRunTask
import ru.DmN.siberia.utils.vtype.MethodModifiers
import ru.DmN.siberia.utils.vtype.VirtualType

object NREFn : INodeProcessor<INodesList> {
    override fun process(node: INodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeDefn {
        val type = ctx.clazz as PhtVirtualType.Impl
        //
        val gens = processor.computeListOr(node.nodes[0], ctx)
        val offset = if (gens == null) 0 else 1
        val generics = type.genericsDefine.toMutableMap()
        gens?.forEach {
            val generic = processor.computeList(it, ctx)
            generics[processor.computeString(generic[0], ctx)] = processor.computeType(generic[1], ctx)
        }
        //
        val extend = processor.computeType(node.nodes[offset], ctx)
        val name = processor.computeString(node.nodes[1 + offset], ctx)
        val returnGen = processor.computeGenericType(node.nodes[2 + offset], ctx)
        val returnType =
            if (returnGen == null)
                processor.computeType(node.nodes[2 + offset], ctx)
            else generics[returnGen]!!
        val args = NRDefn.parseArguments(node.nodes[3 + offset], type.name, generics, processor, ctx)
        //
        args.first.add(0, extend)
        args.second.add(0, "this")
        args.third.add(0, null)
        //
        val method = PhtVirtualMethod.Impl(
            type,
            name,
            returnType,
            returnGen,
            args.first,
            args.second,
            args.third,
            MethodModifiers(static = true, extension = true),
            generator = null,
            extension = extend,
            inline = null,
            generics = generics
        )
        type.methods += method
        ctx.global.getExtensions(extend) += method
        //
        val new = NodeDefn(node.info.withType(EFN_), node.nodes.drop(4 + offset).toMutableList(), method)
        //
        processor.pushOrRunTask(METHODS_BODY, node) {
            val context = ctx.with(method).with(BodyContext.of(method))
            if (method.modifiers.inline)
                method.inline = NodeInlBodyB(node.info.withType(INL_BODY_A), new.nodes.toMutableList(), method.rettype, context)
            processNodesList(
                new,
                processor,
                context,
                method.rettype != VirtualType.VOID
            )
        }
        //
        return new
    }
}