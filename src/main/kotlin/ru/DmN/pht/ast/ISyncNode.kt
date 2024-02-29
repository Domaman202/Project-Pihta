package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node

/**
 * Нода, которая может синхронизироваться.
 */
interface ISyncNode : Node {
    /**
     * Нода синхронизироваться?
     */
    var sync: Boolean
}