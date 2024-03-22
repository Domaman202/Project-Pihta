package ru.DmN.pht.utils.vtype

import ru.DmN.pht.utils.OrPair
import ru.DmN.siberia.utils.vtype.VirtualType

// Variable Virtual Type With Generics
class VVTWithGenerics(type: PhtVirtualType, val gens: Map<String, OrPair<VirtualType, String>>) : VarVirtualType(type) {
    fun with(generics: Map<String, VirtualType>): VVTWithGenerics =
        VVTWithGenerics(type, gens.map { (k, v) -> Pair(k, OrPair.first<VirtualType, String>(if (!v.isFirst) generics[v.second()]!! else v.first!!)) }.toMap())

    override fun toString(): String {
            if (generics.isEmpty())
                return "VTWG($name)"
            val sb = StringBuilder()
            generics.values.forEachIndexed { i, it ->
                sb.append(it.name)
                if (i != generics.size - 1) {
                    sb.append(", ")
                }
            }
            return "VTWG($name<$sb>)"
    }
}