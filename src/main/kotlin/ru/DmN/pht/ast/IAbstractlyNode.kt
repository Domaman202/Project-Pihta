package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node

/**
 * Нода, которая может быть абстрактной
 */
interface IAbstractlyNode : Node {
    /**
     * Нода абстрактна?
     */
    var abstract: Boolean
}