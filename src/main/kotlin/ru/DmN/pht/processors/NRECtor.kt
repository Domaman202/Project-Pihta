package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.utils.clazz
import ru.DmN.pht.processor.utils.global
import ru.DmN.pht.processor.utils.with
import ru.DmN.pht.utils.node.NodeParsedTypes
import ru.DmN.pht.utils.type
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage.METHODS_BODY
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.MethodModifiers
import ru.DmN.siberia.utils.vtype.VirtualMethod.VirtualMethodImpl
import ru.DmN.siberia.utils.vtype.VirtualType
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.INT
import ru.DmN.siberia.utils.vtype.VirtualType.VirtualTypeImpl

object NRECtor : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeDefn {
        val type = ctx.clazz as VirtualTypeImpl
        //
        val args = NRDefn.parseArguments(node.nodes[0], type.generics, processor, ctx)
        //
        args.first.add(0, INT)
        args.first.add(0, ctx.global.getType("String", processor.tp))
        args.second.add(0, "\$order")
        args.second.add(0, "\$name")
        args.third.add(0, null)
        args.third.add(0, null)
        //
        val method = VirtualMethodImpl(
            type,
            "<init>",
            VirtualType.VOID,
            null,
            args.first,
            args.second,
            args.third,
            MethodModifiers(ctor = true),
            null,
            null,
            type.generics
        )
        type.methods += method
        //
        val new = NodeDefn(node.info.withType((node.type as NodeParsedTypes).processed), node.nodes.drop(1).toMutableList(), method)
        processor.stageManager.pushTask(METHODS_BODY) {
            processNodesList(new, processor, ctx.with(method).with(BodyContext.of(method)), false)
        }
        return new
    }
}