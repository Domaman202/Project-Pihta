package ru.DmN.siberia.ast

import ru.DmN.siberia.lexer.Token

/**
 * Обработанная нода использования модулей.
 */
open class NodeParsedUse(
    tkOperation: Token,
    names: List<String>,
    nodes: MutableList<Node>,
    /**
     * Ноды для экспорта в использующий модули контекст.
     */
    val exports: MutableList<NodeNodesList>
) : NodeUse(tkOperation, names, nodes)