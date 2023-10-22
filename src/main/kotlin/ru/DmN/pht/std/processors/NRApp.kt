package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processor.Platform
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.utils.platform
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeProgn
import ru.DmN.pht.std.processor.utils.nodeValn
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.ups.NUPClass

object NRApp : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            Platform.UNIVERSAL -> node
            Platform.JAVA -> {
                val line = node.token.line
                NUPClass.process(
                    NodeNodesList(
                        Token.operation(line, "cls"),
                        mutableListOf(
                            NodeValue.of(line, NodeValue.Type.STRING, ctx.global.name("App")),
                            nodeValn(line, mutableListOf(NodeValue.of(line, NodeValue.Type.STRING, "java.lang.Object"))),
                            NodeNodesList(
                                Token.operation(line, "defn"),
                                mutableListOf(
                                    NodeValue.of(line, NodeValue.Type.STRING, "main"),
                                    NodeValue.of(line, NodeValue.Type.CLASS, "void"),
                                    nodeValn(line, mutableListOf()),
                                    nodeProgn(line, node.nodes)
                                )
                            )
                        )
                    ), processor, ctx, mode
                )
            }
        }
}