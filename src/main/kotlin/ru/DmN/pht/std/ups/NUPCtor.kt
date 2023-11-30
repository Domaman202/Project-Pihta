package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
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
import ru.DmN.siberia.utils.INUP
import ru.DmN.siberia.utils.MethodModifiers
import ru.DmN.siberia.utils.VirtualMethod.VirtualMethodImpl
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.VirtualType.VirtualTypeImpl
import ru.DmN.siberia.utils.line

object NUPCtor : INUP<NodeDefn, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeDefn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.token.text).append(" [")
                argsn.forEachIndexed { i, it ->
                    append('[').append(it).append(" ^").append(argsc[i].name).append(']')
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
        val args = NRDefn.parseArguments(node.nodes[0], processor, ctx)
        //
        val method = VirtualMethodImpl(
            type,
            "<init>",
            VirtualType.VOID,
            args.first.map { gctx.getType(it, processor.tp) },
            args.second,
            MethodModifiers(ctor = true)
        )
        type.methods += method
        //
        val new = NodeDefn(Token.operation(node.line, "ctor"), node.nodes.drop(1).toMutableList(), method)
        processor.stageManager.pushTask(ProcessingStage.METHODS_BODY) {
            NRDefault.process(
                new,
                processor,
                ctx.with(method).with(BodyContext.of(method)),
                mode
            )
        }
        return new
    }
}