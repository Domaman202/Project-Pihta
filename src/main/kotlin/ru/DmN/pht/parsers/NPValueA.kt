package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.pht.utils.node.NodeTypes.VALUE
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.node.INodeInfo

object NPValueA : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeValue {
        val value = parser.nextToken()!!
        return NodeValue(
            INodeInfo.of(VALUE, ctx, token),
            when (value.type) {
                DefaultType.NIL            -> NIL
                DefaultType.BOOLEAN        -> BOOLEAN
                DefaultType.CHAR           -> CHAR
                DefaultType.INTEGER        -> INT
                DefaultType.LONG           -> LONG
                DefaultType.FLOAT          -> FLOAT
                DefaultType.DOUBLE         -> DOUBLE
                DefaultType.STRING         -> STRING
                DefaultType.PRIMITIVE,
                DefaultType.CLASS,
                DefaultType.CLASS_WITH_GEN -> CLASS
                DefaultType.NAMING         -> NAMING
                DefaultType.OPERATION      -> if (value.text == "nil") NIL else throw RuntimeException()
                else -> throw RuntimeException()
            }, value.text!!
        )
    }
}