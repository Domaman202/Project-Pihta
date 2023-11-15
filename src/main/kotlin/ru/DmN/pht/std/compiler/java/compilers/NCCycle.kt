package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.std.ast.NodeCompare
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.processor.utils.nodeValueOf
import ru.DmN.pht.std.utils.line
import ru.DmN.pht.std.utils.text

object NCCycle : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val start = Label()
            val stop = Label()
            visitLabel(start)
            val ifInsert = { node.nodes.drop(1).forEach { compiler.compile(it, ctx) }; visitJumpInsn(Opcodes.GOTO, start) }
            val elseInsert = { visitJumpInsn(Opcodes.GOTO, stop) }
            val cond = node.nodes[0]
            if (cond is NodeCompare) {
                NCCompare.insertIf(
                    cond.text,
                    cond.nodes,
                    ifInsert,
                    elseInsert,
                    compiler, ctx
                )
            } else {
                NCCompare.insertIf(
                    "!eq",
                    mutableListOf(cond, nodeValueOf(node.line, true)),
                    ifInsert,
                    elseInsert,
                    compiler, ctx
                )
            }
            visitLabel(stop)
        }
    }
}