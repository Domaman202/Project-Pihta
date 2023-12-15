package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeType
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.utils.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.VirtualType.Companion.ofKlass
import ru.DmN.siberia.utils.VirtualType.VirtualTypeImpl
import ru.DmN.siberia.utils.text

object NRClass : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (node.token.text == "obj")
            processor.computeType(node.nodes[0], ctx)
        else null

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeType {
        val gctx = ctx.global
        //
        val type = createVirtualType(gctx.name(processor.computeString(processor.process(node.nodes[0], ctx, ValType.VALUE)!!, ctx)))
        //
        when (node.text) {
            "obj" -> type.fields += VirtualFieldImpl(type, "INSTANCE", type, isStatic = true, isEnum = false)
            "itf" -> type.isInterface = true
        }
        processor.tp.types += type
        //
        val new = NodeType(node.token.processed(), node.nodes.drop(2).toMutableList(), type)
        processor.stageManager.pushTask(ProcessingStage.TYPES_PREDEFINE) {
            val context = ctx.with(type)
            type.parents =
                processor.computeList(processor.process(node.nodes[1], context, ValType.VALUE)!!, context)
                    .map { processor.computeType(it, context) }
                    .toMutableList()
            processor.stageManager.pushTask(ProcessingStage.TYPES_DEFINE) {
                NRDefault.process(new, processor, context, mode)
            }
        }
        return new
    }

    private fun createVirtualType(name: String): VirtualTypeImpl {
        val gs = name.indexOf('<')
        if (gs == -1)
            return VirtualTypeImpl(name)
        val generics = ArrayList<String>()
        var s = name.substring(gs)
        while (true) {
            val i = s.indexOf(',')
            generics.add(s.substring(1, if (i == -1) s.length - 1 else i))
            if (i == -1)
                break
            s = s.substring(i)
        }
        return VirtualTypeImpl(name.substring(0, gs), generics = generics.map { Pair(it, ofKlass(Any::class.java)) }) // todo
    }
}