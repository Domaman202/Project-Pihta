package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.node.nodeValue
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import java.util.*
import kotlin.math.absoluteValue

object NRRandSymbol : IStdNodeProcessor<Node> {
    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeValue? =
        if (mode == ValType.VALUE)
            nodeValue(node.info, Random().nextInt())
        else null

    override fun computeString(node: Node, processor: Processor, ctx: ProcessingContext): String =
        Random().nextInt().absoluteValue.toString()
}