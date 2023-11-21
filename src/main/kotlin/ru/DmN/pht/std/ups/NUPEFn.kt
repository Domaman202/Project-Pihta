package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ProcessingStage
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.MethodModifiers
import ru.DmN.pht.base.utils.VirtualMethod.VirtualMethodImpl
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.VirtualType.VirtualTypeImpl
import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeString

object NUPEFn : INodeUniversalProcessor<NodeDefn, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeDefn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.token.text)
                    .append(" ^").append(NUPValueA.unparseType(node.method.extend!!.name))
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
        val type = ctx.clazz as VirtualTypeImpl
        //
        val extend = gctx.getType(processor.computeString(node.nodes[0], ctx), processor.tp)
        val name = processor.computeString(node.nodes[1], ctx)
        val returnType = gctx.getType(processor.computeString(node.nodes[2], ctx), processor.tp)
        val args = NRDefn.parseArguments(node.nodes[3], processor, ctx)
        //
        args.first.add(0, extend.name)
        args.second.add(0, "this")
        //
        val method = VirtualMethodImpl(
            type,
            name,
            returnType,
            args.first.map { gctx.getType(it, processor.tp) },
            args.second,
            MethodModifiers(static = true, extend = true),
            extend
        )
        type.methods += method
        gctx.getExtends(extend) += method
        //
        val new = NodeDefn(node.token, node.nodes.drop(4).toMutableList(), method)
        processor.pushTask(ctx, ProcessingStage.METHODS_BODY) {
            NRDefault.process(
                new,
                processor,
                ctx.with(method).with(BodyContext.of(method)),
                if (method.rettype == VirtualType.VOID)
                    ValType.NO_VALUE
                else ValType.VALUE
            )
        }
        return new
    }
}