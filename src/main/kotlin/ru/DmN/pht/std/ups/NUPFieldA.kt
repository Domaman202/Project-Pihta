package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.base.utils.VirtualField.VirtualFieldImpl
import ru.DmN.pht.base.utils.VirtualType.VirtualTypeImpl
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeFieldA
import ru.DmN.pht.std.ast.NodeFieldB
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line

object NUPFieldA : INodeUniversalProcessor<NodeFieldA, NodeFieldA> {
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
                                    nodeClass(line, clazz.name),
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
                                    Token.operation(line, "fget!"), nodeClass(line, clazz.name), name,
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