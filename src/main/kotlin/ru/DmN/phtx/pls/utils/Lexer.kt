package ru.DmN.phtx.pls.utils

import com.kingmang.lazurite.LZREx.LZRException
import com.kingmang.lazurite.core.KEYWORD
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.parser.Standart.Standart.*
import com.kingmang.lazurite.parser.Standart.range
import com.kingmang.lazurite.parser.pars.Token
import com.kingmang.lazurite.parser.pars.TokenType
import com.kingmang.lazurite.runtime.LZR.LZRNumber
import com.kingmang.lazurite.runtime.LZR.LZRString
import com.kingmang.lazurite.runtime.Value
import com.kingmang.lazurite.runtime.Variables

/**
 * @author ArtyomKingmang
 */
class Lexer(private val input: String, var pos: Int) {
    private val length: Int = input.length
    private val tokens: MutableList<Token>
    private val buffer: StringBuilder
    private var row: Int
    private var col = 1
    fun tokenize(): List<Token> {
        while (pos < length) {
            val current = peek(0)
            if (Character.isDigit(current)) tokenizeNumber() else if (isLZRIdentifier(current)) tokenizeWord() else if (current == '`') tokenizeExtendedWord() else if (current == '"') tokenizeText() else if (current == '#') {
                next()
                tokenizeHexNumber()
            } else if (OPERATOR_CHARS.indexOf(current) != -1) {
                if (!tokenizeOperator()) {
                    break
                }
            } else {
                // whitespaces
                next()
            }
        }
        return tokens
    }

    private fun tokenizeNumber() {
        clearBuffer()
        var current = peek(0)
        if (current == '0' && (peek(1) == 'x' || peek(1) == 'X')) {
            next()
            next()
            tokenizeHexNumber()
            return
        }
        while (true) {
            if (current == '.') {
                if (buffer.indexOf(".") != -1) throw error()
            } else if (!Character.isDigit(current)) {
                break
            }
            buffer.append(current)
            current = next()
        }
        addToken(TokenType.NUMBER, buffer.toString())
    }

    private fun tokenizeHexNumber() {
        clearBuffer()
        var current = peek(0)
        while (isHexNumber(current) || current == '_') {
            if (current != '_') {
                buffer.append(current)
            }
            current = next()
        }
        val length = buffer.length
        if (length > 0) {
            addToken(TokenType.HEX_NUMBER, buffer.toString())
        }
    }

    private var brackets: Int = 0

    private fun tokenizeOperator(): Boolean {
        var current = peek(0)
        if (current == '/') {
            if (peek(1) == '/') {
                next()
                next()
                tokenizeComment()
                return true
            } else if (peek(1) == '*') {
                next()
                next()
                tokenizeMultilineComment()
                return true
            }
        }
        clearBuffer()
        while (true) {
            val text = buffer.toString()
            if (text.isNotEmpty() && !OPERATORS.containsKey(text + current)) {
                val token = OPERATORS[text]
                if (token == TokenType.LPAREN)
                    brackets++
                else if (token == TokenType.RPAREN) {
                    brackets--
                    if (brackets < 0) {
                        return false
                    }
                }
                addToken(token)
                return true
            }
            buffer.append(current)
            current = next()
        }
        return true
    }

    private fun tokenizeWord() {
        clearBuffer()
        buffer.append(peek(0))
        var current = next()
        while (true) {
            if (!isLZRIdentifierPart(current)) {
                break
            }
            buffer.append(current)
            current = next()
        }
        val word = buffer.toString()
        if (KEYWORDS.containsKey(word)) {
            addToken(KEYWORDS[word])
        } else {
            addToken(TokenType.WORD, word)
        }
    }

    private fun tokenizeExtendedWord() {
        next() // skip `
        clearBuffer()
        var current = peek(0)
        while (true) {
            if (current == '`') break
            if (current == '\u0000') throw error()
            if (current == '\n' || current == '\r') throw error()
            buffer.append(current)
            current = next()
        }
        next() // skip closing `
        addToken(TokenType.WORD, buffer.toString())
    }

    private fun tokenizeText() {
        next() // skip "
        clearBuffer()
        var current = peek(0)
        while (true) {
            if (current == '\\') {
                current = next()
                when (current) {
                    '"' -> {
                        current = next()
                        buffer.append('"')
                        continue
                    }

                    '0' -> {
                        current = next()
                        buffer.append('\u0000')
                        continue
                    }

                    'b' -> {
                        current = next()
                        buffer.append('\b')
                        continue
                    }

                    'f' -> {
                        current = next()
                        buffer.append('\u000c')
                        continue
                    }

                    'n' -> {
                        current = next()
                        buffer.append('\n')
                        continue
                    }

                    'r' -> {
                        current = next()
                        buffer.append('\r')
                        continue
                    }

                    't' -> {
                        current = next()
                        buffer.append('\t')
                        continue
                    }

                    'u' -> {
                        val rollbackPosition = pos
                        while (current == 'u') current = next()
                        var escapedValue = 0
                        var i = 12
                        while (i >= 0 && escapedValue != -1) {
                            escapedValue = if (isHexNumber(current)) {
                                escapedValue or (current.digitToIntOrNull(16) ?: (-1 shl i))
                            } else {
                                -1
                            }
                            current = next()
                            i -= 4
                        }
                        if (escapedValue >= 0) {
                            buffer.append(escapedValue.toChar())
                        } else {
                            // rollback
                            buffer.append("\\u")
                            pos = rollbackPosition
                        }
                        continue
                    }
                }
                buffer.append('\\')
                continue
            }
            if (current == '"') break
            if (current == '\u0000') throw error()
            buffer.append(current)
            current = next()
        }
        next() // skip closing "
        addToken(TokenType.TEXT, buffer.toString())
    }

    init {
        tokens = ArrayList()
        buffer = StringBuilder()
        row = col
    }

    private fun tokenizeComment() {
        var current = peek(0)
        while ("\r\n\u0000".indexOf(current) == -1) {
            current = next()
        }
    }

    private fun tokenizeMultilineComment() {
        var current = peek(0)
        while (true) {
            if (current == '*' && peek(1) == '/') break
            if (current == '\u0000') throw error()
            current = next()
        }
        next() // *
        next() // /
    }

    private fun isLZRIdentifierPart(current: Char): Boolean {
        return Character.isLetterOrDigit(current) || current == '_' || current == '$'
    }

    private fun isLZRIdentifier(current: Char): Boolean {
        return Character.isLetter(current) || current == '_' || current == '$'
    }

    private fun clearBuffer() {
        buffer.setLength(0)
    }

    private operator fun next(): Char {
        pos++
        val result = peek(0)
        if (result == '\n') {
            row++
            col = 1
        } else col++
        return result
    }

    private fun peek(relativePosition: Int): Char {
        val position = pos + relativePosition
        return if (position >= length) '\u0000' else input[position]
    }

    private fun addToken(type: TokenType?, text: String = "") {
        tokens.add(Token(type, text, row, col))
    }

    private fun error(): LZRException {
        return LZRException("Lexer exeption", "Lexer error")
    }

    companion object {
        fun tokenize(input: String, pos: Int): List<Token> {
            return Lexer(input, pos).tokenize()
        }

        private const val OPERATOR_CHARS = "+-*/%()[]{}=<>!&|.,^~?:"
        private val tokenTypes = TokenType.entries.toTypedArray()
        private val keywords = arrayOf(
            "throw",
            "print",
            "println",
            "if",
            "else",
            "while",
            "for",
            "break",
            "continue",
            "func",
            "return",
            "using",
            "match",
            "case",
            "include",
            "class",
            "new",
            "enum"
        )

        //init keywords
        private val KEYWORDS: MutableMap<String, TokenType>

        init {
            KEYWORDS = HashMap()
            for (i in keywords.indices) {
                if (i < tokenTypes.size) {
                    KEYWORDS[keywords[i]] =
                        tokenTypes[i]
                } else {
                    println("Not enough token types for all tokens")
                    break
                }
            }
            variables()
            convertTypes()
            standart()
        }

        private fun variables() {
            Variables.define("object", LZRNumber.of(Types.OBJECT))
            Variables.define("num", LZRNumber.of(Types.NUMBER))
            Variables.define("string", LZRNumber.of(Types.STRING))
            Variables.define("array", LZRNumber.of(Types.ARRAY))
            Variables.define("map", LZRNumber.of(Types.MAP))
            Variables.define("function", LZRNumber.of(Types.FUNCTION))
        }

        private fun convertTypes() {
            KEYWORD.put(
                "str"
            ) { args: Array<Value> ->
                LZRString(
                    args[0].asString()
                )
            }
            KEYWORD.put(
                "num"
            ) { args: Array<Value> ->
                LZRNumber.of(
                    args[0].asNumber()
                )
            }
            KEYWORD.put(
                "byte"
            ) { args: Array<Value> ->
                LZRNumber.of(
                    args[0].asInt().toByte().toInt()
                )
            }
            KEYWORD.put(
                "short"
            ) { args: Array<Value> ->
                LZRNumber.of(
                    args[0].asInt().toShort().toInt()
                )
            }
            KEYWORD.put(
                "int"
            ) { args: Array<Value> ->
                LZRNumber.of(
                    args[0].asInt()
                )
            }
            KEYWORD.put(
                "long"
            ) { args: Array<Value> ->
                LZRNumber.of(
                    args[0].asNumber().toLong()
                )
            }
            KEYWORD.put(
                "float"
            ) { args: Array<Value> ->
                LZRNumber.of(
                    args[0].asNumber().toFloat()
                )
            }
            KEYWORD.put(
                "double"
            ) { args: Array<Value> ->
                LZRNumber.of(
                    args[0].asNumber()
                )
            }
        }

        private fun standart() {
            KEYWORD.put("echo", ECHO())
            KEYWORD.put("readln", INPUT())
            KEYWORD.put("length", LEN())
            KEYWORD.put("getBytes") { args: Array<Value?>? ->
                string.getBytes(
                    args
                )
            }
            KEYWORD.put("sprintf", SPRINTF())
            KEYWORD.put("range", range())
            KEYWORD.put("substring", SUBSTR())
            KEYWORD.put("parseInt") { args: Array<Value?>? ->
                PARSE.parseInt(
                    args
                )
            }
            KEYWORD.put("parseLong") { args: Array<Value?>? ->
                PARSE.parseLong(
                    args
                )
            }
            KEYWORD.put("foreach", FOREACH())
            KEYWORD.put("flatmap", FLATMAP())
            KEYWORD.put("split", split())
            KEYWORD.put("filter", FILTER(false))
        }

        private fun isHexNumber(current: Char): Boolean {
            return Character.isDigit(current) || current in 'a'..'f' || current in 'A'..'F'
        }

        private val OPERATORS: MutableMap<String, TokenType>

        init {
            OPERATORS = HashMap()
            OPERATORS["+"] = TokenType.PLUS
            OPERATORS["-"] = TokenType.MINUS
            OPERATORS["*"] = TokenType.STAR
            OPERATORS["/"] = TokenType.SLASH
            OPERATORS["%"] = TokenType.PERCENT
            OPERATORS["("] = TokenType.LPAREN
            OPERATORS[")"] = TokenType.RPAREN
            OPERATORS["["] = TokenType.LBRACKET
            OPERATORS["]"] = TokenType.RBRACKET
            OPERATORS["{"] = TokenType.LBRACE
            OPERATORS["}"] = TokenType.RBRACE
            OPERATORS["="] = TokenType.EQ
            OPERATORS["<"] = TokenType.LT
            OPERATORS[">"] = TokenType.GT
            OPERATORS["."] = TokenType.DOT
            OPERATORS[","] = TokenType.COMMA
            OPERATORS["^"] = TokenType.CARET
            OPERATORS["~"] = TokenType.TILDE
            OPERATORS["?"] = TokenType.QUESTION
            OPERATORS[":"] = TokenType.COLON
            OPERATORS["!"] = TokenType.EXCL
            OPERATORS["&"] = TokenType.AMP
            OPERATORS["|"] = TokenType.BAR
            OPERATORS["=="] = TokenType.EQEQ
            OPERATORS["!="] = TokenType.EXCLEQ
            OPERATORS["<="] = TokenType.LTEQ
            OPERATORS[">="] = TokenType.GTEQ
            OPERATORS["+="] = TokenType.PLUSEQ
            OPERATORS["-="] = TokenType.MINUSEQ
            OPERATORS["*="] = TokenType.STAREQ
            OPERATORS["/="] = TokenType.SLASHEQ
            OPERATORS["%="] = TokenType.PERCENTEQ
            OPERATORS["&="] = TokenType.AMPEQ
            OPERATORS["^="] = TokenType.CARETEQ
            OPERATORS["|="] = TokenType.BAREQ
            OPERATORS["::="] = TokenType.COLONCOLONEQ
            OPERATORS["<<="] = TokenType.LTLTEQ
            OPERATORS[">>="] = TokenType.GTGTEQ
            OPERATORS[">>>="] = TokenType.GTGTGTEQ
            OPERATORS["++"] = TokenType.PLUSPLUS
            OPERATORS["--"] = TokenType.MINUSMINUS
            OPERATORS["::"] = TokenType.COLONCOLON
            OPERATORS["&&"] = TokenType.AMPAMP
            OPERATORS["||"] = TokenType.BARBAR
            OPERATORS["<<"] = TokenType.LTLT
            OPERATORS[">>"] = TokenType.GTGT
            OPERATORS[">>>"] = TokenType.GTGTGT
            OPERATORS["@"] = TokenType.AT
            OPERATORS["@="] = TokenType.ATEQ
            OPERATORS[".."] = TokenType.DOTDOT
            OPERATORS["**"] = TokenType.STARSTAR
            OPERATORS["^^"] = TokenType.CARETCARET
            OPERATORS["?:"] = TokenType.QUESTIONCOLON
            OPERATORS["??"] = TokenType.QUESTIONQUESTION
        }
    }
}
