package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import java.util.*
import kotlin.math.absoluteValue

object NRRandSymbol : IComputableProcessor<Node> {
    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeValue? =
        if (valMode)
            nodeValue(node.info, computeInt(node, processor, ctx))
        else null

    override fun computeInt(node: Node, processor: Processor, ctx: ProcessingContext): Int =
        random.nextInt().absoluteValue

    override fun computeString(node: Node, processor: Processor, ctx: ProcessingContext): String =
        computeInt(node, processor, ctx).toString()

    private val random = Random()
}