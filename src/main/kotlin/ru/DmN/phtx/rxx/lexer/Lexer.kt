package ru.DmN.phtx.rxx.lexer

import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.phtx.rxx.lexer.TokenType.*
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType.*

class Lexer(val input: String) : Iterator<Token?> {
    var ptr = 0
    var line = 0

    override fun hasNext(): Boolean =
        ptr != input.length

    override fun next(): Token? {
        if (ptr >= input.length)
            return null
        while (ptr < input.length) {
            when (input[ptr]) {
                ' ', '\t', '\n' -> inc()
                else -> break
            }
        }
        if (ptr >= input.length)
            return null
        return when (val char = input[inc()]) {
            ':' -> Token(line, COLON, ":")
            ',' -> Token(line, COMMA, ",")
            '.' -> Token(line, DOT, ".")
            '"' -> {
                val sb = StringBuilder()
                var prev: Char? = null
                while (true) {
                    val c = input[ptr++]
                    sb.append(
                        when (c) {
                            '"' -> if (prev == '\\') '"' else break
                            'n' -> if (prev == '\\') '\n' else 'n'
                            't' -> if (prev == '\\') '\t' else 't'
                            '\\' -> if (prev == '\\') '\\' else continue
                            else -> c
                        }
                    )
                    prev = c
                }
                Token(line, STRING, sb.toString())
            }
            else -> {
                if (char.isDigit() || (char == '-' && input[ptr].isDigit())) {
                    val str = StringBuilder()
                    str.append(char)
                    while (ptr < input.length) {
                        val c = input[ptr]
                        if (c.isDigit() || (c == '.' && input[ptr + 1].isDigit())) {
                            inc()
                            str.append(c)
                        } else break
                    }
                    val type = when (input[inc()]) {
                        'i' -> INTEGER
                        'l' -> LONG
                        'f' -> FLOAT
                        'd' -> DOUBLE
                        else -> {
                            ptr--
                            if (str.contains('.'))
                                DOUBLE
                            else INTEGER
                        }
                    }
                    Token(line, type, str.toString())
                } else {
                    val sb = StringBuilder().append(char)
                    while (ptr < input.length) {
                        val c = input[ptr++]
                        if (c.isLetter())
                            sb.append(c)
                        else break
                    }
                    ptr--
                    Token(line, WORD, sb.toString())
                }
            }
        }
    }

    private fun inc(): Int {
        val i = ptr++
        return if (input[i] == '\n') {
            line++
            ptr
        } else i
    }
}