package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.pht.std.processor.utils.method
import ru.DmN.pht.std.processor.utils.nodeAs
import ru.DmN.pht.std.utils.line

object NRRet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        NRAs.process(nodeAs(node.line, NRDefault.process(node, processor, ctx, ValType.VALUE), ctx.method.rettype.name), processor, ctx, ValType.VALUE)!!
}