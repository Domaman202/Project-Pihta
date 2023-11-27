package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.siberia.compiler.java.Compiler
import ru.DmN.siberia.compiler.java.compilers.INodeCompiler
import ru.DmN.siberia.compiler.java.utils.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.ast.NodeNewArray

object NCNewArray : INodeCompiler<NodeNewArray> {
    override fun compileVal(node: NodeNewArray, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            load(compiler.compileVal(node.size, ctx), this)
            if (node.type.isPrimitive) {
                visitIntInsn(
                    Opcodes.NEWARRAY,
                    when (node.type) {
                        VirtualType.BOOLEAN -> Opcodes.T_BOOLEAN
                        VirtualType.BYTE    -> Opcodes.T_BYTE
                        VirtualType.SHORT   -> Opcodes.T_SHORT
                        VirtualType.CHAR    -> Opcodes.T_CHAR
                        VirtualType.INT     -> Opcodes.T_INT
                        VirtualType.LONG    -> Opcodes.T_LONG
                        VirtualType.FLOAT   -> Opcodes.T_FLOAT
                        VirtualType.DOUBLE  -> Opcodes.T_DOUBLE
                        else -> throw RuntimeException()
                    }
                )
            } else visitTypeInsn(Opcodes.ANEWARRAY, node.type.className)
        }
        return Variable.tmp(node, node.type.arrayType)
    }
}