package ru.DmN.pht.jvm.utils.vtype

import ru.DmN.siberia.utils.vtype.VirtualType

interface IJvmVirtualType {

    /**
     * Имя в представлении JVM.
     */
    val VirtualType.jvmName: String
}