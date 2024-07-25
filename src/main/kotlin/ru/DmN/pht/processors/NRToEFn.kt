package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeToEFn
import ru.DmN.pht.processor.ctx.classes_seq
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.node.NodeTypes
import ru.DmN.pht.utils.vtype.FakeExtVirtualMethod
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

object NRToEFn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeToEFn {
        var decl: VirtualType? = null
        var method: VirtualMethod? = null
        val name = processor.computeString(node.nodes[1], ctx)
        val args = processor.computeList(node.nodes[2], ctx).map { processor.computeType(it, ctx) }
        //
        node.nodes[0].let { it ->
            if (processor.computeString(it, ctx) == ".") {
                for (clazz in ctx.classes_seq) {
                    method = clazz.methods.find { it.name == name && it.argsc == args } ?: continue
                    decl = clazz
                }
            } else {
                decl = processor.computeType(it ,ctx).apply {
                    method = this.methods.find { it.name == name && it.argsc == args }
                }
            }
        }
        //
        val ext = args[0]
        val fake = FakeExtVirtualMethod(method ?: NRMCall.throwMNF(ctx.clazz, name, args), ext)
        (decl!!.methods as MutableList) += fake
        ctx.global.getExtensions(ext) += fake
        return NodeToEFn(node.info.withType(NodeTypes.TO_EFN_), decl!!, name, args)
    }
}