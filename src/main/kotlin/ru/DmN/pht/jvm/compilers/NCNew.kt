package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeNew
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.compilers.IValueNodeCompiler
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCNew : IValueNodeCompiler<NodeNew> {
    override fun compileVal(node: NodeNew, compiler: Compiler, ctx: CompilationContext): Variable {
        node.ctor.run {
            val type = declaringClass.jvmName
            val desc = desc
            ctx.method.node.run {
                visitTypeInsn(Opcodes.NEW, type)
                visitInsn(Opcodes.DUP)
                node.nodes.forEach { load(compiler.compileVal(it, ctx), this) }
                visitMethodInsn(
                    Opcodes.INVOKESPECIAL,
                    type,
                    "<init>",
                    desc,
                    false
                )
            }
            return Variable.tmp(node, declaringClass)
        }
    }
}