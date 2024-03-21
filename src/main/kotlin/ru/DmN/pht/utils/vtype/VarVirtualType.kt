package ru.DmN.pht.utils.vtype

import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

open class VarVirtualType(val type: PhtVirtualType) : PhtVirtualType() {
    override val name: String
        get() = type.name
    override val parents: List<VirtualType>
        get() = type.parents
    override val fields: List<VirtualField>
        get() = type.fields
    override val methods: List<VirtualMethod>
        get() = type.methods
    override val componentType: VirtualType?
        get() = type.componentType
    override val isInterface: Boolean
        get() = type.isInterface
    override val isAbstract: Boolean
        get() = type.isAbstract
    override val isFinal: Boolean
        get() = type.isFinal
    override val isFile: Boolean
        get() = type.isFile
    override val generics: Map<String, VirtualType>
        get() = type.generics
}