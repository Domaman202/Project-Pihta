package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.utils.text

object NUPSetB : INUP<NodeSet, NodeSet> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.nextToken()!!
        return NPDefault.parse(parser, ctx) {
            parse(
                Token(token.line, Token.Type.OPERATION, "set!"),
                name.text!!,
                when (name.type) {
                    Token.Type.CLASS -> true
                    Token.Type.OPERATION -> false
                    else -> throw RuntimeException()
                },
                it
            )
        }
    }

    private fun parse(token: Token, name: String, static: Boolean, values: MutableList<Node>): Node {
        val parts = name.split("/")
        return if (parts.size == 1)
            NodeSet(
                Token(token.line, Token.Type.OPERATION, "set!"),
                values,
                parts.last()
            )
        else NodeFieldSet(
            Token(token.line, Token.Type.OPERATION, "fset!"),
            values,
            parse(token.line, parts, 1, static),
            parts.last(),
            static
        )
    }

    private fun parse(line: Int, parts: List<String>, i: Int, static: Boolean): Node =
        if (i + 1 == parts.size)
            NodeGetOrName(Token(line, Token.Type.OPERATION, "get-or-name!"), parts[parts.size - i - 1], static)
        else NodeFMGet(
            Token(line, Token.Type.OPERATION, "fget!"),
            parse(line, parts, i + 1, static),
            parts[parts.size - i - 1],
            static
        )

    override fun unparse(node: NodeSet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.text).append(' ').append(node.name)
//            if (node.value != null) {
//                append(' ')
//                unparser.unparse(node.value!!, ctx, indent + 1)
//            } // todo:
            append(')')
        }
    }

    override fun process(node: NodeSet, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeSet =
        NRDefault.processValue(node, processor, ctx) as NodeSet
}