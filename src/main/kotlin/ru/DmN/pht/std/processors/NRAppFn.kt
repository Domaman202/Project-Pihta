package ru.DmN.pht.std.processors

import ru.DmN.pht.std.processor.utils.clazzOrNull
import ru.DmN.pht.std.processor.utils.nodeCls
import ru.DmN.pht.std.processor.utils.nodeDefn
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.*
import ru.DmN.siberia.processors.INodeProcessor

object NRAppFn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            Platform.UNIVERSAL -> node
            Platform.JAVA -> {
                val line = node.token.line
                val fn = NodeNodesList(
                    Token.operation(line, "@static"), mutableListOf(
                        nodeDefn(
                            line,
                            "main",
                            "void",
                            nodeProgn(line, node.nodes)
                        )
                    )
                )
                if (ctx.clazzOrNull?.name == "App")
                    processor.process(fn, ctx, mode)!!
                else {
                    NRClass.process(
                        nodeCls(
                            line,
                            "App",
                            "java.lang.Object",
                            fn
                        ), processor, ctx, mode
                    )
                }
            }
        }
}