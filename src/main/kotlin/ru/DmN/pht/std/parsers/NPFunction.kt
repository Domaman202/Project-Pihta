package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.lexer.checkOperation
import ru.DmN.pht.base.lexer.isOperation
import ru.DmN.pht.base.lexer.isType
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.ast.NodeFunction
import ru.DmN.pht.std.utils.Arguments

object NPFunction : SimpleNodeParser<NodeFunction>() {
    override fun parse(parser: Parser, operationToken: Token): NodeFunction =
        parse(parser) { name, rettype, args, nodes ->
            NodeFunction(
                operationToken,
                name,
                rettype,
                args,
                operationToken.text == "sfn",
                operationToken.text == "afn",
                operationToken.text == "ofn", nodes
            )
        }

    fun parse(parser: Parser, constructor: (name: String, rettype: String, args: Arguments, nodes: MutableList<Node>) -> NodeFunction): NodeFunction {
        val name = parser.nextToken()!!.let {
            if (it.isOperation())
                it.text!!
            else "<lambda>"
        }
        val rettype = parser.nextToken()!!.let {
            if (it.isType())
                it.text!!
            else {
                parser.tokens.push(it)
                "java.lang.Object"
            }
        }
        return parse(parser) { args, nodes -> constructor(name, rettype, args, nodes) }
    }

    fun parse(parser: Parser, constructor: (args: Arguments, nodes: MutableList<Node>) -> NodeFunction): NodeFunction {
        val args = ArrayList<Pair<String, String>>()
        val argsStartToken = parser.nextToken()
        if (argsStartToken?.type == Token.Type.OPEN_CBRACKET) {
            var tk = parser.nextToken()!!
            while (tk.type != Token.Type.CLOSE_CBRACKET) {
                tk.checkOperation()
                args.add(Pair(tk.text!!, parser.nextToken()!!.let {
                    if (it.isType())
                        it.text!!
                    else {
                        parser.tokens.push(it)
                        "java.lang.Object"
                    }
                }))
                tk = parser.nextToken()!!
            }
        } else parser.tokens.push(argsStartToken)
        return super.parse(parser) { constructor(Arguments(args), it) }
    }
}