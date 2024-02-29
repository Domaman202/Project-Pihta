package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node

/**
 * Нода, которая может быть встроена.
 */
interface IInlinableNode : Node {
    /**
     * Нода встраиваемая?
     */
    var inline: Boolean
}