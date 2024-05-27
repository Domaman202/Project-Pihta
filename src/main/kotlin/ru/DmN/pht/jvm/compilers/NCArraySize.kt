package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.compilers.IValueNodeCompiler
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCArraySize : IValueNodeCompiler<NodeNodesList> {
    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            load(compiler.compileVal(node.nodes[0], ctx), this)
            visitInsn(Opcodes.ARRAYLENGTH)
        }
        return Variable.tmp(node, VirtualType.INT)
    }
}