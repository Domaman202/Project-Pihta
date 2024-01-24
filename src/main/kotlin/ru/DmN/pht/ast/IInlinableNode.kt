package ru.DmN.pht.ast

/**
 * Нода, которая может быть встроена.
 */
interface IInlinableNode {
    /**
     * Нода встраиваемая?
     */
    var inline: Boolean
}