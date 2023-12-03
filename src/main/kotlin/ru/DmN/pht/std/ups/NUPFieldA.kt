package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeFieldA
import ru.DmN.pht.std.ast.NodeFieldB
import ru.DmN.pht.std.ast.NodeFieldSet
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
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.INUP
import ru.DmN.siberia.utils.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.VirtualType.VirtualTypeImpl
import ru.DmN.siberia.utils.line

object NUPFieldA : INUP<NodeFieldA, NodeFieldA> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPDefault.parse(parser, ctx) { NodeFieldA(token, it) }

    override fun unparse(node: NodeFieldA, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefault.unparse(node, unparser, ctx, indent)

    override fun process(node: NodeFieldA, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val gctx = ctx.global
        val clazz = ctx.clazz as VirtualTypeImpl
        val body = ArrayList<Node>()
        val fields = ArrayList<VirtualFieldImpl>()
        val line = node.line
        processor.computeList(node.nodes[0], ctx)
            .map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }.forEach { it ->
                VirtualFieldImpl(clazz, it[0], gctx.getType(it[1], processor.tp), isStatic = node.static, isEnum = false).run {
                    fields += this
                    clazz.fields += this
                    body += nodeDefn(
                        line,
                        "set${name.let { it[0].toUpperCase() + it.substring(1) }}",
                        "void",
                        listOf(Pair(name, type.name)),
                        listOf(
                            if (node.static)
                                NodeFieldSet(
                                    Token.operation(line, "fset!"),
                                    mutableListOf(nodeGetOrName(line, name)),
                                    nodeValueClass(line, clazz.name),
                                    name,
                                    static = true,
                                    native = true
                                )
                            else NodeFieldSet(
                                Token.operation(line, "fset!"),
                                mutableListOf(nodeGetOrName(line, name)),
                                nodeGetOrName(line, "this"),
                                name,
                                static = false,
                                native = true
                            )
                        )
                    )
                    body += nodeDefn(
                        line,
                        "get${name.let { it[0].toUpperCase() + it.substring(1) }}",
                        type.name,
                        emptyList(),
                        listOf(
                            if (node.static)
                                NodeFMGet(
                                    Token.operation(line, "fget!"), nodeValueClass(line, clazz.name), name,
                                    static = true,
                                    native = true
                                )
                            else NodeFMGet(
                                Token.operation(line, "fget!"), nodeGetOrName(line, "this"), name,
                                static = false,
                                native = true
                            )
                        )
                    )
                }
            }
        return processor.process(
            NodeNodesList(
                Token.operation(line, if (node.static) "@static" else "progn"),
                body.apply { this += NodeFieldB(node.token.processed(), fields) }),
            ctx,
            mode
        )!!
    }
}