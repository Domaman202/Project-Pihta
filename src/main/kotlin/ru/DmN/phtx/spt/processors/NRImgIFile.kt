package ru.DmN.phtx.spt.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.nodeAs
import ru.DmN.pht.std.processor.utils.nodeClass
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.processors.NRMCall
import ru.DmN.pht.std.utils.line
import java.awt.Image

object NRImgIFile : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.ofKlass(Image::class.java)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE) {
            val line = node.line
            NRMCall.process(
                nodeMCall(
                    line,
                    nodeClass(line, "javax.imageio.ImageIO"),
                    "read",
                    listOf(nodeMCall(line, nodeAs(line, nodeClass(line, ctx.clazz.name), "java.lang.Class"), "getResourceAsStream", node.nodes)) // todo:
                ), processor, ctx, ValType.VALUE
            )
        } else null
}