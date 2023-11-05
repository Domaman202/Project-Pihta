package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeField
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line

object NRField : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val gctx = ctx.global
        val clazz = ctx.clazz
        val body = ArrayList<Node>()
        val fields = ArrayList<VirtualField>()
        val line = node.line
        processor.computeList(node.nodes[0], ctx)
            .map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }.forEach { it ->
                VirtualField(clazz, it[0], gctx.getType(it[1], processor.tp), static = false, enum = false).run {
                    fields += this
                    clazz.fields += this
                    body += nodeDefn(
                        line,
                        "set${name.let { it[0].toUpperCase() + it.substring(1) }}",
                        "void",
                        listOf(Pair(name, type.name)),
                        listOf(
                            NodeFieldSet(
                                Token.operation(line, "fset!"),
                                nodeGetOrNameOf(line, "this"),
                                name,
                                nodeGetOrNameOf(line, name),
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
                            NodeFMGet(
                                Token.operation(line, "fget!"), nodeGetOrNameOf(line, "this"), name,
                                static = false,
                                native = true
                            )
                        )
                    )
                }
            }
        return NRDefault.process(
            nodeProgn(line, body.apply { this += NodeField(node.token.processed(), fields) }),
            processor,
            ctx,
            mode
        )
    }
}