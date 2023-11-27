package ru.DmN.siberia

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.INodeUnparser
import ru.DmN.pht.base.utils.getRegex
import ru.DmN.pht.std.utils.text

class Unparser {
    val out = StringBuilder()

    fun unparse(node: Node, ctx: UnparsingContext, indent: Int) =
        get(ctx, node).unparse(node, this, ctx, indent)

    fun get(ctx: UnparsingContext, node: Node): INodeUnparser<Node> {
        val name = node.text
        ctx.loadedModules.forEach { it -> it.unparsers.getRegex(name)?.let { return it as INodeUnparser<Node> } }
        throw RuntimeException()
    }
}