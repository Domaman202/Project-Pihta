package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.utils.vtype.genericName
import ru.DmN.pht.utils.vtype.nameWithGenerics
import ru.DmN.pht.utils.vtype.simpleName
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUClass : INodeUnparser<NodeType> {
    override fun unparse(node: NodeType, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(" [")
            node.type.genericsDefine.entries.forEach {
                append('[').append(it.key.genericName).append(' ').append(it.value.nameWithGenerics).append(']')
            }
            append("] ").append(node.type.simpleName).append(" [")
            node.type.parents.forEachIndexed { i, it ->
                if (i > 0)
                    append(' ')
                append('^').append(it.name)
            }
            append(']')
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}