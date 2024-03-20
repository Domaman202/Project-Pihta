package ru.DmN.pht.utils.vtype

import ru.DmN.pht.jvm.utils.vtype.IJvmVirtualType
import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

object VTDynamic : PhtVirtualType(), IJvmVirtualType {
    override val name: String
        get() = "dynamic"
    override val VirtualType.jvmName: String
        get() = "java/lang/Object"
    override val parents: List<VirtualType>
        get() = emptyList()
    override val fields: List<VirtualField>
        get() = emptyList()
    override val methods: List<VirtualMethod>
        get() = emptyList()
    override val componentType: VirtualType?
        get() = null
    override val isInterface: Boolean
        get() = false
    override val isAbstract: Boolean
        get() = false
    override val isFinal: Boolean
        get() = false
    override val isFile: Boolean
        get() = false
    override val generics: Map<String, VirtualType>
        get() = emptyMap()
    override val isPrimitive: Boolean
        get() = false
    override val isArray: Boolean
        get() = false
}