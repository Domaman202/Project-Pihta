package ru.DmN.phtx.spt.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.Klass
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.line
import ru.DmN.phtx.spt.processor.utils.page

class NRComponent(val type: Klass) : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.ofKlass(type)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val line = node.line
        return NRDefault.process(
            nodeProgn(
                line, mutableListOf(
                    nodeMCall(
                        line,
                        nodeGetOrName(line, ctx.page.page),
                        "add",
                        listOf(nodeNew(line, type.name, node.nodes))
                    )
                )
            ),
            processor, ctx, mode
        )
    }
}