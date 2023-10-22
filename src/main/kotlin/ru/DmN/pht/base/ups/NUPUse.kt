package ru.DmN.pht.base.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.NodeProcessedUse
import ru.DmN.pht.base.ast.NodeUse
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPUse : INodeUniversalProcessor<NodeUse, NodeUse> { // todo: exports
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeUse {
        val names = ArrayList<String>()
        var tk = parser.nextToken()!!
        while (tk.type == Token.Type.OPERATION) {
            names.add(tk.text!!)
            tk = parser.nextToken()!!
        }
        NUPUseCtx.loadModules(names, parser, ctx)
        return NodeUse(operationToken, names, mutableListOf())
    }

    override fun unparse(node: NodeUse, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        NUPUseCtx.loadModules(node.names, unparser, ctx)
        unparser.out.let {
            it.append('(').append(node.token.text)
            node.names.forEach { name ->
                it.append(' ').append(name)
                Module.MODULES[name]!!.inject(unparser, ctx)
            }
            it.append(')')
        }
    }

    override fun process(node: NodeUse, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeProcessedUse =
        NodeProcessedUse(node.token, node.names, node.nodes, ArrayList(), ArrayList())
            .apply { NUPUseCtx.injectModules(node, processor, ctx, ValType.NO_VALUE, processed) }
}