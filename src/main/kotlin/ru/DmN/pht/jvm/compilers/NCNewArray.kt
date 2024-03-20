package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeNewArray
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.pht.utils.vtype.arrayType
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

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
            } else visitTypeInsn(Opcodes.ANEWARRAY, node.type.jvmName)
        }
        return Variable.tmp(node, node.type.arrayType)
    }
}