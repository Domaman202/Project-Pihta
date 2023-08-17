package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.CompileStage
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.BodyContext
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.*
import ru.DmN.pht.std.utils.insertRet
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext.Type as CtxType

object NCFunction : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.clazz) { // todo: lambda
            val gctx = ctx.global
            val cctx = ctx.clazz!!
            val clazz = cctx.clazz
            val generics = clazz.generics
            //
            val parts = node.nodes.map { { name: Boolean -> compiler.compute<Any?>(it, ctx, name) } }
            val name = parts[0](true) as String
            val returnClass = parts[1](true) as String
            val returnType = cctx.getType(compiler, gctx, returnClass)
            val args = (parts[2](false) as List<Node>).map { compiler.compute<Any?>(it, ctx, true) }.map { it ->
                when (it) {
                    is String -> Pair(it, "java.lang.Object")
                    is List<*> -> it.map { (compiler.computeStringConst(it as Node, ctx)) }.let { Pair(it.first(), it.last()) }
                    else -> throw RuntimeException()
                }
            }
            val argsTypes = args.map { cctx.getType(compiler, gctx, it.second) }
            val argsTOGs = argsTypes.mapIndexed { i, it -> TypeOrGeneric.of(generics, args[i].second, it) }
            val body = parts[3](false) as Node
            //
            val abstract = node.attributes.getOrPut("abstract") { false } as Boolean
            val final = node.attributes.getOrPut("final") { false } as Boolean
            val override = node.attributes.getOrPut("override") { false } as Boolean
            val static = node.attributes.getOrPut("static") { false } as Boolean
            val varargs = node.attributes.getOrPut("varargs") { false } as Boolean
            //
            var access = Opcodes.ACC_PUBLIC
            if (abstract)
                access += Opcodes.ACC_ABSTRACT
            else if (final)
                access += Opcodes.ACC_FINAL
            else if (static)
                access += Opcodes.ACC_STATIC
            if (varargs)
                access += Opcodes.ACC_VARARGS
            //
            val mnode = cctx.node.visitMethod(
                access,
                name,
                getDescriptor(argsTypes, returnType),
                getSignature(args.map { it.second }, returnClass, varargs) { ctx.clazz.getType(compiler, gctx, it) },
                null
            ) as MethodNode
            val method = VirtualMethod(
                clazz,
                name,
                TypeOrGeneric.of(generics, returnType),
                argsTOGs,
                args.map { it.first },
                varargs,
                static,
                abstract,
                null,
                null,
                generics
            )
            clazz.methods += method
            val context = MethodContext(mnode, method)
            cctx.methods += context
            if (!abstract) {
                compiler.tasks[CompileStage.METHODS_DEFINE].add {
                    if (override) {
                        method.override = clazz.getAllMethods().find { it ->
                            it.declaringClass != clazz && it.overridableBy(method) {
                                gctx.getType(
                                    compiler,
                                    it
                                )
                            }
                        }!!.apply {
                            if (desc != mnode.desc) {
                                cctx.node.visitMethod(
                                    Opcodes.ACC_PUBLIC + Opcodes.ACC_SYNTHETIC + Opcodes.ACC_BRIDGE,
                                    name,
                                    desc,
                                    null,
                                    null
                                ).apply {
                                    val labelStart = Label()
                                    visitLabel(labelStart)
                                    visitVarInsn(Opcodes.ALOAD, 0)
                                    argsc.forEachIndexed { i, it ->
                                        if (it.type.isPrimitive()) {
                                            visitVarInsn(
                                                when (it.type) {
                                                    "void" -> throw RuntimeException()
                                                    "boolean", "byte", "short", "char", "int" -> Opcodes.ILOAD
                                                    "long" -> Opcodes.LLOAD
                                                    "float" -> Opcodes.FLOAD
                                                    "double" -> Opcodes.DLOAD
                                                    else -> throw Error("Unreachable code")
                                                },
                                                i + 1
                                            )
                                        } else {
                                            visitVarInsn(Opcodes.ALOAD, i + 1)
                                            visitTypeInsn(Opcodes.CHECKCAST, method.argsc[i].type.replace('.', '/'))
                                        }
                                    }
                                    visitMethodInsn(Opcodes.INVOKEVIRTUAL, clazz.className, name, method.desc, false)
                                    visitInsn(
                                        when (rettype.type) {
                                            "void" -> Opcodes.RETURN
                                            "boolean", "byte", "short", "char", "int" -> Opcodes.IRETURN
                                            "long" -> Opcodes.LRETURN
                                            "float" -> Opcodes.FRETURN
                                            "double" -> Opcodes.DRETURN
                                            else -> Opcodes.ARETURN
                                        }
                                    )
                                    val labelStop = Label()
                                    visitLabel(labelStop)
                                    visitLocalVariable("this", clazz.desc, cctx.node.signature, labelStart, labelStop, 0)
                                    visitEnd()
                                }
                            }
                        }
                    }
                    compiler.tasks[CompileStage.METHODS_BODY].add {
                        mnode.visitCode()
                        val bctx = BodyContext.of(context)
                        if (!static) {
                            val label = Label()
                            mnode.visitLabel(label)
                            context.variableStarts[bctx.addVariable("this", clazz.name).id] = label
                        }
                        args.forEach {
                            val label = Label()
                            mnode.visitLabel(label)
                            context.variableStarts[bctx.addVariable(it.first, it.second).id] = label
                        }
                        insertRet(compiler.compile(body, ctx.with(CtxType.CLASS.with(CtxType.METHOD).with(CtxType.BODY)).with(context).with(bctx), returnType.name != "void"), returnType, mnode)
                        val stopLabel = Label()
                        mnode.visitLabel(stopLabel)
                        bctx.stopLabel = stopLabel
                        bctx.visitAllVariables(compiler, gctx, cctx, context)
                        mnode.visitEnd()
                    }
                }
            }
        }
        return null
    }

    override fun applyAnnotation(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        when (annotation.tkOperation.text) {
            "@abstract" -> node.attributes["abstract"] = true
            "@final" -> node.attributes["final"] = true
            "@override" -> node.attributes["override"] = true
            "@static" -> node.attributes["static"] = true
            "@varargs" -> node.attributes["varargs"] = true
        }
    }

    fun getDescriptor(args: List<VirtualType>, returnType: VirtualType) =
        StringBuilder().apply {
            append('(')
            args.forEach { append(it.desc) }
            append(')').append(returnType.desc)
        }.toString()

    fun getSignature(args: List<String>, returnType: String, varargs: Boolean, getType: (name: String) -> VirtualType) =
        StringBuilder().apply {
            append('(')
            if (args.isNotEmpty()) {
                args.dropLast(1).forEach { append(getSignature(it, getType)) }
                args.forEach { append(getSignature(if (varargs) "[$it" else it, getType)) }
            }
            append(')').append(getSignature(returnType, getType))
        }.toString()

    fun getSignature(type: String, getType: (name: String) -> VirtualType): String =
        type.let {
            if (it.endsWith('^'))
                "T${it.substring(0, type.length - 1)};"
            else getType(it).desc
        }
}