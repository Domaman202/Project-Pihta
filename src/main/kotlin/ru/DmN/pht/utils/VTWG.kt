package ru.DmN.pht.utils

import ru.DmN.pht.utils.OrPair
import ru.DmN.siberia.utils.VirtualField
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

// Virtual Type With Generics
class VTWG(val type: VirtualType, val gens: Map<String, OrPair<VirtualType, String>>) : VirtualType() {
    override val componentType: VirtualType?
        get() = type.componentType
    override val fields: List<VirtualField>
        get() = type.fields
    override val generics: Map<String, VirtualType>
        get() = type.generics
    override val isAbstract: Boolean
        get() = type.isAbstract
    override val isFinal: Boolean
        get() = type.isFinal
    override val isInterface: Boolean
        get() = type.isInterface
    override val methods: List<VirtualMethod>
        get() = type.methods
    override val name: String
        get() = type.name
    override val parents: List<VirtualType>
        get() = type.parents

    fun with(generics: Map<String, VirtualType>): VTWG =
        VTWG(type, gens.map { (k, v) -> Pair(k, OrPair.first<VirtualType, String>(if (!v.isFirst) generics[v.second()]!! else v as VirtualType)) }.toMap())

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