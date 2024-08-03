package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.processor.utils.PhtProcessingStage.METHODS_BODY
import ru.DmN.pht.utils.node.NodeParsedTypes
import ru.DmN.pht.utils.type
import ru.DmN.pht.utils.vtype.PhtVirtualMethod
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.pushOrRunTask
import ru.DmN.siberia.utils.vtype.MethodModifiers
import ru.DmN.siberia.utils.vtype.VirtualType

object NRECtor : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeDefn {
        val type = ctx.clazz as PhtVirtualType.Impl
        //
        val args = NRDefn.parseArguments(node.nodes[0], type.name, type.genericsDefine, processor, ctx)
        //
        args.first.add(0, VirtualType.INT)
        args.first.add(0, ctx.global.getType("String"))
        args.second.add(0, "\$order")
        args.second.add(0, "\$name")
        args.third.add(0, null)
        args.third.add(0, null)
        //
        val method = PhtVirtualMethod.Impl(
            type,
            "<init>",
            VirtualType.VOID,
            null,
            args.first,
            args.second,
            args.third,
            MethodModifiers(ctor = true),
            generator = null,
            extension = null,
            inline = null,
            generics = type.genericsDefine
        )
        type.methods += method
        //
        val new = NodeDefn(node.info.withType((node.type as NodeParsedTypes).processed), node.nodes.drop(1).toMutableList(), method)
        processor.pushOrRunTask(METHODS_BODY, node) {
            processNodesList(new, processor, ctx.with(method).with(BodyContext.of(method)), false)
        }
        return new
    }
}