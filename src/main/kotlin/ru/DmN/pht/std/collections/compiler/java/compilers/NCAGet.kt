package ru.DmN.pht.std.collections.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.utils.load
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.collections.ast.NodeAGet

object NCAGet : INodeCompiler<NodeAGet> {
    override fun compileVal(node: NodeAGet, compiler: Compiler, ctx: CompilationContext): Variable =
        ctx.method.node.run {
            val arr = compiler.compileVal(node.arr, ctx)
            load(arr, this)
            val type = arr.type().componentType!!
            load(compiler.compileVal(node.index, ctx), this)
            visitInsn(
                if (type.isPrimitive)
                    when (type) {
                        VirtualType.BOOLEAN, VirtualType.BYTE, VirtualType.INT -> Opcodes.IALOAD
                        VirtualType.SHORT   -> Opcodes.SALOAD
                        VirtualType.CHAR    -> Opcodes.CALOAD
                        VirtualType.LONG    -> Opcodes.LALOAD
                        VirtualType.FLOAT   -> Opcodes.FALOAD
                        VirtualType.DOUBLE  -> Opcodes.DALOAD
                        else -> throw RuntimeException()
                    }
                else Opcodes.AALOAD
            )
            Variable.tmp(node, type)
        }
}