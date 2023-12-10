package ru.DmN.phtx.pcl.laxer

import ru.DmN.phtx.pcl.ast.*
import ru.DmN.siberia.lexer.Token

class Laxer(val text: String) {
    fun parse(line: Int): NodeElement {
        val ctx = Context(calcLine(line))
        return ctx.parse(line, ctx.calcOffset())
    }

    fun parseValue(line: Int): NodeElement {
        val ctx = Context(calcLine(line))
        return ctx.parseValue(line, ctx.calcOffset())
    }

    private fun calcLine(line: Int): Int {
        var i = 0
        var j = 0
        while (line != j) {
            if (text[i++] == '\n') {
                j++
            }
        }
        return i
    }

    inner class Context(var i: Int) {
        fun parse(line: Int, offset: Int): NodeElement {
            if (text[i] == '\'') {
                val name = parseName()
                return NodeNamedElement(
                    Token.operation(line, "named"),
                    offset,
                    name,
                    if (checkMap())
                        parseMap(line, offset)
                    else if (checkArray())
                        parseArray(line, offset)
                    else {
                        i--
                        parseValue(line, offset)
                    }
                )
            }
            return parseValue(line, offset)
        }

        private fun parseArray(line: Int, offset: Int): NodeElement =
            NodeLazyArray(Token.operation(line, "array"), offset, this@Laxer, calcArraySize(line, offset))

        private fun parseMap(line: Int, offset: Int): NodeElement =
            NodeLazyMap(Token.operation(line, "map"), offset, this@Laxer, calcArraySize(line, offset))

        fun parseValue(line: Int, offset: Int): NodeElement {
            skipSpace()
            return if (text[--i] == '"')
                parseValueString(line, offset)
            else parseValueBON(line, offset)
        }

        private fun parseValueBON(line: Int, offset: Int): NodeElement {
            val value = StringBuilder()
            while (text.length > i) {
                val last = text[i++]
                if (last == '\n')
                    break
                value.append(last)
            }
            return NodeValue(Token.operation(line, "value"), offset, NodeValue.Type.BON, value.toString())
        }

        private fun parseValueString(line: Int, offset: Int): NodeElement {
            val value = StringBuilder()
            var prev: Char?
            var last: Char? = null
            while (true) {
                prev = last
                last = text[++i]
                if (prev != '\\' && last == '"')
                    break
                value.append(last)
            }
            return NodeValue(Token.operation(line, "value"), offset, NodeValue.Type.STRING, value.toString())
        }

        private fun checkMap(): Boolean {
            skipSpace()
            return text[i] == '\n' && text[i - 1] == ':'
        }

        private fun checkArray(): Boolean {
            skipSpace()
            return text[i] == '\n' && text[i - 1] == ':' && text[i - 2] == ':'
        }

        private fun parseName(): String {
            i++
            val name = StringBuilder()
            while (text[i] != ':')
                name.append(text[i++])
            return name.toString()
        }

        private fun calcArraySize(line: Int, offset: Int): Int {
            var k = 0
            while (true) {
                if (calcLine(line, line + 1) && calcOffset() - 1 == offset)
                    k++
                else break
            }
            return k
        }

        fun calcOffset(): Int {
            skipSpace()
            var j = 0
            while (checkTextLen() && text[i++] == '-')
                j++
            return j
        }

        private fun calcLine(lastLine: Int, line: Int): Boolean {
            var j = lastLine
            while (line != j) {
                if (text.length == i)
                    return false
                if (text[i++] == '\n') {
                    j++
                }
            }
            return true
        }

        private fun skipSpace() {
            while (checkTextLen() && text[i++] == ' ');
        }

        private fun checkTextLen(): Boolean =
            text.length - 1 > i
    }
}