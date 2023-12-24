package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.MethodModifiers
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

object NRCtor : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeDefn {
        val gctx = ctx.global
        val type = ctx.clazz as VirtualType.VirtualTypeImpl
        //
        val args = NRDefn.parseArguments(node.nodes[0], type.generics, processor, ctx, gctx)
        //
        val method = VirtualMethod.VirtualMethodImpl(
            type,
            "<init>",
            VirtualType.VOID,
            null,
            args.first,
            args.second,
            args.third,
            MethodModifiers(ctor = true),
            null,
            type.generics
        )
        type.methods += method
        //
        val new = NodeDefn(node.info.withType(NodeTypes.CTOR_), node.nodes.drop(1).toMutableList(), method)
        processor.stageManager.pushTask(ProcessingStage.METHODS_BODY) {
            processNodesList(
                new,
                processor,
                ctx.with(method).with(BodyContext.of(method)),
                mode
            )
        }
        return new
    }
}