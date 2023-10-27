package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.isClass
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.processors.IStdNodeProcessor

object NUPGetOrName : INodeUniversalProcessor<NodeGetOrName, NodeGetOrName>, IStdNodeProcessor<NodeGetOrName> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? {
        val tk = parser.nextOperation()
        return if (tk.text!!.contains("[/#]".toRegex())) {
            parser.tokens.push(tk)
            parser.get(ctx, "get!")!!.parse(parser, ctx, Token(operationToken.line, Token.Type.OPERATION, "get!"))
        } else NodeGetOrName(operationToken, tk.text, false)
    }

    override fun unparse(node: NodeGetOrName, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append(node.name)
    }

    override fun calc(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): VirtualType =
        if (node.name == "super")
            ctx.body["this"]!!.type()
        else {
            val variable = ctx.body[node.name]
            variable?.type() ?: if (ctx.isClass()) ctx.clazz.fields.find { it.name == node.name }!!.type else throw RuntimeException()
        }

    override fun computeString(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): String =
        node.getValueAsString()

    override fun computeInt(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): Int =
        node.getValueAsString().toInt()
}