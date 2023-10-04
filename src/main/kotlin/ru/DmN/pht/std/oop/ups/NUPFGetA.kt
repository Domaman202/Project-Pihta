package ru.DmN.pht.std.oop.ups

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
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.fp.ast.NodeFGet
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.base.utils.processNodes

object NUPFGetA : INodeUniversalProcessor<NodeFGet, NodeNodesList> { // todo: static fields
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeFGet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            unparser.unparse(node.nodes[0], ctx, indent + 1)
            append(' ').append(node.name).append(')')
        }
    }

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        findField(processor.calc(node.nodes[0], ctx), processor.computeString(node.nodes[1], ctx), node.nodes[0].isConstClass())?.type

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFGet? {
        return if (mode == ValType.VALUE) {
            val nodes = processor.processNodes(node, ctx, ValType.VALUE)
            val name = processor.computeString(nodes[1], ctx)
            NodeFGet(
                node.tkOperation,
                mutableListOf(nodes[0]),
                name,
                processor.calc(nodes[0], ctx).let {
                    val field = findField(it, name, nodes[0].isConstClass())
                    if (field == null)
                        NodeFGet.Type.UNKNOWN
                    else if (field.static)
                        NodeFGet.Type.STATIC
                    else NodeFGet.Type.INSTANCE
                }
            )
        } else null
    }

    fun findField(instance: VirtualType?, name: String, static: Boolean): VirtualField? =
        instance?.fields?.find { it.name == name && it.static == static }
}