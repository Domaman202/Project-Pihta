package ru.DmN.pht.std.fp.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.IValueNode
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.indent

class NodeValue(tkOperation: Token, val vtype: Type, val value: String) : Node(tkOperation), IValueNode {
    fun getBoolean() =
        value.toBoolean()
    fun getChar() =
        value.first()
    fun getInt() =
        value.toInt()
    fun getLong() =
        value.toLong()
    fun getFloat() =
        value.toFloat()
    fun getDouble() =
        value.toDouble()
    fun getString() =
        value

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append("[${tkOperation.text} ($vtype) $value]")

    override fun isConst(): Boolean = true
    override fun isConstClass(): Boolean = vtype == Type.PRIMITIVE || vtype == Type.CLASS
    override fun getValueAsString(): String = value

    enum class Type {
        NIL,
        BOOLEAN,
        CHAR,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        STRING,
        PRIMITIVE,
        CLASS,
        NAMING
    }

    companion object {
        fun of(line: Int, vtype: Type, value: String) =
            NodeValue(Token(line, Token.Type.OPERATION, "value"), vtype, value)
    }
}