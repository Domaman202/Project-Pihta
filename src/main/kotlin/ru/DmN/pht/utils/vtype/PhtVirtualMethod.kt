package ru.DmN.pht.utils.vtype

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.vtype.MethodModifiers
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

abstract class PhtVirtualMethod : VirtualMethod() {
    /**
     * Тело метода если он является генератором, иначе null.
     */
    abstract val generator: List<Node>?

    /**
     * Generic's (Name / Type)
     */
    abstract val generics: Map<String, VirtualType>

    class Impl(
        override var declaringClass: VirtualType,
        //
        override var name: String,
        //
        override var rettype: VirtualType,
        override val retgen: String?,
        //
        override val argsc: List<VirtualType>,
        override val argsn: List<String>,
        override val argsg: List<String?>,
        //
        override val modifiers: MethodModifiers,
        //
        override val extension: VirtualType?,
        override val generator: List<Node>?,
        override var inline: Node?,
        //
        override val generics: Map<String, VirtualType>
    ) : PhtVirtualMethod()
}