package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeCompare
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.utils.node.NodeTypes
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCCycle : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val start = Label()
            val stop = Label()
            visitLabel(start)
            val ifInsert =
                { node.nodes.drop(1).forEach { compiler.compile(it, ctx) }; visitJumpInsn(Opcodes.GOTO, start) }
            val elseInsert = { visitJumpInsn(Opcodes.GOTO, stop) }
            val cond = node.nodes[0]
            if (cond is NodeCompare)
                NCCompare.insertIf(
                    cond.info.type,
                    cond.nodes,
                    ifInsert,
                    elseInsert,
                    compiler, ctx
                )
            else NCCompare.insertIf(
                NodeTypes.EQ_,
                mutableListOf(cond, nodeValue(node.info, true)),
                ifInsert,
                elseInsert,
                compiler, ctx
            )
            visitLabel(stop)
        }
    }
}