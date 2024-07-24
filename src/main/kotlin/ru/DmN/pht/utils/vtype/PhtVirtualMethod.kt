package ru.DmN.pht.utils.vtype

import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
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
        override var argsc: List<VirtualType>,
        override var argsn: List<String>,
        override val argsg: List<String?>,
        //
        override var modifiers: MethodModifiers,
        //
        override var extension: VirtualType?,
        override val generator: List<Node>?,
        override var inline: Node?,
        //
        override var generics: Map<String, VirtualType>
    ) : PhtVirtualMethod()
}