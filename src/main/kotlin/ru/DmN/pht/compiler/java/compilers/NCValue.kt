package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.desc
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.ast.NodeValue.Type.*
import ru.DmN.pht.std.compiler.java.utils.method

object NCValue : INodeCompiler<NodeValue> {
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
}