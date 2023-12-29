package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser

object NPValueA : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeValue {
        val value = parser.nextToken()!!
        return NodeValue(
            INodeInfo.of(NodeTypes.VALUE, ctx, token),
            when (value.type) {
                Token.DefaultType.OPERATION -> if (value.text == "nil") NodeValue.Type.NIL else throw RuntimeException()
                Token.DefaultType.CLASS   -> NodeValue.Type.CLASS
                Token.DefaultType.NAMING  -> NodeValue.Type.NAMING
                Token.DefaultType.STRING  -> NodeValue.Type.STRING
                Token.DefaultType.NIL     -> NodeValue.Type.NIL
                Token.DefaultType.BOOLEAN -> NodeValue.Type.BOOLEAN
                Token.DefaultType.INTEGER -> NodeValue.Type.INT
                Token.DefaultType.LONG    -> NodeValue.Type.LONG
                Token.DefaultType.FLOAT   -> NodeValue.Type.FLOAT
                Token.DefaultType.DOUBLE  -> NodeValue.Type.DOUBLE
                else -> throw RuntimeException()
            }, value.text!!
        )
    }
}