package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Generics
import ru.DmN.pht.base.utils.TypeOrGeneric
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.std.base.compiler.java.compilers.NCDefn.getDescriptor
import ru.DmN.pht.std.base.compiler.java.ctx.*
import ru.DmN.pht.std.base.compiler.java.utils.*
import ru.DmN.pht.std.base.utils.insertRet

object NCExFn : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val gctx = ctx.global
        val cctx = ctx.clazz
        //
        val parts = node.nodes.map { compiler.compute<Any?>(it, ctx, ComputeType.NAME) }
        val clazz = gctx.getType(compiler, parts[0] as String)
        val name = parts[1] as String
        val returnClass = parts[2] as String
        val returnType = cctx.getType(compiler, gctx, returnClass)
        val args = (parts[3] as List<Node>).map { compiler.compute<Any?>(it, ctx, ComputeType.NAME) }.map { it ->
            if (it is String)
                Pair(it, "java.lang.Object")
            else (it as List<Node>)
                .map { (compiler.computeName(it, ctx)) }
                .let { Pair(it.first(), it.last()) }
        }
        val argsTypes = args.map { cctx.getType(compiler, gctx, it.second) }
        val argsTOGs = argsTypes.map { TypeOrGeneric.of(Generics.EMPTY, it) }
        val body = parts[4] as Node
        //
        val varargs = node.attributes.getOrPut("varargs") { false } as Boolean
        //
        val mnode = cctx.node.visitMethod(
            Opcodes.ACC_STATIC + Opcodes.ACC_PUBLIC + if (varargs) Opcodes.ACC_VARARGS else 0,
            name,
            getDescriptor(listOf(clazz) + argsTypes, returnType),
            null,
            null
        ) as MethodNode
        val method = VirtualMethod(
            cctx.clazz,
            name,
            TypeOrGeneric.of(Generics.EMPTY, returnType),
            argsTOGs,
            args.map { it.first },
            varargs = varargs,
            static = true,
            abstract = false,
            extend = clazz,
            override = null
        )
        cctx.clazz.methods += method
        val context = MethodContext(mnode, method)
        cctx.methods += context
        compiler.pushTask(ctx, CompileStage.METHODS_DEFINE) {
            val bctx = BodyContext.of(context)
            val label0 = Label()
            mnode.visitLabel(label0)
            context.variableStarts[bctx.addVariable("this", clazz.name).id] = label0
            args.forEach {
                val label1 = Label()
                mnode.visitLabel(label1)
                context.variableStarts[bctx.addVariable(it.first, it.second).id] = label1
            }
            insertRet(
                compiler.compile(body, ctx.with(context).with(bctx), returnType.name != "void"),
                returnType,
                mnode
            )
            val stopLabel = Label()
            mnode.visitLabel(stopLabel)
            bctx.stopLabel = stopLabel
            bctx.visitAllVariables(compiler, gctx, cctx, context)
        }
        return null
    }
}