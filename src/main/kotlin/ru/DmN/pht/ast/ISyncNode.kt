package ru.DmN.pht.ast

/**
 * Нода, которая может синхронизироваться.
 */
interface ISyncNode {
    /**
     * Нода синхронизироваться?
     */
    var sync: Boolean
}