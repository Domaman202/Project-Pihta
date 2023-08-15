package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.lexer.checkOperation
import ru.DmN.pht.base.lexer.isType
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.ast.NodeDefMacro

object NPDefMacro : SimpleNodeParser<NodeDefMacro>() {
    override fun parse(parser: Parser, operationToken: Token): NodeDefMacro {
        val name = parser.nextOperation().text!!
        val args = ArrayList<String>()
        val argsStartToken = parser.nextToken()
        if (argsStartToken?.type == Token.Type.OPEN_CBRACKET) {
            var tk = parser.nextToken()!!
            while (tk.type != Token.Type.CLOSE_CBRACKET) {
                args.add(tk.checkOperation().text!!)
                tk = parser.nextToken()!!
            }
        } else parser.tokens.push(argsStartToken)
        return parse(parser) { NodeDefMacro(operationToken, name, args, it) }
    }
}