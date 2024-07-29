package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeAGet
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCAGet : IValueNodeCompiler<NodeAGet> {
    override fun compileVal(node: NodeAGet, compiler: Compiler, ctx: CompilationContext): Variable =
        ctx.method.node.run {
            val arr = compiler.compileVal(node.arr, ctx)
            load(arr, this)
            val type = arr.type.componentType!!
            load(compiler.compileVal(node.index, ctx), this)
            visitInsn(
                when (type) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE    -> Opcodes.BALOAD
                    VirtualType.SHORT   -> Opcodes.SALOAD
                    VirtualType.CHAR    -> Opcodes.CALOAD
                    VirtualType.INT     -> Opcodes.IALOAD
                    VirtualType.LONG    -> Opcodes.LALOAD
                    VirtualType.FLOAT   -> Opcodes.FALOAD
                    VirtualType.DOUBLE  -> Opcodes.DALOAD
                    else                -> Opcodes.AALOAD
                }
            )
            Variable.tmp(node, type)
        }
}