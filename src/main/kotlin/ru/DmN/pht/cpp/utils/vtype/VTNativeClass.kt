package ru.DmN.pht.cpp.utils.vtype

import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

// Костыль
object VTNativeClass : VirtualType() {
    override val name: String
        get() = "Class"
    //
    override val parents: List<VirtualType>
        get() = emptyList()
    //
    override val fields: List<VirtualField>
        get() = emptyList()
    override val methods: List<VirtualMethod>
        get() = emptyList()
    //
    override val componentType: VirtualType?
        get() = null
    //
    override val isInterface: Boolean
        get() = false
    override val isAbstract: Boolean
        get() = false
    override val isFinal: Boolean
        get() = false
    override val isNative: Boolean
        get() = false
    override val isFile: Boolean
        get() = false
    override val isPrimitive: Boolean
        get() = false
}