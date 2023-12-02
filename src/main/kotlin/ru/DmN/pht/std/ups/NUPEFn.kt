package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.MethodModifiers
import ru.DmN.siberia.utils.VirtualMethod.VirtualMethodImpl
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.VirtualType.VirtualTypeImpl
import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.utils.computeString

object NUPEFn : INUP<NodeDefn, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeDefn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.token.text)
                    .append(" ^").append(NUPValueA.unparseType(node.method.extension!!.name))
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
            MethodModifiers(static = true, extension = true),
            extend,
            false
        )
        type.methods += method
        gctx.getExtensions(extend) += method
        //
        val new = NodeDefn(node.token, node.nodes.drop(4).toMutableList(), method)
        processor.stageManager.pushTask(ProcessingStage.METHODS_BODY) {
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