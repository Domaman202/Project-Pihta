package ru.DmN.pht.std.fp.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.fp.ast.NodeFMGet
import ru.DmN.pht.std.fp.ast.NodeFieldSet
import ru.DmN.pht.std.value.ast.NodeGetOrName
import ru.DmN.pht.std.fp.ast.NodeSet
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor

object NUPSetB : INodeUniversalProcessor<NodeSet, NodeSet> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val name = parser.nextToken()!!
        return parse(
            Token(operationToken.line, Token.Type.OPERATION, "set!"),
            name.text!!,
            when (name.type) {
                Token.Type.CLASS -> true
                Token.Type.OPERATION -> false
                else -> throw RuntimeException()
            },
            parser.parseNode(ctx)!!
        )
    }

    private fun parse(operationToken: Token, name: String, static: Boolean, value: Node): Node {
        val parts = name.split("/")
        return if (parts.size == 1) NodeSet(
            Token(operationToken.line, Token.Type.OPERATION, "set!"),
            parts.last(),
            value
        ) else NodeFieldSet(
            Token(operationToken.line, Token.Type.OPERATION, "fset!"),
            parse(operationToken.line, parts, 1, static),
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
            append('(').append(node.tkOperation.text).append(' ').append(node.name)
            if (node.value != null) {
                append(' ')
                unparser.unparse(node.value!!, ctx, indent + 1)
            }
            append(')')
        }
    }

    override fun process(node: NodeSet, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeSet {
        node.value = node.value?.let { processor.process(it, ctx, mode) }
        return node
    }
}