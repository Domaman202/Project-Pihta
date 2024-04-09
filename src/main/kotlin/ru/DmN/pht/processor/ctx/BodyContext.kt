package ru.DmN.pht.processor.ctx

import ru.DmN.siberia.utils.SubList
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType
import java.util.concurrent.atomic.AtomicInteger

open class BodyContext(
    val parent: BodyContext?,
    val children: MutableList<BodyContext>,
    val variables: MutableList<Variable>,
    val nvi: AtomicInteger
) {
    open fun copy(): BodyContext =
        BodyContext(parent, ArrayList(children), ArrayList(variables), AtomicInteger(nvi.get()))

    operator fun get(name: String) =
        variables.find { it.name == name }

    fun addVariable(name: String, type: VirtualType, tmp: Boolean = false): Variable =
        Variable(name, type, nvi.getAndIncrement(), tmp).apply { variables += this }

    companion object {
        fun of(ctx: BodyContext?): BodyContext =
            if (ctx == null)
                BodyContext(null, ArrayList(), ArrayList(), AtomicInteger(0))
            else BodyContext(ctx, ArrayList(), SubList(ctx.variables, ArrayList()), ctx.nvi).apply { ctx.children.add(this) }


        fun of(ctx: BodyContext?, variables: MutableList<Variable>, nvi: AtomicInteger): BodyContext =
            if (ctx == null)
                BodyContext(null, ArrayList(), ArrayList(), AtomicInteger(0))
            else BodyContext(ctx, ArrayList(), SubList(ctx.variables, variables), nvi).apply { ctx.children.add(this) }

        fun of(method: VirtualMethod): BodyContext {
            val ctx = of(null)
            if (!method.modifiers.static)
                ctx.addVariable("this", method.declaringClass)
            method.argsc.forEachIndexed { i, it -> ctx.addVariable(method.argsn[i], it) }
            return ctx
        }
    }
}