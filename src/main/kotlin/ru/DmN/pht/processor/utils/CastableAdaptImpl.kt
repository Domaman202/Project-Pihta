package ru.DmN.pht.std.processor.utils

import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.ast.IAdaptableNode

data class CastableAdaptImpl(val node: IAdaptableNode) : ICastable {
    override fun castableTo(to: VirtualType): Int =
        if (node.isAdaptableTo(to)) 0 else -1
}
