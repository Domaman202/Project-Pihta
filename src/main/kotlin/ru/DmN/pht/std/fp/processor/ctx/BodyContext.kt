package ru.DmN.pht.std.fp.processor.ctx

import org.objectweb.asm.Label
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.utils.SubList
import java.util.concurrent.atomic.AtomicInteger

//class BodyContext(
//    val parent: BodyContext? = null,
//    val sub: MutableList<BodyContext> = ArrayList(),
//    val nvi: AtomicInteger = AtomicInteger(0),
//    val variables: MutableList<Variable> = ArrayList()) {
//    operator fun get(name: String): Variable? =
//        variables.find { it.name == name } ?: parent?.get(name)
//
//    fun addVariable(name: String, type: VirtualType?, tmp: Boolean = false): Variable =
//        Variable(name, type, nvi.getAndIncrement(), tmp).apply { variables += this }

//    private fun getAllVariables(): Collection<Pair<BodyContext, MutableList<Variable>>> {
//        val list = mutableListOf(Pair(this, variables))
//        parent?.let { list.addAll(it.getAllVariablesWithoutSub()) }
//        sub.forEach { list.addAll(it.getAllVariablesWithoutParent()) }
//        return list
//    }
//
//    private fun getAllVariablesWithoutSub(): Collection<Pair<BodyContext, MutableList<Variable>>> {
//        val list = mutableListOf(Pair(this, variables))
//        parent?.let { list.addAll(it.getAllVariablesWithoutSub()) }
//        return list
//    }
//
//    private fun getAllVariablesWithoutParent(): Collection<Pair<BodyContext, MutableList<Variable>>> {
//        val list = mutableListOf(Pair(this, variables))
//        sub.forEach { list.addAll(it.getAllVariablesWithoutParent()) }
//        return list
//    }
//
//    companion object {
//        fun of(ctx: BodyContext) =
//            BodyContext(ctx, nvi = ctx.nvi).apply { ctx.sub += this }
//    }
//}

class BodyContext(
    val children: MutableList<BodyContext>,
    val variables: MutableList<Variable>,
    val nvi: AtomicInteger
) {
    operator fun get(name: String) =
        variables.find { it.name == name }

    fun addVariable(name: String, type: VirtualType?, tmp: Boolean = false): Variable =
        Variable(name, type, nvi.getAndIncrement(), tmp).apply { variables += this }

    companion object {
        fun of(ctx: BodyContext?): BodyContext =
            if (ctx == null)
                BodyContext(ArrayList(), ArrayList(), AtomicInteger(0))
            else BodyContext(ArrayList(), SubList(ctx.variables, ArrayList()), ctx.nvi).apply { ctx.children.add(this) }
    }
}