package ru.DmN.pht.utils.vtype

// Variable Virtual Type Array
class VVTArray(type: PhtVirtualType) : VarVirtualType(type) {
    override val name: String
        get() = '[' + type.name
    override val componentType: PhtVirtualType
        get() = type
}