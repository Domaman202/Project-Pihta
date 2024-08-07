package ru.DmN.pht.imports.parsers

import ru.DmN.pht.imports.ast.IValueNode
import ru.DmN.pht.imports.ast.NodeArgument
import ru.DmN.pht.imports.node.NodeTypes
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.node.INodeInfo

object NPArgument : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeArgument {
        val text = token.text
        return NodeArgument(INodeInfo.of(NodeTypes.entries.find { it.operation == text }!!, ctx, token), (parser.parseNode(ctx) as IValueNode).value)
    }
}