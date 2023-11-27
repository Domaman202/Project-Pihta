package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeGetA
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.isBody
import ru.DmN.pht.std.processor.utils.isClass
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeString

object NUPGetB : INodeUniversalProcessor<NodeGetA, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

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