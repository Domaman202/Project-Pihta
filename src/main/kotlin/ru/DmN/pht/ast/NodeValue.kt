package ru.DmN.pht.ast

import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.pht.utils.node.NodeTypes.VALUE
import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeValue(
    info: INodeInfo,
    val vtype: Type,
    val value: String
) : BaseNode(info), IValueNode {
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

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        indent(indent + 1).append("(type = ").append(vtype).append(")\n")
        indent(indent + 1).append("(value = ").append(value).append(")\n")
        indent(indent).append(']')
    }

    override fun isConstClass(): Boolean = when (vtype) { PRIMITIVE, CLASS, CLASS_WITH_GEN -> true else -> false }
    override fun getValueAsString(): String = value

    enum class Type(val clazz: Boolean = false) {
        NIL,
        BOOLEAN,
        CHAR,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        STRING,
        PRIMITIVE(true),
        CLASS(true),
        CLASS_WITH_GEN(true),
        NAMING
    }
}