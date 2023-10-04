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
import ru.DmN.pht.base.unparser.unparsers.NUDefault
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.std.base.processor.utils.ICastable
import ru.DmN.pht.std.fp.ast.NodeValue
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.oop.ast.NodeMCall

object NUPMCallA : INodeUniversalProcessor<NodeMCall, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeMCall, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            unparser.unparse(node.instance, ctx, indent + 1)
            append(' ').append(node.method.name)
            NUDefault.unparseNodes(node, unparser, ctx, indent + 1)
            append(')')
        }
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeMCall {
        val pair = findMethod(node, processor, ctx)
        return if (pair.second.extend == null) {
            NodeMCall(
                node.tkOperation,
                pair.first.toMutableList(),
                processor.process(node.nodes[0], ctx, mode)!!,
                pair.second,
                if (pair.second.modifiers.static)
                    NodeMCall.Type.STATIC
                else NodeMCall.Type.VIRTUAL
            )
        } else NodeMCall(
            node.tkOperation,
            (pair.first + processor.process(node.nodes[0], ctx, ValType.VALUE)!!).toMutableList(),
            NodeValue.of(node.tkOperation.line, NodeValue.Type.CLASS, pair.second.extend!!.name),
            pair.second,
            NodeMCall.Type.EXTEND
        )
    }

    private fun findMethod(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Pair<List<Node>, VirtualMethod> {
        val gctx = ctx.global
        val type = node.nodes[0].let {
            if (it.isConstClass()) gctx.getType(it.getConstValueAsString(), processor.tp)
            else processor.calc(it, ctx)!!
        }
        val name = processor.computeString(node.nodes[1], ctx)
        val args = node.nodes
            .asSequence()
            .drop(2)
            .map { processor.process(it, ctx, ValType.VALUE)!! }
            .toList()
        val argsc = args
            .asSequence()
            .map { processor.calc(it, ctx)!!.name }
            .map { ICastable.of(processor.tp.typeOf(it)) }
            .toList()
        val methods = gctx.getMethodVariants(type, name, argsc, processor.tp).first()
        return Pair(args, methods)
    }
}