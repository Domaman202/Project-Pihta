package ru.DmN.pht.std.compiler.java.ctx

import org.objectweb.asm.Label
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.utils.Variable
import java.util.concurrent.atomic.AtomicInteger

class BodyContext(val parent: BodyContext?, val sub: MutableList<BodyContext> = ArrayList(), val nvi: AtomicInteger, val variables: MutableList<Variable> = ArrayList()) {
    lateinit var stopLabel: Label

    operator fun get(name: String): Variable? =
        variables.find { it.name == name } ?: parent?.get(name)

    fun addVariable(name: String, type: String?, tmp: Boolean = false): Variable =
        Variable(name, type, nvi.getAndIncrement(), tmp).apply { variables += this }

    fun visitAllVariables(compiler: Compiler, gctx: GlobalContext, cctx: ClassContext, mctx: MethodContext) {
        getAllVariables().forEach { pair ->
            pair.second.filter { !it.tmp }.forEach {
                val type = it.type()
                mctx.node.visitLocalVariable(
                    it.name,
                    cctx.getType(compiler, gctx, type).desc,
//                    cctx.getSignature(type), // todo:
                    null,
                    mctx.variableStarts[it.id],
                    pair.first.stopLabel,
                    it.id
                )
            }
        }
    }

    private fun getAllVariables(): Collection<Pair<BodyContext, MutableList<Variable>>> {
        val list = mutableListOf(Pair(this, variables))
        parent?.let { list.addAll(it.getAllVariablesWithoutSub()) }
        sub.forEach { list.addAll(it.getAllVariablesWithoutParent()) }
        return list
    }

    private fun getAllVariablesWithoutSub(): Collection<Pair<BodyContext, MutableList<Variable>>> {
        val list = mutableListOf(Pair(this, variables))
        parent?.let { list.addAll(it.getAllVariablesWithoutSub()) }
        return list
    }

    private fun getAllVariablesWithoutParent(): Collection<Pair<BodyContext, MutableList<Variable>>> {
        val list = mutableListOf(Pair(this, variables))
        sub.forEach { list.addAll(it.getAllVariablesWithoutParent()) }
        return list
    }

    companion object {
        fun of(ctx: MethodContext): BodyContext =
            BodyContext(null, nvi = ctx.nvi)

        fun of(ctx: BodyContext) =
            BodyContext(ctx, nvi = ctx.nvi).apply { ctx.sub += this }
    }
}