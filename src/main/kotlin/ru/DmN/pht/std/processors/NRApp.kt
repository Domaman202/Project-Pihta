package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.utils.Platform
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.platform
import ru.DmN.pht.std.processor.utils.nodeClass

object NRApp : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            Platform.UNIVERSAL -> node
            Platform.JAVA -> {
                val line = node.token.line
                NRClass.process(
                    nodeClass(
                        line,
                        "App",
                        listOf("java.lang.Object"),
                        listOf(
                            NodeNodesList(
                                Token.operation(line, "@static"),
                                node.nodes
                            )
                        )
                    ), processor, ctx, mode
                )
            }
        }
}