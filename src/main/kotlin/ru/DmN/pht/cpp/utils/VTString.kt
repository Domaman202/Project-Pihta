package ru.DmN.pht.cpp.utils

import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

object VTString : VirtualType() {
    override val name: String = "std.string"
    //
    override val parents: MutableList<VirtualType> = ArrayList()
    override val fields: MutableList<VirtualField> = ArrayList()
    override val methods: MutableList<VirtualMethod> = ArrayList()
    //
    override val isPrimitive: Boolean
        get() = true
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
    //
    override val generics: MutableMap<String, VirtualType>
        get() = HashMap()
}