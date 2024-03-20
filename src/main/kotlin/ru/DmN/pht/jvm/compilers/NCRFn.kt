package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Handle
import org.objectweb.asm.Opcodes.H_INVOKESTATIC
import org.objectweb.asm.Opcodes.H_INVOKEVIRTUAL
import org.objectweb.asm.Type
import ru.DmN.pht.ast.NodeRFn
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCRFn : INodeCompiler<NodeRFn> {
    override fun compileVal(node: NodeRFn, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.apply {
            node.method!!.run {
                val declName = declaringClass.jvmName
                if (modifiers.static) {
                    visitInvokeDynamicInsn(
                        node.lambda!!.name,
                        "()L${node.type!!.jvmName};",
                        Handle(
                            H_INVOKESTATIC,
                            "java/lang/invoke/LambdaMetafactory",
                            "metafactory",
                            "(Ljava/lang/invoke/MethodHandles\$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                            false
                        ),
                        Type.getType(node.lambda!!.desc),
                        Handle(
                            H_INVOKESTATIC,
                            declName,
                            name,
                            desc,
                            declaringClass.isInterface
                        ),
                        Type.getType(desc),
                    )
                } else {
                    load(compiler.compileVal(node.instance!!, ctx), this@apply)
                    visitInvokeDynamicInsn(
                        node.lambda!!.name,
                        "(L${declName};)L${node.type!!.jvmName};",
                        Handle(
                            H_INVOKESTATIC,
                            "java/lang/invoke/LambdaMetafactory",
                            "metafactory",
                            "(Ljava/lang/invoke/MethodHandles\$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                            false
                        ),
                        Type.getType(node.lambda!!.desc),
                        Handle(
                            H_INVOKEVIRTUAL,
                            declName,
                            name,
                            desc,
                            declaringClass.isInterface
                        ),
                        Type.getType(desc),
                    )
                }
            }
        }
        return Variable.tmp(node, node.type)
    }
}