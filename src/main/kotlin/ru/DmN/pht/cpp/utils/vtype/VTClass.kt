package ru.DmN.pht.cpp.utils.vtype

import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

// Костыль
object VTClass : VirtualType() {
    override val name: String
        get() = "Class"
    //
    override val parents: MutableList<VirtualType> = ArrayList()
    override val fields: MutableList<VirtualField> = ArrayList()
    override val methods: MutableList<VirtualMethod> = ArrayList()
    //
    override val isPrimitive: Boolean
        get() = false
    override val componentType: VirtualType?
        get() = null
    //
    override val isInterface: Boolean
        get() = false
    override val isAbstract: Boolean
        get() = false
    override val isFinal: Boolean
        get() = false
    override val isFile: Boolean
        get() = false
}