package ru.DmN.pht.utils.vtype

import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

abstract class PhtVirtualType : VirtualType() {
    /**
     * Generic's (Name / Type)
     */
    abstract val generics: Map<String, VirtualType>

    open class Impl(
        override var name: String,
        //
        override var parents: MutableList<VirtualType> = ArrayList(),
        override var fields: MutableList<VirtualField> = ArrayList(),
        override var methods: MutableList<VirtualMethod> = ArrayList(),
        //
        override var componentType: VirtualType? = null,
        //
        override var isInterface: Boolean = false,
        override var isAbstract: Boolean = false,
        override var isFinal: Boolean = false,
        override var isFile: Boolean = false,
        //
        override var generics: MutableMap<String, VirtualType> = HashMap()
    ) : PhtVirtualType()
}