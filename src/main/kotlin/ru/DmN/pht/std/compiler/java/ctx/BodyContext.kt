package ru.DmN.pht.std.compiler.java.ctx

import org.objectweb.asm.Label
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.SubList

class BodyContext(
    val children: MutableList<BodyContext>,
    val start: Label,
    val variables: MutableList<Variable>
) : Iterable<BodyContext> {
    lateinit var stop: Label

    fun add(name: String, type: VirtualType?) {
        variables += Variable(name, type, variables.size, false)
    }

    operator fun get(name: String) =
        variables.find { it.name == name }

    override fun iterator(): Iterator<BodyContext> =
        BodyIterator()

    inner class BodyIterator : Iterator<BodyContext> {
        var i = 0

        override fun hasNext(): Boolean =
            i < children.size

        override fun next(): BodyContext =
            children[i++]
    }

    companion object {
        fun of(ctx: BodyContext?, start: Label): BodyContext =
            if (ctx == null)
                BodyContext(ArrayList(), start, ArrayList())
            else BodyContext(ArrayList(), start, SubList(ctx.variables, ArrayList())).apply { ctx.children.add(this) }

        fun of(start: Label, method: VirtualMethod): BodyContext {
            val ctx = of(null, start)
            if (!method.modifiers.static)
                ctx.add("this", method.declaringClass)
            method.argsc.forEachIndexed { i, it -> ctx.add(method.argsn[i], it) }
            return ctx
        }
    }
}