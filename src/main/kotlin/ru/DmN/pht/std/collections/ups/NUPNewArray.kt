package ru.DmN.pht.std.collections.ups

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
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.compute
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.collections.ast.NodeNewArray

object NUPNewArray : INodeUniversalProcessor<NodeNewArray, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeNewArray, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append(node.tkOperation.text)
                .append('\n').append("\t".repeat(indent + 1)).append('^').append(node.type.name)
                .append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.size, ctx, indent + 1)
            append(')')
        }
    }

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp).arrayType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNewArray =
        NodeNewArray(node.tkOperation, ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp), processor.compute(node.nodes[1], ctx))
}