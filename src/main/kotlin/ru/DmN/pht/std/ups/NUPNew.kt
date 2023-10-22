package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.ast.NodeNew
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line
import ru.DmN.pht.std.utils.processNodes

object NUPNew : INodeUniversalProcessor<NodeNew, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeNew, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.token.text).append(' ').append(NUPValue.unparseType(node.ctor.declaringClass!!.name))
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNew {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val ctor = NUPMCallA.findMethod(
            ctx.global.getType(processor.computeString(nodes[0], ctx), processor.tp),
            "<init>",
            nodes.asSequence().drop(1),
            processor,
            ctx
        )
        return NodeNew(node.token, NUPMCallA.processArguments(node.line, processor, ctx, ctor.second, ctor.first), ctor.second)
    }
}