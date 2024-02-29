package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node

/**
 * Нода, которая может быть открыта.
 */
interface IOpenlyNode : Node {
    /**
     * Нода открыта?
     */
    var open: Boolean
}