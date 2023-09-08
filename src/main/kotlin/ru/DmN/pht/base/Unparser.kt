package ru.DmN.pht.base

import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.INodeUnparser
import ru.DmN.pht.base.utils.getRegex

class Unparser {
    val out = StringBuilder()

    fun unparse(node: Node, ctx: UnparsingContext, indent: Int) = get(ctx, node).unparse(node, this, ctx, indent)

    fun get(ctx: UnparsingContext, node: Node): INodeUnparser<Node> {
        val name = node.tkOperation.text!!
        ctx.loadedModules.forEach { it -> it.unparsers.getRegex(name)?.let { return it as INodeUnparser<Node> } }
        throw RuntimeException()
    }
}