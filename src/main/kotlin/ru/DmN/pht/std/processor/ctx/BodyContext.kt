package ru.DmN.pht.std.processor.ctx

import ru.DmN.siberia.utils.*
import java.util.concurrent.atomic.AtomicInteger

class BodyContext(
    val children: MutableList<BodyContext>,
    val variables: MutableList<Variable>,
    val nvi: AtomicInteger
) {
    fun copy(): BodyContext =
        TODO()

    operator fun get(name: String) =
        variables.find { it.name == name }

    fun addVariable(name: String, type: VirtualType?, tmp: Boolean = false): Variable =
        Variable(name, type, nvi.getAndIncrement(), tmp).apply { variables += this }

    fun addVariable(name: String, type: VirtualType?, tmp: Boolean = false, generics: List<VirtualType>): Variable =
        VariableWithGenerics(name, type, nvi.getAndIncrement(), tmp, generics).apply { variables += this }

    companion object {
        fun of(ctx: BodyContext?): BodyContext =
            if (ctx == null)
                BodyContext(ArrayList(), ArrayList(), AtomicInteger(0))
            else BodyContext(ArrayList(), SubList(ctx.variables, ArrayList()), ctx.nvi).apply { ctx.children.add(this) }

        fun of(method: VirtualMethod): BodyContext {
            val ctx = of(null)
            if (!method.modifiers.static)
                ctx.addVariable("this", method.declaringClass)
            method.argsc.forEachIndexed { i, it -> ctx.addVariable(method.argsn[i], it) }
            return ctx
        }
    }
}