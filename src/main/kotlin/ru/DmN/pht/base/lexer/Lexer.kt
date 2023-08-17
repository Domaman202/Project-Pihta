package ru.DmN.pht.base.lexer

import ru.DmN.pht.base.utils.isPrimitive

class Lexer(private val input: String) : Iterator<Token?> {
    private var ptr: Int = 0
    private var line: Int = 0

    override fun hasNext(): Boolean = ptr != input.length

    override fun next(): Token? {
        if (ptr >= input.length)
            return null
        while (ptr < input.length && (input[ptr] == ' ' || input[ptr] == '\n'))
            inc()
        if (ptr >= input.length)
            return null
        when (val char = input[inc()]) {
            '(' -> return Token(line, Token.Type.OPEN_BRACKET, null)
            ')' -> return Token(line, Token.Type.CLOSE_BRACKET, null)
            '[' -> return Token(line, Token.Type.OPEN_CBRACKET, null)
            ']' -> return Token(line, Token.Type.CLOSE_CBRACKET, null)
            '^', '#' -> {
                val str = StringBuilder()
                while (ptr < input.length) {
                    val c = input[ptr]
                    if (c.isWhitespace() || c == '(' || c == ')' || c == '[' || c == ']')
                        break
                    else {
                        inc()
                        str.append(c)
                    }
                }
                return str.toString().let {
                    Token(
                        line,
                        if (char == '^') if (it.isPrimitive()) Token.Type.PRIMITIVE else Token.Type.CLASS else Token.Type.NAMING,
                        it
                    )
                }
            }
            '"' -> {
                val str = StringBuilder()
                var prev: Char? = null
                while (true) {
                    val c = input[inc()]
                    str.append(
                        when (c) {
                            '"' -> if (prev == '\\') '\"' else break
                            'n' -> if (prev == '\\') '\n' else 'n'
                            't' -> if (prev == '\\') '\t' else 't'
                            '\\' -> {
                                prev = if (prev == '\\') {
                                    str.append(c)
                                    null
                                } else c
                                continue
                            }

                            else -> c
                        }
                    )
                    prev = c
                }
                return Token(line, Token.Type.STRING, str.toString())
            }

            else -> {
                if (char.isDigit() || (char == '-' && input[ptr + 1].isDigit())) {
                    val str = StringBuilder()
                    str.append(char)
                    while (ptr < input.length) {
                        val c = input[ptr]
                        if (c.isDigit() || c == '.') {
                            inc()
                            str.append(c)
                        } else break
                    }
                    return Token(line, Token.Type.NUMBER, str.toString())
                }

                val str = StringBuilder()
                str.append(char)
                while (ptr < input.length) {
                    val c = input[ptr]
                    if (c.isWhitespace() || c == '(' || c == ')' || c == '[' || c == ']')
                        break
                    else {
                        inc()
                        str.append(c)
                    }
                }
                return str.toString().let {
                    Token(line, if (it == "nil") Token.Type.NIL else if (it == "true" || it == "false") Token.Type.BOOLEAN else if (it.endsWith('^')) Token.Type.CLASS else Token.Type.OPERATION, it)
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