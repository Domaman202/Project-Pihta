package ru.DmN.siberia.parsers

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node

/**
 * Парсер нод
 */
interface INodeParser {
    /**
     * Парсит ноду.
     * Не обязан её возвращать.
     */
    fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node?

    /**
     * Пропускает ноду. (не парсит, просто пропускает её структуру)
     */
    fun skip(parser: Parser, ctx: ParsingContext, token: Token): Unit =
        throw UnsupportedOperationException()
}