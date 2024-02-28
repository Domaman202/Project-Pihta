package ru.DmN.pht.helper.parsers

import ru.DmN.pht.helper.ast.IValueNode
import ru.DmN.pht.helper.ast.NodeArgument
import ru.DmN.pht.helper.node.NodeTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.node.INodeInfo

object NPArgument : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeArgument {
        val text = token.text
        return NodeArgument(INodeInfo.of(NodeTypes.entries.find { it.operation == text }!!, ctx, token), (parser.parseNode(ctx) as IValueNode).value)
    }
}