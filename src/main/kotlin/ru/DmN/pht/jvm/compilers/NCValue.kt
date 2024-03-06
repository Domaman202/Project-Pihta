package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.pht.compiler.java.utils.method
import ru.DmN.pht.jvm.compilers.IStdNodeCompiler
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.desc
import ru.DmN.siberia.utils.vtype.VirtualType

object NCValue : IStdNodeCompiler<NodeValue, Nothing, Any?> {
    override fun compileVal(node: NodeValue, compiler: Compiler, ctx: CompilationContext): Variable =
        Variable.tmp(
            node,
            ctx.method.node.run {
                when (node.vtype) {
                    NIL -> {
                        visitInsn(Opcodes.ACONST_NULL)
                        VirtualType.ofKlass("java.lang.Object")
                    }
                    BOOLEAN -> {
                        visitLdcInsn(node.getBoolean())
                        VirtualType.BOOLEAN
                    }
                    CHAR -> {
                        visitLdcInsn(node.getChar())
                        VirtualType.CHAR
                    }
                    INT -> {
                        visitLdcInsn(node.getInt())
                        VirtualType.INT
                    }
                    LONG -> {
                        visitLdcInsn(node.getLong())
                        VirtualType.LONG
                    }
                    FLOAT -> {
                        visitLdcInsn(node.getFloat())
                        VirtualType.FLOAT
                    }
                    DOUBLE -> {
                        visitLdcInsn(node.getDouble())
                        VirtualType.DOUBLE
                    }
                    STRING, NAMING -> {
                        visitLdcInsn(node.getString())
                        VirtualType.ofKlass("java.lang.String")
                    }
                    PRIMITIVE, CLASS -> {
                        visitLdcInsn(Type.getType(node.value.desc))
                        VirtualType.ofKlass("java.lang.Class")
                    }
                    CLASS_WITH_GEN -> {
                        visitLdcInsn(Type.getType(node.value.substring(0, node.value.indexOf('<')).desc))
                        VirtualType.ofKlass("java.lang.Class")
                    }
                }
            }
        )

    override fun computeValue(node: NodeValue, compiler: Compiler, ctx: CompilationContext): Any? =
        when (node.vtype) {
            NIL -> null
            BOOLEAN -> node.getBoolean()
            CHAR    -> node.getChar()
            INT     -> node.getInt()
            LONG    -> node.getLong()
            FLOAT   -> node.getFloat()
            DOUBLE  -> node.getDouble()
            STRING  -> node.getString()
            PRIMITIVE, CLASS, CLASS_WITH_GEN -> node.getString()
            NAMING -> throw UnsupportedOperationException()
        }
}