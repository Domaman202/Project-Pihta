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
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.fp.ast.NodeDef
import ru.DmN.pht.std.base.processor.utils.Variable
import ru.DmN.pht.std.base.processor.utils.body
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeList
import ru.DmN.pht.std.base.utils.computeString

object NUPDef : INodeUniversalProcessor<NodeDef, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeDef, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(" [")
            node.variables.forEach {
                append("[^").append(it.type).append(' ').append(it.name)
                if (it.value != null) {
                    append(' ')
                    unparser.unparse(it.value, ctx, indent + 1)
                }
                append(']')
            }
            append("])")
        }
    }

    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeDef {
        val gctx = ctx.global
        val bctx = ctx.body
        //
        val list = ArrayList<Variable>()
        processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx) }.forEach { it ->
            lateinit var type: VirtualType
            lateinit var name: String
            val value: Node? =
                if (it[0].isConstClass()) {
                    type = gctx.getType(processor.computeString(it[0], ctx), processor.tp)
                    name = processor.computeString(it[1], ctx)
                    it.getOrNull(2)
                } else {
                    type = processor.calc(it[1], ctx)!!
                    name = processor.computeString(it[0], ctx)
                    it[1]
                }?.let { processor.process(it, ctx, ValType.VALUE) }
            list.add(Variable(name, type, value, bctx.addVariable(name, type).id))
        }
        return NodeDef(node.tkOperation, list)
    }
}