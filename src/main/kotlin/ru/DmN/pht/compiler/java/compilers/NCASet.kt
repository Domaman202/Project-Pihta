package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.ast.NodeASet
import ru.DmN.pht.std.compiler.java.utils.bytecodeCast

object NCASet : INodeCompiler<NodeASet> {
    override fun compile(node: NodeASet, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val arr = compiler.compileVal(node.arr, ctx)
            load(arr, this)
            load(compiler.compileVal(node.index, ctx), this)
            val value = compiler.compileVal(node.value, ctx)
            load(value, this)
            visitAStore(arr, value)
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
            visitAStore(arr, value)
            Variable.tmp(node, value.type)
        }

    private fun MethodVisitor.visitAStore(arr: Variable, value: Variable) {
        val componentType = arr.type().componentType!!
        if (value.type().isPrimitive)
            bytecodeCast(value.type().name, componentType.name, this)
        else visitTypeInsn(Opcodes.CHECKCAST, componentType.className)
        visitAStore(arr)
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