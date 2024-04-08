package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.DmN.pht.compiler.java.utils.bytecodeCast
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCASet : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val arr = compiler.compileVal(node.nodes[0], ctx)
            load(arr, this)
            load(compiler.compileVal(node.nodes[1], ctx), this)
            val value = compiler.compileVal(node.nodes[2], ctx)
            load(value, this)
            visitAStore(arr, value)
        }
    }

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable =
        ctx.method.node.run {
            val arr = compiler.compileVal(node.nodes[0], ctx)
            load(arr, this)
            load(compiler.compileVal(node.nodes[1], ctx), this)
            val value = compiler.compileVal(node.nodes[2], ctx)
            load(value, this)
            visitInsn(Opcodes.DUP_X2)
            visitAStore(arr, value)
            Variable.tmp(node, value.type)
        }

    private fun MethodVisitor.visitAStore(arr: Variable, value: Variable) {
        val componentType = arr.type.componentType!!
        if (value.type.isPrimitive)
            bytecodeCast(value.type.name, componentType.name, this)
        else visitTypeInsn(Opcodes.CHECKCAST, componentType.jvmName)
        visitAStore(arr)
    }

    private fun MethodVisitor.visitAStore(arr: Variable) {
        visitInsn(
            when (arr.type.componentType) {
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