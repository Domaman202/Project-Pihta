package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import java.util.Random
import kotlin.math.absoluteValue

object NRRandSymbol : ru.DmN.pht.std.processors.IStdNodeProcessor<Node> {
    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        null

    override fun computeString(node: Node, processor: Processor, ctx: ProcessingContext): String =
        Random().nextInt().absoluteValue.toString()
}