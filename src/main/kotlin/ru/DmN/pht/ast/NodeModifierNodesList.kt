package ru.DmN.pht.ast

import ru.DmN.pht.utils.meta.MetadataKeys.*
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.meta.IMetadataKey
import ru.DmN.siberia.utils.node.INodeInfo

class NodeModifierNodesList(info: INodeInfo, nodes: MutableList<Node>) : NodeNodesList(info, nodes),
    IAbstractlyNode, IFinallyNode, IInlinableNode, IOpenlyNode, IStaticallyNode, ISyncNode, IVarargNode {
    override var abstract: Boolean = false
        set(value) {
            field = value
            nodes.forEach {
                if (it is IAbstractlyNode) {
                    it.abstract = value
                }
            }
            visitMetadata(ABSTRACT, value)
        }

    override var final: Boolean = false
        set(value) {
            field = value
            nodes.forEach {
                if (it is IFinallyNode) {
                    it.final = value
                }
            }
            visitMetadata(FINAL, value)
        }

    override var inline: Boolean = false
        set(value) {
            field = value
            nodes.forEach {
                if (it is IInlinableNode) {
                    it.inline = value
                }
            }
            visitMetadata(INLINE, value)
        }

    override var open: Boolean = false
        set(value) {
            field = value
            nodes.forEach {
                if (it is IOpenlyNode) {
                    it.open = value
                }
            }
            visitMetadata(OPEN, value)
        }

    override var static: Boolean = false
        set(value) {
            field = value
            nodes.forEach {
                if (it is IStaticallyNode) {
                    it.static = value
                }
            }
            visitMetadata(STATIC, value)
        }

    override var sync: Boolean = false
        set(value) {
            field = value
            nodes.forEach {
                if (it is ISyncNode) {
                    it.sync = value
                }
            }
            visitMetadata(SYNC, value)
        }

    override var varargs: Boolean = false
        set(value) {
            field = value
            nodes.forEach {
                if (it is IVarargNode) {
                    it.varargs = value
                }
            }
            visitMetadata(VARARG, value)
        }

    override fun setMetadata(key: IMetadataKey, value: Any?) {
        when (key) {
            ABSTRACT -> abstract = value as Boolean
            FINAL    -> final    = value as Boolean
            INLINE   -> inline   = value as Boolean
            OPEN     -> open     = value as Boolean
            STATIC   -> static   = value as Boolean
            SYNC     -> sync     = value as Boolean
            VARARG   -> varargs  = value as Boolean
            else -> super.setMetadata(key, value)
        }
    }

    override fun getMetadata(key: IMetadataKey): Any? =
        when (key) {
            ABSTRACT -> abstract
            FINAL    -> final
            INLINE   -> inline
            OPEN     -> open
            STATIC   -> static
            SYNC     -> sync
            VARARG   -> varargs
            else -> super.getMetadata(key)
        }

    override fun copy(): NodeModifierNodesList =
        NodeModifierNodesList(info, copyNodes()).apply {
            abstract = this@NodeModifierNodesList.abstract
            final    = this@NodeModifierNodesList.final
            inline   = this@NodeModifierNodesList.inline
            open     = this@NodeModifierNodesList.open
            static   = this@NodeModifierNodesList.static
            sync     = this@NodeModifierNodesList.sync
            varargs  = this@NodeModifierNodesList.varargs
        }
}