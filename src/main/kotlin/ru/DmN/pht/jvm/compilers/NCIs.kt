package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCIs : INodeCompiler<NodeIsAs> {
    override fun compileVal(node: NodeIsAs, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            load(compiler.compileVal(node.nodes[0], ctx), this)
            visitTypeInsn(Opcodes.INSTANCEOF, node.type.className)
        }
        return Variable.tmp(node, VirtualType.BOOLEAN)
    }
}