package ru.DmN.pht.cpp.utils.vtype

import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.pht.utils.vtype.VarVirtualType

// Variable Virtual Type Pointer
class VVTPointer(type: PhtVirtualType) : VarVirtualType(type) {
    override val name: String
        get() = '*' + type.name
    override val componentType: PhtVirtualType
        get() = type
}