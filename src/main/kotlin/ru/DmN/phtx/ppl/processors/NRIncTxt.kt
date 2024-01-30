package ru.DmN.phtx.ppl.processors

import ru.DmN.pht.node.nodeValue
import ru.DmN.pht.processor.utils.global
import ru.DmN.pht.processors.IStdNodeProcessor
import ru.DmN.pht.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.utils.VirtualType

object NRIncTxt : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("String", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        nodeValue(node.info, computeString(node, processor, ctx))

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        ctx.module.getModuleFile(processor.computeString(node.nodes[0], ctx))
}