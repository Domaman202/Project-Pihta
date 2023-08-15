package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeVar
import ru.DmN.pht.std.utils.load
import ru.DmN.pht.std.utils.store

object NCDef : NodeCompiler<NodeVar>() {
    override fun compile(node: NodeVar, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.method && ctx.type.body) {
            val mctx = ctx.method!!
            val label = Label()
            mctx.node.visitLabel(label)
            node.variables.forEach { it ->
                val value = it.second?.let { compiler.compile(it, ctx, true)?.apply { load(this, mctx.node) } }
                val variable = ctx.body!!.addVariable(it.first, value?.type)
                mctx.variableStarts[variable.id] = label
                if (value != null) {
                    store(variable, mctx.node)
                }
            }
        }
        return null
    }
}