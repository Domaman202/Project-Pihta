package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.unparsers.NUDefn.unparseGenerics
import ru.DmN.pht.utils.vtype.genericName
import ru.DmN.pht.utils.vtype.nameWithGens
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUGFn : INodeUnparser<NodeDefn> {
    override fun unparse(node: NodeDefn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.operation)
                unparseGenerics(node, unparser)
                append(name).append(' ')
                retgen?.let { append(it.genericName).append('^') } ?: append(rettype.nameWithGens)
                append(" [")
                argsn.let { if (modifiers.extension) it.drop(1) else it }.forEachIndexed { i, it ->
                    append('[').append(it).append(' ')
                    argsg[i]?.let { append(it.genericName).append('^') } ?: append(argsc[i].nameWithGens)
                    append(']')
                }
                append(']')
                NUDefault.unparseNodes(node, unparser, ctx, indent)
                append(')')
            }
        }
    }
}