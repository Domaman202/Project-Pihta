package ru.DmN.siberia.unparsers

import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.unparser.UnparsingContext

/**
 * Де-парсер нод.
 */
interface INodeUnparser<T : Node> {
    /**
     * Де-перасит ноду.
     */
    fun unparse(node: T, unparser: Unparser, ctx: UnparsingContext, indent: Int)
}