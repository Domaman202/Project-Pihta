package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Label
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.*
import ru.DmN.pht.std.base.utils.load
import ru.DmN.pht.std.base.utils.loadCast
import ru.DmN.pht.std.base.utils.store

object NCDef : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val mctx = ctx.method
        val bctx = ctx.body
        val mnode = mctx.node
        val label = Label()
        mnode.visitLabel(label)
        compiler.compute<List<Node>>(node.nodes.first(), ctx, ComputeType.NODE).forEach { it ->
            val pair = compiler.compute<List<Node>>(it, ctx, ComputeType.NODE)
                .map { compiler.compute<Node>(it, ctx, ComputeType.NODE) }
            if (pair[0].isConstClass()) {
                val variable = bctx.addVariable(pair[1].getValueAsString(), pair[0].getValueAsString())
                mctx.variableStarts[variable.id] = label
                pair.getOrNull(2)?.let {
                    compiler.compile(it, ctx, true)?.apply {
                        loadCast(this, ctx.global.getType(compiler, variable.type!!), mnode)
                        store(variable, mnode)
                    }
                }
            } else {
                val value = pair.getOrNull(1)?.let { compiler.compile(it, ctx, true)?.apply { load(this, mnode) } }
                val variable = bctx.addVariable(pair[0].getValueAsString(), value?.type)
                mctx.variableStarts[variable.id] = label
                if (value != null) {
                    store(variable, mnode)
                }
            }
        }
        return null
    }
}