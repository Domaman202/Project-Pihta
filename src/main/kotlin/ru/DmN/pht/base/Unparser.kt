package ru.DmN.pht.base

import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

class Unparser {
    val out = StringBuilder()

    fun unparse(ctx: UnparsingContext, node: Node) = get(ctx, node).unparse(this, ctx, node)

    fun get(ctx: UnparsingContext, node: Node): NodeUnparser<Node> {
        val name = node.tkOperation.text!!
        ctx.modules.forEach { it.unparsers[name]?.let { return it as NodeUnparser<Node> } }
        throw RuntimeException()
    }
}