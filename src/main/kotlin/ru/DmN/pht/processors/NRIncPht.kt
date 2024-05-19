package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeIncPht
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.parser.ParserImpl
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processors.INodeProcessor

object NRIncPht : INodeProcessor<NodeIncPht> {
    override fun process(node: NodeIncPht, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        processor.process(
            ParserImpl(
                "(use-ctx pht ${String(ctx.module.getModuleFile(processor.computeString(node.nodes[0], ctx)).readBytes())})",
                processor.mp
            ).parseNode(node.ctx)!!,
            ctx,
            valMode
        )
}