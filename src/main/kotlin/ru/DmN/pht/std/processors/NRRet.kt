package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.std.processor.utils.method
import ru.DmN.pht.std.processor.utils.nodeAs
import ru.DmN.pht.std.utils.line

object NRRet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        NRAs.process(nodeAs(node.line, NRDefault.process(node, processor, ctx, ValType.VALUE), ctx.method.rettype.name), processor, ctx, ValType.VALUE)!!
}