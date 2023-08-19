package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.ast.NodeValue.Type.*
import ru.DmN.pht.std.compiler.java.ctx.global
import ru.DmN.pht.std.compiler.java.ctx.method

object NCValue : NodeCompiler<NodeValue>() {
    override fun calc(node: NodeValue, compiler: Compiler, ctx: CompilationContext): VirtualType? =
            ctx.global.getType(
                compiler, when (node.vtype) {
                    NIL -> "java.lang.Object"
                    BOOLEAN -> "boolean"
                    INT -> "int"
                    DOUBLE -> "double"
                    STRING -> "java.lang.String"
                    PRIMITIVE, CLASS -> "java.lang.Class"
                    NAMING -> throw RuntimeException()
                }
            )

    override fun compile(node: NodeValue, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret) {
            val mnode = ctx.method.node
            Variable(
                "(tmp$${node.hashCode()})",
                when (node.vtype) {
                    NIL -> {
                        mnode.visitInsn(Opcodes.ACONST_NULL)
                        "java.lang.Object"
                    }

                    BOOLEAN -> {
                        mnode.visitLdcInsn(node.getBoolean())
                        "boolean"
                    }

                    INT -> {
                        mnode.visitLdcInsn(node.getInt())
                        "int"
                    }

                    DOUBLE -> {
                        mnode.visitLdcInsn(node.getDouble())
                        "double"
                    }

                    STRING -> {
                        mnode.visitLdcInsn(node.getString())
                        "java.lang.String"
                    }

                    PRIMITIVE, CLASS -> {
                        mnode.visitLdcInsn(Type.getType(ctx.global.getType(compiler, node.value).desc))
                        "java.lang.Class"
                    }

                    NAMING -> throw RuntimeException()
                }, -1, true
            )
        } else null

    override fun compute(node: NodeValue, compiler: Compiler, ctx: CompilationContext, name: Boolean): Any? =
        if (name) node.value else node
//        when (node.vtype) {
//            NIL -> null
//            BOOLEAN -> node.getBoolean()
//            INT -> node.getInt()
//            DOUBLE -> node.getDouble()
//            STRING, NAMING -> node.getString()
//            PRIMITIVE, CLASS -> node.getString()
////            PRIMITIVE, CLASS -> if (ctx.type.clazz) ctx.clazz!!.getType(compiler, ctx.global, node.getString()) else ctx.global.getType(compiler, node.getString())
//        }
}