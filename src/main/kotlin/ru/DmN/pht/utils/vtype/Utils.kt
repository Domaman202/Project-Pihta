package ru.DmN.pht.utils.vtype

import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.siberia.utils.vtype.VirtualType

/**
 * Тип массива из элементов данного типа.
 */
val VirtualType.arrayType: VirtualType
    get() = PhtVirtualType.Impl("[${this.desc.replace('/', '.')}", componentType = this)