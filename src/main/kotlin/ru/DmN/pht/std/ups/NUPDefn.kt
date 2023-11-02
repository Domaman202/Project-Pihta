package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.processor.ProcessingStage
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.MethodModifiers
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.processor.utils.nodeAs
import ru.DmN.pht.std.processors.NRAs
import ru.DmN.pht.std.utils.line

object NUPDefn : INodeUniversalProcessor<NodeDefn, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeDefn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.token.text)
                    .append(' ').append(name)
                    .append(" ^").append(rettype.name)
                    .append(" [")
                argsn.forEachIndexed { i, it ->
                    append('[').append(it).append(' ').append(NUPValueA.unparseType(argsc[i].name)).append(']')
                    if (argsn.size + 1 < i) {
                        append(' ')
                    }
                }
                append(']')
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
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeDefn {
        val gctx = ctx.global
        val type = ctx.clazz
        //
        val name = processor.computeString(node.nodes[0], ctx)
        val returnType = processor.computeString(node.nodes[1], ctx)
        val args = parseArguments(node.nodes[2], processor, ctx)
        //
        val method = VirtualMethod(
            type,
            name,
            gctx.getType(returnType, processor.tp),
            args.first.map { gctx.getType(it, processor.tp) },
            args.second,
            MethodModifiers()
        )
        type.methods += method
        //
        val line = node.line
        val new = NodeDefn(
            node.token,
            mutableListOf(
                if (method.rettype == VirtualType.VOID)
                    node.nodes.last()
                else NRAs.process(nodeAs(line, node.nodes.last(), method.rettype.name), processor, ctx, ValType.VALUE)!!
            ),
            method
        )
        processor.pushTask(ctx, ProcessingStage.METHODS_BODY) {
            NRDefault.process(
                new,
                processor,
                ctx.with(method).with(BodyContext.of(method)),
                ValType.NO_VALUE
            )
        }
        return new
    }

    fun parseArguments(list: Node, processor: Processor, ctx: ProcessingContext): Pair<MutableList<String>, MutableList<String>> {
        val argsn = ArrayList<String>()
        val argsc = ArrayList<String>()
        processor.computeList(list, ctx)
            .stream()
            .map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }
            .forEach {
                argsn += it.first()
                argsc += it.last()
            }
        return Pair(argsc, argsn)
    }
}