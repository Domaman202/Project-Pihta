package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType.OPERATION
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.text

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
        builder.indent(indent).append("[${text} ($vtype) $value]")

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
        CLASS_WITH_GEN,
        NAMING
    }

    companion object {
        fun of(line: Int, vtype: Type, value: String) =
            NodeValue(Token(line, OPERATION, "value"), vtype, value)
    }
}