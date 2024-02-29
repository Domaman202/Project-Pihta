package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node

/**
 * Нода, которая может содержать переменные аргументы.
 */
interface IVarargNode : Node {
    /**
     * Нода содержит переменные аргументы?
     */
    var varargs: Boolean
}