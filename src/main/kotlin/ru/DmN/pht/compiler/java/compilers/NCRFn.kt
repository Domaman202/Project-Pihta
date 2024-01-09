package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Handle
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import ru.DmN.pht.ast.NodeRFn
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCRFn : INodeCompiler<NodeRFn> {
    override fun compileVal(node: NodeRFn, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            node.method.run {
                if (modifiers.static) {
                    visitInvokeDynamicInsn(
                        node.lambda.name,
                        "()L${node.type.className};",
                        Handle(
                            Opcodes.H_INVOKESTATIC,
                            "java/lang/invoke/LambdaMetafactory",
                            "metafactory",
                            "(Ljava/lang/invoke/MethodHandles\$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                            false
                        ),
                        Type.getType(desc),
                        Handle(Opcodes.H_INVOKESTATIC, declaringClass!!.className, name, desc, declaringClass!!.isInterface),
                        Type.getType(desc)
                    )
                } else {
                    TODO()
                }
            }
        }
        return Variable.tmp(node, node.type)
    }
}