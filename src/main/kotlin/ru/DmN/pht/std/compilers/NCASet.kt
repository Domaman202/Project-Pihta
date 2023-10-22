package ru.DmN.pht.std.compilers

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.ast.NodeASet

object NCASet : INodeCompiler<NodeASet> {
    override fun compile(node: NodeASet, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val arr = compiler.compileVal(node.arr, ctx)
            load(arr, this)
            load(compiler.compileVal(node.index, ctx), this)
            load(compiler.compileVal(node.value, ctx), this)
            visitAStore(arr)
        }
    }

    override fun compileVal(node: NodeASet, compiler: Compiler, ctx: CompilationContext): Variable =
        ctx.method.node.run {
            val arr = compiler.compileVal(node.arr, ctx)
            load(arr, this)
            load(compiler.compileVal(node.index, ctx), this)
            val value = compiler.compileVal(node.value, ctx)
            load(value, this)
            visitInsn(Opcodes.DUP_X2)
            visitAStore(arr)
            Variable.tmp(node, value.type)
        }

    private fun MethodVisitor.visitAStore(arr: Variable) {
        visitInsn(
            when (arr.type().componentType) {
                VirtualType.BOOLEAN,
                VirtualType.BYTE,   -> Opcodes.BASTORE
                VirtualType.SHORT   -> Opcodes.SASTORE
                VirtualType.CHAR    -> Opcodes.CASTORE
                VirtualType.INT     -> Opcodes.IASTORE
                VirtualType.LONG    -> Opcodes.LASTORE
                VirtualType.FLOAT   -> Opcodes.FASTORE
                VirtualType.DOUBLE  -> Opcodes.DASTORE
                else                -> Opcodes.AASTORE
            }
        )
    }
}