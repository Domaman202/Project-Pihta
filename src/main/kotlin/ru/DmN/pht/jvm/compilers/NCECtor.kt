package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.compiler.java.compilers.NCDefn.visit
import ru.DmN.pht.std.compiler.java.utils.clazz
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.utils.CompilingStage
import ru.DmN.siberia.compilers.INodeCompiler

object NCECtor : INodeCompiler<NodeDefn> {
    override fun compile(node: NodeDefn, compiler: Compiler, ctx: CompilationContext) {
        val method = node.method
        val mnode = ctx.clazz.node.visitMethod(Opcodes.ACC_PUBLIC, method.name, method.desc, method.signature, null) as MethodNode
        compiler.stageManager.pushTask(CompilingStage.METHODS_BODY) {
            mnode.run {
                visitVarInsn(Opcodes.ALOAD, 0)
                visitVarInsn(Opcodes.ALOAD, 1)
                visitVarInsn(Opcodes.ILOAD, 2)
                visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Enum", "<init>", "(Ljava/lang/String;I)V", false)
                visit(node, method, compiler, ctx)
            }
        }
    }
}