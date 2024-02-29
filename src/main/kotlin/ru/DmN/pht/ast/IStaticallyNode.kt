package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node

/**
 * Нода, которая может быть статической.
 */
interface IStaticallyNode : Node {
    /**
     * Нода статическая?
     */
    var static: Boolean
}