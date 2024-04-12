package ru.DmN.pht.utils.vtype

// Variable Virtual Type Array
class VVTArray(type: PhtVirtualType) : VarVirtualType(type) {
    override val name: String
        get() = '[' + type.name
    override val cname: String
        get() = '[' + type.cname
    override val componentType: PhtVirtualType
        get() = type
}