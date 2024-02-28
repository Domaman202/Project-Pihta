package ru.DmN.pht.ast

import ru.DmN.pht.utils.meta.MetadataKeys.FINAL
import ru.DmN.pht.utils.meta.MetadataKeys.STATIC
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.meta.IMetadataKey
import ru.DmN.siberia.utils.node.INodeInfo

class NodeFieldA(info: INodeInfo, nodes: MutableList<Node>, static: Boolean = false, final: Boolean = false) : NodeNodesList(info, nodes), IStaticallyNode, IFinallyNode {
    override var static: Boolean = static
        set(value) {
            field = value
            visitMetadata(STATIC, value)
        }

    override var final: Boolean = final
        set(value) {
            field = value
            visitMetadata(FINAL, value)
        }

    override fun setMetadata(key: IMetadataKey, value: Any?) {
        when (key) {
            FINAL  -> final  = value as Boolean
            STATIC -> static = value as Boolean
            else -> super.setMetadata(key, value)
        }
    }

    override fun getMetadata(key: IMetadataKey): Any? =
        when (key) {
            FINAL  -> final
            STATIC -> static
            else -> super.getMetadata(key)
        }

    override fun copy(): NodeNodesList =
        NodeFieldA(info, copyNodes(), static, final)
}