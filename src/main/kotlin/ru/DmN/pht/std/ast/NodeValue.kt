package ru.DmN.pht.std.ast

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.IValueNode
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.indent

class NodeValue(tkOperation: Token, val vtype: Type, val value: String) : Node(tkOperation), IValueNode {
    fun getBoolean(): Boolean = value.toBoolean()
    fun getInt(): Int = value.toInt()
    fun getDouble(): Double = value.toDouble()
    fun getString(): String = value

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        return builder.indent(indent).append("[${tkOperation.text}\n")
            .indent(indent + 1).append("type = $vtype").append('\n')
            .indent(indent + 1).append("value = $value").append('\n')
            .indent(indent).append(']')
    }

    override fun isConst(): Boolean = true

    override fun getValueAsString(): String = value

    val type: String
        get() = when (this.vtype) {
            Type.NIL -> "java.lang.Object"
            Type.BOOLEAN -> "java.lang.Boolean"
            Type.INT -> "java.lang.Integer"
            Type.DOUBLE -> "java.lang.Double"
            Type.STRING, Type.NAMING -> "java.lang.String"
            Type.PRIMITIVE, Type.CLASS -> "java.lang.Class"
        }

    enum class Type {
        NIL,
        BOOLEAN,
        INT,
        DOUBLE,
        STRING,
        PRIMITIVE,
        CLASS,
        NAMING
    }
}