package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.nodeDef
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.line

object NRDefSet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val bctx = ctx.body
        val name = processor.computeString(node.nodes[0], ctx)
        val variable = bctx[name]
        return if (variable == null)
            NRDef.process(nodeDef(node.line, name, node.nodes[1]), processor, ctx, mode)
        else NodeSet(Token.operation(node.line, "set!"), mutableListOf(node.nodes[1]), name)
    }
}