package ru.DmN.pht.std.collections.compiler.java.compilers

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.load
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.collections.ast.NodeASet

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
            arr.type().componentType!!.run {
                if (isPrimitive)
                    when (name) {
                        "boolean", "byte", "int" -> Opcodes.IASTORE
                        "short" -> Opcodes.SASTORE
                        "char" -> Opcodes.CASTORE
                        "long" -> Opcodes.LASTORE
                        "float" -> Opcodes.FASTORE
                        "double" -> Opcodes.DASTORE
                        else -> throw RuntimeException()
                    }
                else Opcodes.AASTORE
            }
        )
    }
}