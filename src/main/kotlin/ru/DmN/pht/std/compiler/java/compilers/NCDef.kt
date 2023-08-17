package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.utils.load
import ru.DmN.pht.std.utils.store

object NCDef : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.method && ctx.type.body) {
            val mctx = ctx.method!!
            val label = Label()
            mctx.node.visitLabel(label)
            node.nodes.forEach { it ->
                val pair = compiler.compute(it, ctx, false) as List<Node>
                val value = pair.lastOrNull()?.let { compiler.compile(it, ctx, true)?.apply { load(this, mctx.node) } }
                val variable = ctx.body!!.addVariable(compiler.computeStringConst(pair.first(), ctx), value?.type)
                mctx.variableStarts[variable.id] = label
                if (value != null) {
                    store(variable, mctx.node)
                }
            }
        }
        return null
    }
}