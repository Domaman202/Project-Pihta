package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node

/**
 * Нода, которая может быть финальная.
 */
interface IFinallyNode : Node {
    /**
     * Нода финальная?
     */
    var final: Boolean
}