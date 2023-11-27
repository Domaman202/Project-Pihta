package ru.DmN.siberia.ast

import ru.DmN.siberia.lexer.Token

/**
 * Обработанная нода использования модулей.
 */
class NodeProcessedUse(
    tkOperation: Token,
    names: List<String>,
    nodes: MutableList<Node>,
    exports: MutableList<NodeNodesList>,
    /**
     * Обработанные ноды используемых нод.
     */
    val processed: MutableList<Node>
) : NodeParsedUse(tkOperation, names, nodes, exports)