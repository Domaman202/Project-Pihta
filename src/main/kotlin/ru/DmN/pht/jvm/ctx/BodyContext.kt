package ru.DmN.pht.std.compiler.java.ctx

import org.objectweb.asm.Label
import ru.DmN.siberia.utils.SubList
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import java.util.concurrent.atomic.AtomicInteger

class BodyContext(
    val children: MutableList<BodyContext>,
    val start: Label,
    val variables: MutableList<Variable>,
    val lvi: AtomicInteger
) : Iterable<BodyContext> {
    lateinit var stop: Label

    fun add(name: String, type: VirtualType?): Variable =
        Variable(name, type, lvi.getAndIncrement(), false).apply {
            variables += this
            if (type == VirtualType.LONG || type == VirtualType.DOUBLE) {
                lvi.incrementAndGet()
            }
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
                BodyContext(ArrayList(), start, ArrayList(), AtomicInteger(0))
            else BodyContext(ArrayList(), start, SubList(ctx.variables, ArrayList()), ctx.lvi).apply { ctx.children.add(this) }

        fun of(start: Label, method: VirtualMethod): BodyContext {
            val ctx = of(null, start)
            if (!method.modifiers.static)
                ctx.add("this", method.declaringClass)
            method.argsc.forEachIndexed { i, it -> ctx.add(method.argsn[i], it) }
            return ctx
        }
    }
}