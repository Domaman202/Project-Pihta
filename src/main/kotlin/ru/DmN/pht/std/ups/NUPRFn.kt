package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeRFn
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPRFn : INodeUniversalProcessor<NodeRFn, NodeRFn> {
    override fun unparse(node: NodeRFn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append(NUDefaultX.text(node.token)).append(' ')
            node.type?.let { append('^').append(it.name) }
            append(" [")
            node.refs.forEachIndexed { i, it ->
                append(it.name)
                if (i + 1 < node.refs.size) {
                    append(' ')
                }
            }
            append("] [")
            node.args.forEachIndexed { i, it ->
                append(it)
                if (i + 1 < node.args.size) {
                    append(' ')
                }
            }
            append("]")
            if (node.nodes.isNotEmpty()) {
                append('\n')
                node.nodes.forEachIndexed { i, it ->
                    append("\t".repeat(indent + 1))
                    unparser.unparse(it, ctx, indent + 1)
                    if (node.nodes.size + 1 < i) {
                        append('\n')
                    }
                }
            }

            append(')')
        }
    }

    override fun calc(node: NodeRFn, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type ?: ctx.global.getType("Any", processor.tp)
}