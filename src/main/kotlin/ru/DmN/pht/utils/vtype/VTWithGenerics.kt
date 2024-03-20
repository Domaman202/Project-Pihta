package ru.DmN.pht.utils.vtype

import ru.DmN.pht.jvm.utils.vtype.generics
import ru.DmN.pht.utils.OrPair
import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

// Virtual Type With Generics
class VTWithGenerics(val type: VirtualType, val gens: Map<String, OrPair<VirtualType, String>>) : PhtVirtualType() {
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
    override val isFile: Boolean
        get() = type.isFile
    override val methods: List<VirtualMethod>
        get() = type.methods
    override val name: String
        get() = type.name
    override val parents: List<VirtualType>
        get() = type.parents

    fun with(generics: Map<String, VirtualType>): VTWithGenerics =
        VTWithGenerics(type, gens.map { (k, v) -> Pair(k, OrPair.first<VirtualType, String>(if (!v.isFirst) generics[v.second()]!! else v as VirtualType)) }.toMap())

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