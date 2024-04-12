package ru.DmN.pht.cpp.utils.vtype

import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.utils.vtype.VirtualType

fun VirtualType.normalizedName() =
    cname.replace(".", "::")
fun VirtualType.defineName() =
    if (this is VVTAutoPointer)
        "dmn::pht::auto_ptr<${normalizedName()}>"
    else normalizedName()

inline val VirtualType.isPointer: Boolean
    get() = this is VVTPointer

inline val VirtualType.isAutoPointer: Boolean
    get() = this is VVTAutoPointer

inline val VirtualType.pointer: VVTPointer
    get() = VVTPointer(PhtVirtualType.of(this))

inline val VirtualType.autoPointer: VVTAutoPointer
    get() = VVTAutoPointer(PhtVirtualType.of(this))