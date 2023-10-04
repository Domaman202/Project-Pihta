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
import ru.DmN.pht.std.fp.processor.ctx.BodyContext
import ru.DmN.pht.std.base.processor.utils.clazz
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.processor.utils.with
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.fp.ups.NUPDefn
import ru.DmN.pht.std.oop.ast.NodeEfn

object NUPEFn : INodeUniversalProcessor<NodeEfn, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeEfn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.tkOperation.text)
                    .append(" ^").append(node.extend.name)
                    .append(' ').append(name)
                    .append(" ^").append(rettype.type)
                    .append(" [")
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

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeEfn {
        val gctx = ctx.global
        val type = ctx.clazz
        val generics = type.generics
        //
        val extend = gctx.getType(processor.computeString(node.nodes[0], ctx), processor.tp)
        val name = processor.computeString(node.nodes[1], ctx)
        val returnType = gctx.getType(processor.computeString(node.nodes[2], ctx), processor.tp)
        val args = NUPDefn.parseArguments(node.nodes[3], processor, ctx)
        //
        val method = VirtualMethod(
            type,
            generics,
            name,
            TypeOrGeneric.of(generics, returnType),
            args.first.map { TypeOrGeneric.of(generics, gctx.getType(it, processor.tp)) },
            args.second,
            MethodModifiers(),
            extend
        )
        type.methods += method
        gctx.getExtends(extend) += method
        //
        val new = NodeEfn(node.tkOperation, node.nodes.drop(4).toMutableList(), extend, method)
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