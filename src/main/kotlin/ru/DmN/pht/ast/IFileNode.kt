package ru.DmN.pht.ast

/**
 * Нода, которая может быть файловой (тип/метод представляются в виде файла)
 */
interface IFileNode {
    /**
     * Нода файловая?
     */
    var file: Boolean
}