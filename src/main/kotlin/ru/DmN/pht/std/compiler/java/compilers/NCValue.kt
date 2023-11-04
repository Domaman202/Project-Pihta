package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.desc
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.compiler.java.utils.method

object NCValue : INodeCompiler<NodeValue> {
    override fun compileVal(node: NodeValue, compiler: Compiler, ctx: CompilationContext): Variable =
        Variable.tmp(
            node,
            ctx.method.node.run {
                when (node.vtype) {
                    NodeValue.Type.NIL -> {
                        visitInsn(Opcodes.ACONST_NULL)
                        VirtualType.ofKlass("java.lang.Object")
                    }
                    NodeValue.Type.BOOLEAN -> {
                        visitLdcInsn(node.getBoolean())
                        VirtualType.BOOLEAN
                    }
                    NodeValue.Type.CHAR -> {
                        visitLdcInsn(node.getChar())
                        VirtualType.CHAR
                    }
                    NodeValue.Type.INT -> {
                        visitLdcInsn(node.getInt())
                        VirtualType.INT
                    }
                    NodeValue.Type.LONG -> {
                        visitLdcInsn(node.getLong())
                        VirtualType.LONG
                    }
                    NodeValue.Type.FLOAT -> {
                        visitLdcInsn(node.getFloat())
                        VirtualType.FLOAT
                    }
                    NodeValue.Type.DOUBLE -> {
                        visitLdcInsn(node.getDouble())
                        VirtualType.DOUBLE
                    }
                    NodeValue.Type.STRING -> {
                        visitLdcInsn(node.getString())
                        VirtualType.ofKlass("java.lang.String")
                    }
                    NodeValue.Type.PRIMITIVE, NodeValue.Type.CLASS -> {
                        visitLdcInsn(Type.getType(node.value.desc))
                        VirtualType.ofKlass("java.lang.Class")
                    }
                    NodeValue.Type.NAMING -> throw UnsupportedOperationException()
                }
            }
        )
}