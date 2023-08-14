package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.ast.NodeValue.Type.*

object NCValue : NodeCompiler<NodeValue>() {
    override fun calcType(node: NodeValue, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method)
            ctx.gctx.getType(
                compiler, when (node.vtype) {
                    NIL -> "java.lang.Object"
                    BOOLEAN -> "boolean"
                    INT -> "int"
                    DOUBLE -> "double"
                    STRING -> "java.lang.String"
                    PRIMITIVE, CLASS -> "java.lang.Class"
                    NAMING -> TODO()
                }
            )
        else null

    override fun compile(node: NodeValue, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret && ctx.type.method) {
            val mctx = ctx.mctx!!
            Variable(
                "(tmp$${node.hashCode()})",
                when (node.vtype) {
                    NIL -> {
                        mctx.node.visitInsn(Opcodes.ACONST_NULL)
                        "java.lang.Object"
                    }

                    BOOLEAN -> {
                        mctx.node.visitLdcInsn(node.getBoolean())
                        "boolean"
                    }

                    INT -> {
                        mctx.node.visitLdcInsn(node.getInt())
                        "int"
                    }

                    DOUBLE -> {
                        mctx.node.visitLdcInsn(node.getDouble())
                        "double"
                    }

                    STRING -> {
                        mctx.node.visitLdcInsn(node.getString())
                        "java.lang.String"
                    }

                    PRIMITIVE, CLASS -> {
                        mctx.node.visitLdcInsn(Type.getType(ctx.gctx.getType(compiler, node.value).desc))
                        "java.lang.Class"
                    }

                    NAMING -> TODO()
                }, -1, true
            )
        } else null
}