package ru.DmN.pht.jvm.utils.vtype

import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.utils.vtype.VirtualType

class JavaVirtualTypeImpl(
    name: String,
    componentType: VirtualType?,
    isInterface: Boolean,
    isDynamic: Boolean,
    isFinal: Boolean
) : PhtVirtualType.Impl(
    name = name,
    componentType = componentType,
    isInterface = isInterface,
    isDynamic = isDynamic,
    isNative = true,
    isFinal = isFinal
), IJvmVirtualType {
    override val VirtualType.jvmName: String
        get() = this.name.replace('.', '/')
}