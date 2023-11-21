package ru.DmN.pht.base.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.*
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ProcessingStage
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.processor.utils.exports
import ru.DmN.pht.std.processor.utils.isExports
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.text
import java.util.*
import kotlin.collections.ArrayList

object NUPUse : INodeUniversalProcessor<NodeUse, NodeParsedUse> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val names = ArrayList<String>()
        var tk = parser.nextToken()!!
        while (tk.type == Token.Type.OPERATION) {
            names.add(tk.text!!)
            tk = parser.nextToken()!!
        }
        parser.tokens.push(tk)
        return parse(names, token, parser, ctx)
    }

    fun parse(names: List<String>, token: Token, parser: Parser, ctx: ParsingContext): NodeParsedUse {
        val exports = ArrayList<NodeNodesList>()
        if (ctx.isExports())
            ctx.exports.push(exports)
        else ctx.exports = Stack<MutableList<NodeNodesList>>().apply { push(exports) }
        NUPUseCtx.loadModules(names, parser, ctx)
        ctx.exports.pop()
        return NodeParsedUse(token, names, ArrayList(), exports)
    }

    override fun unparse(node: NodeUse, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        NUPUseCtx.loadModules(node.names, unparser, ctx)
        unparser.out.apply {
            append('(').append(node.text).append(' ')
            node.names.forEach(this::append)
            append(')')
        }
    }

    override fun process(node: NodeParsedUse, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val processed = ArrayList<Node>()
        NUPUseCtx.injectModules(node, processor, ctx, ValType.NO_VALUE, processed)
        node.exports.forEach { NRDefault.process(it, processor, ctx, ValType.NO_VALUE) }
        return NodeProcessedUse(node.token, node.names, ArrayList(), processed, node.exports)
    }
}