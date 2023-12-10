package ru.DmN.phtx.pcl.laxer

import ru.DmN.phtx.pcl.ast.NodeLazyArray
import ru.DmN.phtx.pcl.ast.NodeElement
import ru.DmN.phtx.pcl.ast.NodeValue
import ru.DmN.siberia.lexer.Token

class Laxer(val text: String) {
    fun parse(line: Int): NodeElement {
        val ctx = Context(calcLine(line))
        return ctx.parse(line, ctx.calcOffset())
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
            val name = parseName()
            return if (checkArray()) parseArray(line, offset, name) else parseValue(line, offset, name)
        }

        private fun parseValue(line: Int, offset: Int, name: String): NodeValue {
            skipSpace()
            return if (text[--i] == '"')
                parseStringValue(line, offset, name)
            else parseNumberValue(line, offset, name)
        }

        private fun parseNumberValue(line: Int, offset: Int, name: String): NodeValue {
            val value = StringBuilder()
            while (true) {
                val last = text[++i]
                if (last != '\n')
                    break
                value.append(last)
            }
            return NodeValue(Token.operation(line, "value"), offset, name, NodeValue.Type.NUMBER, value.toString())
        }

        private fun parseStringValue(line: Int, offset: Int, name: String): NodeValue {
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
            return NodeValue(Token.operation(line, "value"), offset, name, NodeValue.Type.STRING, value.toString())
        }

        private fun parseArray(line: Int, offset: Int, name: String) =
            NodeLazyArray(Token.operation(line, "array"), offset, name, this@Laxer, calcArraySize(line, offset))

        private fun calcArraySize(line: Int, offset: Int): Int {
            var k = 0
            while (true) {
                if (calcLine(line, line + 1) && calcOffset() - 1 == offset)
                    k++
                else break
            }
            return k
        }

        private fun checkArray(): Boolean {
            skipSpace()
            return text[i] == '\n'
        }

        private fun parseName(): String {
            val name = StringBuilder()
            while (text[i] != ':')
                name.append(text[i++])
            return name.toString()
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