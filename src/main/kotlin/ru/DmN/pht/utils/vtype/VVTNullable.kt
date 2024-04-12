package ru.DmN.pht.utils.vtype

// Variable Virtual Type Nullable
class VVTNullable(type: PhtVirtualType, val nullable: Boolean) : VarVirtualType(type) {
    override val name: String
        get() = (if (nullable) '?' else '!') + type.name
    override val cname: String
        get() = type.name
}