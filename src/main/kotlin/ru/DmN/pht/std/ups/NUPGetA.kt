package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeGetA
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.isBody
import ru.DmN.pht.std.processor.utils.isClass
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeString

object NUPGetA : INodeUniversalProcessor<NodeGetA, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeGetA, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.token.text).append(' ').append(node.name).append(')')
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeGetA {
        val name = processor.computeString(processor.process(node.nodes[0], ctx, mode)!!, ctx)
        var type = NodeGetA.Type.UNKNOWN
        if (ctx.isBody() && ctx.body[name] != null)
            type = NodeGetA.Type.VARIABLE
        else if (ctx.isClass() && ctx.clazz.fields.find { it.name == name } != null)
            type = NodeGetA.Type.THIS_FIELD
        return NodeGetA(node.token, name, type)
    }
}