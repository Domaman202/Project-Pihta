package ru.DmN.pht.std.utils

import ru.DmN.siberia.utils.VirtualField
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

// Virtual Type With Generics
class VTWG(val type: VirtualType, val gens: List<VirtualType>) : VirtualType() {
    override val componentType: VirtualType?
        get() = type.componentType
    override val fields: List<VirtualField>
        get() = type.fields
    override val generics: List<Pair<String, VirtualType>>
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

    override fun toString(): String =
        "VTWG(${type.nameWithGenerics})"
}