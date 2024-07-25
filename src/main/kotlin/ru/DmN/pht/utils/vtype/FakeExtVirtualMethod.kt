package ru.DmN.pht.utils.vtype

import ru.DmN.pht.jvm.utils.vtype.generator
import ru.DmN.pht.jvm.utils.vtype.generics
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.vtype.MethodModifiers
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

class FakeExtVirtualMethod(
    val method: VirtualMethod,
    override val extension: VirtualType?
) : PhtVirtualMethod() {
    override val declaringClass: VirtualType
        get() = this.method.declaringClass
    override val name: String
        get() = this.method.name
    override val rettype: VirtualType
        get() = this.method.rettype
    override val retgen: String?
        get() = this.method.retgen
    override val argsc: List<VirtualType>
        get() = this.method.argsc
    override val argsn: List<String>
        get() = this.method.argsn
    override val argsg: List<String?>
        get() = this.method.argsg
    override val modifiers: MethodModifiers
        get() = this.method.modifiers.copy(static = true, extension = true)
    override val generator: List<Node>?
        get() = this.method.generator
    override val inline: Node?
        get() = this.method.inline
    override val generics: Map<String, VirtualType>?
        get() = this.method.generics
}