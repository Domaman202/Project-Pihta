package ru.DmN.pht.std.node

import ru.DmN.siberia.node.INodeInfo

val INodeInfo.processed
    get() = this.withType((this.type as IParsedNodeType).processed)