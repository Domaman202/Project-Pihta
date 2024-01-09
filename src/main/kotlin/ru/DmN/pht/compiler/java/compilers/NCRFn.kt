package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Handle
import org.objectweb.asm.Opcodes.H_INVOKESTATIC
import org.objectweb.asm.Opcodes.H_INVOKEVIRTUAL
import org.objectweb.asm.Type
import ru.DmN.pht.ast.NodeRFn
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCRFn : INodeCompiler<NodeRFn> {
    override fun compileVal(node: NodeRFn, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.apply {
            node.method.run {
                val declName = declaringClass!!.className
                val desc = desc
                val static = modifiers.static
                if (!static)
                    load(compiler.compileVal(node.instance, ctx), this@apply)
                visitInvokeDynamicInsn(
                    node.lambda.name,
                    if (static) "()L${node.type.className};" else "(L${declName};)L${node.type.className};",
                    Handle(
                        H_INVOKESTATIC,
                        "java/lang/invoke/LambdaMetafactory",
                        "metafactory",
                        "(Ljava/lang/invoke/MethodHandles\$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        false
                    ),
                    Type.getType(desc),
                    Handle(
                        if (static) H_INVOKESTATIC else H_INVOKEVIRTUAL,
                        declName,
                        name,
                        desc,
                        declaringClass!!.isInterface
                    ),
                    Type.getType(desc)
                )
            }
        }
        return Variable.tmp(node, node.type)
    }
}