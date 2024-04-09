package ru.DmN.pht.processor.ctx

import ru.DmN.siberia.utils.SubList
import ru.DmN.siberia.utils.Variable
import java.util.concurrent.atomic.AtomicInteger

open class NamedBodyContext(
    parent: BodyContext?,
    children: MutableList<BodyContext>,
    variables: MutableList<Variable>,
    nvi: AtomicInteger,
    val name: String
) : BodyContext(parent, children, variables, nvi) {
    override fun copy(): NamedBodyContext =
        NamedBodyContext(parent, ArrayList(children), ArrayList(variables), AtomicInteger(nvi.get()), name)

    companion object {
        fun of(ctx: BodyContext?, name: String): NamedBodyContext =
            if (ctx == null)
                NamedBodyContext(null, ArrayList(), ArrayList(), AtomicInteger(0), name)
            else NamedBodyContext(ctx, ArrayList(), SubList(ctx.variables, ArrayList()), ctx.nvi, name).apply { ctx.children.add(this) }
    }
}