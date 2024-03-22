package ru.DmN.pht.cpp.utils.vtype

import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.pht.utils.vtype.VarVirtualType

// Variable Virtual Type Auto Pointer
class VVTAutoPointer(type: PhtVirtualType) : VarVirtualType(type) {
    override val componentType: PhtVirtualType
        get() = type
}