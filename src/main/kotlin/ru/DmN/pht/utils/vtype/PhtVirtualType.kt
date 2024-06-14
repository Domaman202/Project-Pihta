package ru.DmN.pht.utils.vtype

import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

abstract class PhtVirtualType : VirtualType() {
    /**
     * Generic's Accept
     */
    abstract val genericsAccept: List<String>

    /**
     * Generic's (Name / Type).
     */
    abstract val genericsDefine: Map<String, VirtualType>

    /**
     * Generic's Mapping to parents (This / Parent)
     */
    abstract val genericsMap: Map<String, String>

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
        override val isNative: Boolean = false,
        override var isFinal: Boolean = false,
        override var isFile: Boolean = false,
        //
        override var genericsAccept: MutableList<String> = ArrayList(),
        override var genericsDefine: MutableMap<String, VirtualType> = HashMap(),
        override var genericsMap: MutableMap<String, String> = HashMap()
    ) : PhtVirtualType()

    class Decorator(val type: VirtualType) : PhtVirtualType() {
        override val name: String
            get() = type.name
        override val cname: String
            get() = type.cname
        override val parents: List<VirtualType>
            get() = type.parents
        override val fields: List<VirtualField>
            get() = type.fields
        override val methods: List<VirtualMethod>
            get() = type.methods
        override val componentType: VirtualType?
            get() = type.componentType
        override val isInterface: Boolean
            get() = type.isInterface
        override val isAbstract: Boolean
            get() = type.isAbstract
        override val isNative: Boolean
            get() = type.isNative
        override val isFinal: Boolean
            get() = type.isFinal
        override val isFile: Boolean
            get() = type.isFile
        override val genericsAccept: List<String>
            get() = emptyList()
        override val genericsDefine: Map<String, VirtualType>
            get() = emptyMap()
        override val genericsMap: Map<String, String>
            get() = emptyMap()
    }

    companion object {
        fun of(type: VirtualType) =
            if (type is PhtVirtualType)
                type
            else Decorator(type)
    }
}