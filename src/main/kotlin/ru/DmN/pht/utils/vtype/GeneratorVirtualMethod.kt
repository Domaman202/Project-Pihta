package ru.DmN.pht.utils.vtype

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.vtype.MethodModifiers
import ru.DmN.siberia.utils.vtype.VirtualType

class GeneratorVirtualMethod(
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
    override val generator: List<Node>?,
    //
    override var generics: Map<String, VirtualType>
) : PhtVirtualMethod() {
    override val inline: Node?
        get() = null
    override val extension: VirtualType?
        get() = null // todo: Пока что нереализуемо
}