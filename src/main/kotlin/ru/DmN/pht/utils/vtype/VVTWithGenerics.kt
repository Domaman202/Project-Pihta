package ru.DmN.pht.utils.vtype

import ru.DmN.pht.utils.OrPair
import ru.DmN.siberia.utils.vtype.VirtualType

// Variable Virtual Type With Generics
class VVTWithGenerics(
    type: PhtVirtualType,
    val genericsData: Map<String, OrPair<VirtualType, String>>
) : VarVirtualType(type) {
    fun with(generics: Map<String, VirtualType>): VVTWithGenerics =
        VVTWithGenerics(type, genericsData.map { (k, v) -> Pair(k, OrPair.first<VirtualType, String>(if (!v.isFirst) generics[v.second()]!! else v.first!!)) }.toMap())

    override fun toString(): String {
            if (genericsDefine.isEmpty())
                return "VTWG($name)"
            val sb = StringBuilder()
        genericsDefine.values.forEachIndexed { i, it ->
                sb.append(it.name)
                if (i != genericsDefine.size - 1) {
                    sb.append(", ")
                }
            }
            return "VTWG($name<$sb>)"
    }
}