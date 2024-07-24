package ru.DmN.pht.ast

import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.signature
import ru.DmN.pht.utils.meta.MetadataKeys.*
import ru.DmN.pht.utils.vtype.PhtVirtualMethod
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.meta.IMetadataKey
import ru.DmN.siberia.utils.node.INodeInfo

class NodeDefn(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val method: PhtVirtualMethod
) : NodeNodesList(info, nodes), IAbstractlyNode, IFileNode, IInlinableNode, IOpenlyNode, IStaticallyNode, ISyncNode, IVarargNode {
    override var abstract: Boolean
        set(value) { method.modifiers.abstract = value }
        get() = method.modifiers.abstract

    override var file: Boolean
        set(value) { method.modifiers.file = value }
        get() = method.modifiers.file

    override var inline: Boolean
        set(value) { method.modifiers.inline = value }
        get() = method.modifiers.inline

    override var open: Boolean
        set(value) { method.modifiers.final = !value }
        get() = !method.modifiers.final

    override var static: Boolean
        set(value) { method.modifiers.static = value }
        get() = method.modifiers.static

    override var sync: Boolean
        set(value) { method.modifiers.sync = value }
        get() = method.modifiers.sync

    override var varargs: Boolean
        set(value) { method.modifiers.varargs = value }
        get() = method.modifiers.varargs

    override fun setMetadata(key: IMetadataKey, value: Any?) {
        when (key) {
            ABSTRACT -> abstract = value as Boolean
            FILE     -> file     = value as Boolean
            INLINE   -> inline   = value as Boolean
            OPEN     -> open     = value as Boolean
            STATIC   -> static   = value as Boolean
            SYNC     -> sync     = value as Boolean
            VARARG   -> varargs  = value as Boolean
        }
    }

    override fun getMetadata(key: IMetadataKey): Any? =
        when (key) {
            ABSTRACT -> abstract
            FILE     -> file
            INLINE   -> inline
            OPEN     -> open
            STATIC   -> static
            SYNC     -> sync
            VARARG   -> varargs
            else     -> null
        }

    override fun copy(): NodeDefn =
        NodeDefn(info, copyNodes(), method).apply {
            this.abstract = this@NodeDefn.abstract
            this.file     = this@NodeDefn.file
            this.inline   = this@NodeDefn.inline
            this.open     = this@NodeDefn.open
            this.static   = this@NodeDefn.static
            this.sync     = this@NodeDefn.sync
            this.varargs  = this@NodeDefn.varargs
        }

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(type = ")
        if (method.modifiers.varargs)
            append("varargs ")
        append(
            if (method.modifiers.ctor)
                "constructor"
            else if (method.modifiers.abstract)
                "abstract method"
            else if (method.modifiers.extension)
                "extension method"
            else "method"
        ).append(")\n")
        if (short)
            indent(indent + 1).append("(desc = ").append(method.name).append(method.desc).append(')')
        else indent(indent + 1).append("(name = ").append(method.name).append(")\n")
            .indent(indent + 1).append("(desc = ").append(method.desc).append(")\n")
            .indent(indent + 1).append("(sign = ").append(method.signature).append(')')
        if (nodes.isEmpty())
            append('\n').indent(indent)
        else printNodes(this, indent, short)
        append(']')
    }
}