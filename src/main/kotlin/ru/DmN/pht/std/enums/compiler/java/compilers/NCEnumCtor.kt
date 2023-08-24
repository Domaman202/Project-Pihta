package ru.DmN.pht.std.enums.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.TypeOrGeneric
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.compilers.NCDefn.getDescriptor
import ru.DmN.pht.std.base.compiler.java.ctx.BodyContext
import ru.DmN.pht.std.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.std.base.compiler.java.utils.*

object NCEnumCtor : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val gctx = ctx.global
        val cctx = ctx.enum
        val generics = cctx.clazz.generics
        //
        val parts = node.nodes.map { compiler.compute<Any?>(it, ctx, ComputeType.NODE) }
        val args = (parts[0] as List<Node>).map { compiler.compute<Any?>(it, ctx, ComputeType.NAME) }.map { it ->
            when (it) {
                is String -> Pair(it, "java.lang.Object")
                is List<*> -> it.map { (compiler.computeName(it as Node, ctx)) }
                    .let { Pair(it.first(), it.last()) }

                else -> throw RuntimeException()
            }
        }
        val argsTypes = args.map { cctx.getType(compiler, gctx, it.second) }
        val argsTOGs = argsTypes.mapIndexed { i, it -> TypeOrGeneric.of(generics, args[i].second, it) }
        //
        val varargs = node.attributes.getOrPut("varargs") { false } as Boolean
        //
        cctx.node.visitMethod(
            if (varargs) Opcodes.ACC_PRIVATE + Opcodes.ACC_VARARGS else Opcodes.ACC_PRIVATE,
            "<init>",
            "(Ljava/lang/String;I${getDescriptor(argsTypes, VirtualType.VOID).let { it.substring(1, it.lastIndexOf(')')) }})V",
            null,
            null
        ).run {
            val method = VirtualMethod(
                cctx.clazz,
                "<init>",
                TypeOrGeneric.of(generics, VirtualType.VOID),
                argsTOGs,
                args.map { it.first },
                varargs
            )
            cctx.clazz.methods += method
            val mctx = MethodContext(this as MethodNode, method)
            cctx.methods += mctx
            compiler.pushTask(ctx, CompileStage.METHODS_DEFINE) {
                val bctx = BodyContext.of(mctx)
                val context = ctx.with(mctx).with(bctx)
                val start = Label()
                visitLabel(start)
                mctx.createVariable(bctx, "this", cctx.clazz.name, start)
                mctx.createVariable(bctx, "pht\$internal$0", "java.lang.String", start)
                mctx.createVariable(bctx, "pht\$internal$1", "int", start)
                args.forEach {
                    val label = Label()
                    visitLabel(label)
                    mctx.variableStarts[bctx.addVariable(it.first, it.second).id] = label
                }
                visitVarInsn(Opcodes.ALOAD, 0)
                visitVarInsn(Opcodes.ALOAD, 1)
                visitVarInsn(Opcodes.ILOAD, 2)
                visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Enum", "<init>", "(Ljava/lang/String;I)V", false)
                compiler.compile(parts[1] as Node, context, false)
                visitInsn(Opcodes.RETURN)
                val stopLabel = Label()
                visitLabel(stopLabel)
                bctx.stopLabel = stopLabel
                bctx.visitAllVariables(compiler, ctx.global, cctx, mctx)
            }
        }
        return null
    }
}