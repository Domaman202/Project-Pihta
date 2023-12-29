package ru.DmN.pht.std.imports.parsers

import ru.DmN.pht.std.imports.ast.IValueNode
import ru.DmN.pht.std.imports.ast.NodeArgument
import ru.DmN.pht.std.imports.node.NodeTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser

object NPArgument : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeArgument {
        val text = token.text
        return NodeArgument(INodeInfo.of(NodeTypes.entries.find { it.operation == text }!!, ctx, token), (parser.parseNode(ctx) as IValueNode).value)
    }
}