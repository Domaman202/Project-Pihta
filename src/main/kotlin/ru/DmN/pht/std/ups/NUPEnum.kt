package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.ast.NodeType
import ru.DmN.pht.std.processor.ctx.EnumContext
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.INUP
import ru.DmN.siberia.utils.VirtualType.VirtualTypeImpl
import ru.DmN.siberia.utils.line

object NUPEnum : INUP<NodeType, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeType, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUPClass.unparse(node, unparser, ctx, indent)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val gctx = ctx.global
        //
        val type = VirtualTypeImpl(gctx.name(processor.computeString(processor.process(node.nodes[0], ctx, ValType.VALUE)!!, ctx)))
        processor.tp.types += type
        //
        val line = node.line
        val new = NodeType(Token.operation(line, "!enum"), node.nodes.drop(2).toMutableList(), type)
        processor.pushTask(ctx, ProcessingStage.TYPES_PREDEFINE) {
            type.parents = processor.computeList(processor.process(node.nodes[1], ctx, ValType.VALUE)!!, ctx)
                .map { gctx.getType(processor.computeString(it, ctx), processor.tp) }
                .toMutableList()
            processor.pushTask(ctx, ProcessingStage.TYPES_DEFINE) {
                val ectx = EnumContext(type)
                val context = ctx.with(ectx)
                NRDefault.process(new, processor, context, ValType.NO_VALUE)
                new.nodes += processor.process(
                    NodeNodesList(
                        Token.operation(line, "@static"), mutableListOf(
                            nodeDefn(
                                node.line,
                                "<clinit>",
                                "void",
                                emptyList(),
                                mutableListOf(
                                    nodeProgn(
                                        line,
                                        ectx.enums.map {
                                            NodeFieldSet(
                                                Token.operation(line, "fset!"),
                                                mutableListOf(nodeNew(line, type.name, it.args)),
                                                nodeClass(line, type.name),
                                                it.name,
                                                true
                                            )
                                        }.toMutableList()
                                    )
                                )
                            )
                        )
                    ), context, ValType.NO_VALUE
                )!!
            }
        }
        return new
    }
}