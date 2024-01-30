package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeInlBodyA
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.compiler.java.utils.method
import ru.DmN.pht.compiler.java.utils.with
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType

object NCInlBody : INodeCompiler<NodeInlBodyA> {
    override fun compile(node: NodeInlBodyA, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val endLabel = Label()
            if (node.type == VirtualType.VOID)
                NCDefault.compile(node, compiler, ctx.with { visitJumpInsn(Opcodes.GOTO, endLabel) })
            else {
                NCDefault.compileVal(node, compiler, ctx.with { visitJumpInsn(Opcodes.GOTO, endLabel) })
                visitInsn(Opcodes.POP)
            }
            visitLabel(endLabel)
        }
    }

    override fun compileVal(node: NodeInlBodyA, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            val endLabel = Label()
            load(NCDefault.compileVal(node, compiler, ctx.with { visitJumpInsn(Opcodes.GOTO, endLabel) }), this)
            visitLabel(endLabel)
        }
        return Variable.tmp(node, node.type)
    }
}