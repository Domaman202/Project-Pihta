package ru.DmN.pht.std.app.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.Platform
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.platform
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.processor.utils.nodePrognOf
import ru.DmN.pht.std.base.processor.utils.nodeValnOf
import ru.DmN.pht.std.fp.ast.NodeValue
import ru.DmN.pht.std.oop.ups.NUPClass

object NRApp : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            Platform.UNIVERSAL -> node
            Platform.JAVA -> {
                val line = node.tkOperation.line
                NUPClass.process(
                    NodeNodesList(
                        Token.operation(line, "cls"),
                        mutableListOf(
                            NodeValue.of(line, NodeValue.Type.STRING, ctx.global.name("App")),
                            nodeValnOf(line, mutableListOf(NodeValue.of(line, NodeValue.Type.STRING, "java.lang.Object"))),
                            NodeNodesList(
                                Token.operation(line, "defn"),
                                mutableListOf(
                                    NodeValue.of(line, NodeValue.Type.STRING, "main"),
                                    NodeValue.of(line, NodeValue.Type.CLASS, "void"),
                                    nodeValnOf(line, mutableListOf()),
                                    nodePrognOf(line, node.nodes)
                                )
                            )
                        )
                    ), processor, ctx, mode
                )
            }
        }
}