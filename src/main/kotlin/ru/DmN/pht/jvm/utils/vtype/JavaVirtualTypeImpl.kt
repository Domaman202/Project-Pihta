package ru.DmN.pht.jvm.utils.vtype

import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

class JavaVirtualTypeImpl(
    name: String,
    //
    parents: MutableList<VirtualType>,
    fields: MutableList<VirtualField>,
    methods: MutableList<VirtualMethod>,
    //
    componentType: VirtualType?,
    //
    isInterface: Boolean,
    isFinal: Boolean
) : PhtVirtualType.Impl(name, parents, fields, methods, componentType, isInterface = isInterface, isFinal = isFinal), IJvmVirtualType {
    override val VirtualType.jvmName: String
        get() = this.name.replace('.', '/')
}