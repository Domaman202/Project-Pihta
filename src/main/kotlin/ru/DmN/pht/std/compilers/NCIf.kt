package ru.DmN.pht.std.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCIf : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            compiler.compileVal(node.nodes.first(), ctx)
            val labelIf = Label()
            visitJumpInsn(Opcodes.IFEQ, labelIf)
            compiler.compile(node.nodes[1], ctx)
            val labelExit = Label()
            visitJumpInsn(Opcodes.GOTO, labelExit)
            visitLabel(labelIf)
            if (node.nodes.size == 3)
                compiler.compile(node.nodes[2], ctx)
            visitLabel(labelExit)
        }
    }

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            compiler.compileVal(node.nodes.first(), ctx)
            val labelIf = Label()
            visitJumpInsn(Opcodes.IFEQ, labelIf)
            val type = compiler.compileVal(node.nodes[1], ctx).apply { load(this, this@run) }.type
            val labelExit = Label()
            visitJumpInsn(Opcodes.GOTO, labelExit)
            visitLabel(labelIf)
            if (node.nodes.size == 3)
                compiler.compileVal(node.nodes[2], ctx).apply { load(this, this@run) }
            visitLabel(labelExit)
            return Variable("pht$${node.hashCode()}", type, -1, true)
        }
    }
}