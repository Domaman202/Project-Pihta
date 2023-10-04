package ru.DmN.pht.std.fp.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.fp.ast.NodeGetA
import ru.DmN.pht.std.base.processor.utils.body
import ru.DmN.pht.std.base.processor.utils.clazz
import ru.DmN.pht.std.base.processor.utils.isBody
import ru.DmN.pht.std.base.processor.utils.isClass
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeString

object NUPGetA : INodeUniversalProcessor<NodeGetA, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeGetA, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(node.name).append(')')
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeGetA {
        val name = processor.computeString(processor.process(node.nodes[0], ctx, mode)!!, ctx)
        var type = NodeGetA.Type.UNKNOWN
        if (ctx.isBody() && ctx.body[name] != null)
            type = NodeGetA.Type.VARIABLE
        else if (ctx.isClass() && ctx.clazz.fields.find { it.name == name } != null)
            type = NodeGetA.Type.THIS_FIELD
        return NodeGetA(node.tkOperation, name, type)
    }
}