package ru.DmN.pht.std.base.ast

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

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        return builder.indent(indent).append("[${tkOperation.text}\n")
            .indent(indent + 1).append("type = $vtype").append('\n')
            .indent(indent + 1).append("value = $value").append('\n')
            .indent(indent).append(']')
    }

    override fun isConst(): Boolean = true
    override fun isConstClass(): Boolean = vtype == Type.PRIMITIVE || vtype == Type.CLASS
    override fun getValueAsString(): String = value

//    val type: String
//        get() = when (this.vtype) {
//            Type.NIL -> "java.lang.Object"
//            Type.BOOLEAN -> "java.lang.Boolean"
//            Type.CHAR -> "java.lang.Char"
//            Type.INT -> "java.lang.Integer"
//            Type.LONG -> "java.lang.Long"
//            Type.FLOAT -> "java.lang.Float"
//            Type.DOUBLE -> "java.lang.Double"
//            Type.STRING, Type.NAMING -> "java.lang.String"
//            Type.PRIMITIVE, Type.CLASS -> "java.lang.Class"
//        }

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
}