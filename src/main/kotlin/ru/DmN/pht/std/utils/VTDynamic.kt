package ru.DmN.pht.std.utils

import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType

object VTDynamic : VirtualType() {
    override val name: String
        get() = "dynamic"
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

    override val simpleName: String
        get() = "dynamic"
    override val className: String
        get() = "java/lang/Object"
    override val superclass: VirtualType?
        get() = null
    override val interfaces: List<VirtualType>
        get() = emptyList()
    override val arrayType: VirtualType
        get() = ofKlass(Any::class.java).arrayType
    override val isPrimitive: Boolean
        get() = false
    override val isArray: Boolean
        get() = false
    override val desc: String
        get() = "Ljava/lang/Object;"
}