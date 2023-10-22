package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ProcessingStage
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.ctx.EnumContext
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.ast.NodeType

object NUPEnum : INodeUniversalProcessor<NodeType, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeType, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.token.text).append(' ').append(node.type.simpleName).append(" [")
            node.type.parents.forEachIndexed { i, it ->
                append('^').append(it.name)
                if (node.type.parents.size + 1 < i) {
                    append(' ')
                }
            }
            append(']')
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val gctx = ctx.global
        //
        val type = VirtualType(gctx.name(processor.computeString(processor.process(node.nodes[0], ctx, ValType.VALUE)!!, ctx)))
        processor.tp.types += type
        //
        val new = NodeType(node.token, node.nodes.drop(2).toMutableList(), type)
        processor.pushTask(ctx, ProcessingStage.TYPES_PREDEFINE) {
            type.parents = processor.computeList(processor.process(node.nodes[1], ctx, ValType.VALUE)!!, ctx)
                .map { gctx.getType(processor.computeString(it, ctx), processor.tp) }
                .toMutableList()
            processor.pushTask(ctx, ProcessingStage.TYPES_DEFINE) {
                NRDefault.process(new, processor, ctx.with(EnumContext(type)), mode)
            }
        }
        return new
    }
}