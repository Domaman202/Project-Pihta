package ru.DmN.pht.std.ast

import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

class NodeValue(info: INodeInfo, val vtype: Type, val value: String) : Node(info), IValueNode {
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
        fun of(info: INodeInfo, vtype: Type, value: String) =
            NodeValue(info.withType(NodeTypes.VALUE), vtype, value)
    }
}