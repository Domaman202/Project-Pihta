package ru.DmN.pht.unparsers

import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.utils.nameWithGenerics
import ru.DmN.pht.std.utils.nameWithGens
import ru.DmN.pht.unparsers.NUDefn.unparseGenerics
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUEFn : INodeUnparser<NodeDefn> {
    override fun unparse(node: NodeDefn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.operation)
                unparseGenerics(node, unparser)
                append(extension!!.nameWithGenerics).append(' ').append(name).append(' ')
                retgen?.let { append(it).append('^') } ?: append(rettype.nameWithGens)
                append(" [")
                argsn.asSequence().drop(1).forEachIndexed { i, it ->
                    append('[').append(it).append(' ')
                    argsg[i]?.let { append(it).append('^') } ?: append(argsc[i].nameWithGens)
                    append(']')
                }
                append(']')
                NUDefault.unparseNodes(node, unparser, ctx, indent)
                append(')')
            }
        }
    }
}