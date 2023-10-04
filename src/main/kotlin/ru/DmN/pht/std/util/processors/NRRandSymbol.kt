package ru.DmN.pht.std.util.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.base.processor.processors.IStdNodeProcessor
import java.util.Random
import kotlin.math.absoluteValue

object NRRandSymbol : IStdNodeProcessor<Node> {
    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        null

    override fun computeString(node: Node, processor: Processor, ctx: ProcessingContext): String =
        Random().nextInt().absoluteValue.toString()
}