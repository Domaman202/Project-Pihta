package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.jvm.utils.vtype.genericsDefine
import ru.DmN.pht.utils.vtype.genericName
import ru.DmN.pht.utils.vtype.nameWithGenerics
import ru.DmN.pht.utils.vtype.nameWithGens
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUDefn : INodeUnparser<NodeDefn> {
    override fun unparse(node: NodeDefn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.operation)
                unparseGenerics(node, unparser)
                append(name).append(' ')
                retgen?.let { append(it.genericName).append('^') } ?: append(rettype.nameWithGens)
                append(" [")
                argsn.forEachIndexed { i, it ->
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

    fun unparseGenerics(node: NodeDefn, unparser: Unparser) {
        unparser.out.apply {
            append(" [")
            node.method.generics?.let { it ->
                it.entries
                .stream()
                .skip(node.method.declaringClass.genericsDefine.size.toLong())
                .forEach { append('[').append(it.key.genericName).append(' ').append(it.value.nameWithGenerics).append(']') }
            }
            append("] ")
        }
    }
}