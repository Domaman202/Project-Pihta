package ru.DmN.pht.std.base.compiler.java.ctx

import org.objectweb.asm.Label
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.SubList

class BodyContext(
    val children: MutableList<BodyContext>,
    val start: Label,
    val variables: MutableList<Variable>
) : Iterable<BodyContext> {
    lateinit var stop: Label

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
    }
}