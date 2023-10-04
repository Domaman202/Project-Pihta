package ru.DmN.pht.std.oop.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.processor.utils.ProcessingStage
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.MethodModifiers
import ru.DmN.pht.base.utils.TypeOrGeneric
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.fp.processor.ctx.BodyContext
import ru.DmN.pht.std.base.processor.utils.clazz
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.processor.utils.with
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.fp.ast.NodeDefn
import ru.DmN.pht.std.fp.ups.NUPDefn

object NUPCtor : INodeUniversalProcessor<NodeDefn, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeDefn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.tkOperation.text).append(" [")
                argsn.forEachIndexed { i, it ->
                    append('[').append(it).append(" ^").append(argsc[i].type).append(']')
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
        val generics = type.generics
        //
        val args = NUPDefn.parseArguments(node.nodes[0], processor, ctx)
        //
        val method = VirtualMethod(
            type,
            generics,
            "<ctor>",
            TypeOrGeneric.of(generics, VirtualType.VOID),
            args.first.map { TypeOrGeneric.of(generics, gctx.getType(it, processor.tp)) },
            args.second,
            MethodModifiers(ctor = true)
        )
        type.methods += method
        //
        val new = NodeDefn(node.tkOperation, node.nodes.drop(1).toMutableList(), method)
        processor.pushTask(ctx, ProcessingStage.METHODS_BODY) {
            NRDefault.process(
                new,
                processor,
                ctx.with(method).with(BodyContext.of(null).apply { if (!method.modifiers.static) addVariable("this", type) }),
                mode
            )
        }
        return new
    }
}