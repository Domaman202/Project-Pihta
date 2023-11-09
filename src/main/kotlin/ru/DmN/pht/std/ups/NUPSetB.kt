package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.text

object NUPSetB : INodeUniversalProcessor<NodeSet, NodeSet> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.nextToken()!!
        return parse(
            Token(token.line, Token.Type.OPERATION, "set!"),
            name.text!!,
            when (name.type) {
                Token.Type.CLASS -> true
                Token.Type.OPERATION -> false
                else -> throw RuntimeException()
            },
            parser.parseNode(ctx)!!
        )
    }

    private fun parse(token: Token, name: String, static: Boolean, value: Node): Node {
        val parts = name.split("/")
        return if (parts.size == 1)
            NodeSet(
                Token(token.line, Token.Type.OPERATION, "set!"),
                parts.last(),
                value
            )
        else NodeFieldSet(
            Token(token.line, Token.Type.OPERATION, "fset!"),
            parse(token.line, parts, 1, static),
            parts.last(),
            value,
            static
        )
    }

    private fun parse(line: Int, parts: List<String>, i: Int, static: Boolean): Node {
        val j = i + 1
        return if (j == parts.size)
            NodeGetOrName(Token(line, Token.Type.OPERATION, "get-or-name!"), parts[parts.size - j], static)
        else NodeFMGet(
            Token(line, Token.Type.OPERATION, "fget!"),
            parse(line, parts, j, static),
            parts[parts.size - j],
            static
        )
    }
    override fun unparse(node: NodeSet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.text).append(' ').append(node.name)
            if (node.value != null) {
                append(' ')
                unparser.unparse(node.value!!, ctx, indent + 1)
            }
            append(')')
        }
    }

    override fun process(node: NodeSet, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeSet =
        node.apply { value = value?.let { processor.process(it, ctx, ValType.VALUE) } }
}