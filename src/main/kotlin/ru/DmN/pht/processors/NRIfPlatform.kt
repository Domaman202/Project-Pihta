package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeIfPlatform
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platforms
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRProgn

object NRIfPlatform : INodeProcessor<NodeIfPlatform> {
    override fun process(node: NodeIfPlatform, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val platform = ctx.platform
        return if (node.platforms.contains(platform.toString()))
            NRProgn.process(node, processor, ctx, ValType.NO_VALUE)
        else if (platform == Platforms.UNIVERSAL)
            node
        else null
    }
}