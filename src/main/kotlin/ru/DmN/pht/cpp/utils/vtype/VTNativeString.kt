package ru.DmN.pht.cpp.utils.vtype

import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

object VTNativeString : PhtVirtualType() {
    override val name: String = "std.string"
    //
    override val parents: List<VirtualType>
        get() = emptyList()
    //
    override val fields: MutableList<VirtualField> = ArrayList()
    override val methods: MutableList<VirtualMethod> = ArrayList()
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
    override val isFile: Boolean
        get() = false
    override val isPrimitive: Boolean
        get() = false
    override val isNative: Boolean
        get() = true
    //
    override val genericsAccept: List<String>
        get() = emptyList()
    override val genericsDefine: Map<String, VirtualType>
        get() = emptyMap()
    override val genericsMap: Map<String, String>
        get() = emptyMap()
}