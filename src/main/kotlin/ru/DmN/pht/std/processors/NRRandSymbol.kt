package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import java.util.Random
import kotlin.math.absoluteValue

object NRRandSymbol : ru.DmN.pht.std.processors.IStdNodeProcessor<Node> {
    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        null

    override fun computeString(node: Node, processor: Processor, ctx: ProcessingContext): String =
        Random().nextInt().absoluteValue.toString()
}