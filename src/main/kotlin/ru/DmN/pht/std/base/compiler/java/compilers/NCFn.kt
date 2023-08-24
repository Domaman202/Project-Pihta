package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.*
import ru.DmN.pht.std.base.compiler.java.ctx.*
import ru.DmN.pht.std.base.compiler.java.utils.*
import ru.DmN.pht.std.base.utils.insertRet

object NCFn : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val gctx = ctx.global
        val cctx = ctx.clazz
        val clazz = cctx.clazz
        val generics = clazz.generics
        //
        val parts = node.nodes.map { { type: ComputeType -> compiler.compute<Any?>(it, ctx, type) } }
        val name = parts[0](ComputeType.NAME) as String
        val returnClass = parts[1](ComputeType.NAME) as String
        val returnType = cctx.getType(compiler, gctx, returnClass)
        val args = parseArgs(parts[2](ComputeType.NODE) as List<Node>, compiler, ctx, cctx, gctx, generics)
        val body = parts[3](ComputeType.NODE) as Node
        //
        val abstract = node.attributes.getOrPut("abstract") { false } as Boolean
        val bridge = node.attributes.getOrPut("bridge") { false } as Boolean
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
            getDescriptor(args.second, returnType),
            getSignature(args.first.map { it.second }, returnClass, varargs) { ctx.clazz.getType(compiler, gctx, it) },
            null
        ) as MethodNode
        val method = VirtualMethod(
            clazz,
            name,
            TypeOrGeneric.of(generics, returnType),
            args.third,
            args.first.map { it.first },
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
            compiler.pushTask(ctx, CompileStage.METHODS_DEFINE) {
                if (override) {
                    method.override = clazz.getAllMethods().find { it ->
                        it.declaringClass != clazz && it.name == name && it.overridableBy(method) { gctx.getType(compiler, it) }
                    }!!.apply {
                        if (bridge && desc != mnode.desc) {
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
                compiler.pushTask(ctx, CompileStage.METHODS_BODY) {
                    mnode.visitCode()
                    val bctx = BodyContext.of(context)
                    if (!static) {
                        val label = Label()
                        mnode.visitLabel(label)
                        context.variableStarts[bctx.addVariable("this", clazz.name).id] = label
                    }
                    args.first.forEach {
                        val label = Label()
                        mnode.visitLabel(label)
                        context.variableStarts[bctx.addVariable(it.first, it.second).id] = label
                    }
                    insertRet(compiler.compile(body, ctx.with(context).with(bctx), returnType.name != "void"), returnType, mnode)
                    val stopLabel = Label()
                    mnode.visitLabel(stopLabel)
                    bctx.stopLabel = stopLabel
                    bctx.visitAllVariables(compiler, gctx, cctx, context)
                    mnode.visitEnd()
                }
            }
        }
        return null
    }

    override fun applyAnnotation(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        val text = annotation.tkOperation.text!!
        node.attributes[text.substring(text.lastIndexOf('@') + 1)] = true
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

    fun parseArgs(parts: List<Node>, compiler: Compiler, ctx: CompilationContext): List<Pair<String, String>> =
        parts.map { compiler.compute<Any?>(it, ctx, ComputeType.NAME) }.map { it ->
            when (it) {
                is String -> Pair(it, "java.lang.Object")
                is List<*> -> it.map { (compiler.computeName(it as Node, ctx)) }
                    .let { Pair(it.first(), it.last()) }

                else -> throw RuntimeException()
            }
        }

    fun parseArgs(args: List<Pair<String, String>>, compiler: Compiler, cctx: ClassContext, gctx: GlobalContext, generics: Generics): Triple<List<Pair<String, String>>, List<VirtualType>, List<TypeOrGeneric>> {
        val argsTypes = args.map { cctx.getType(compiler, gctx, it.second) }
        return Triple(
            args,
            argsTypes,
            argsTypes.mapIndexed { i, it -> TypeOrGeneric.of(generics, args[i].second, it) }
        )
    }

    fun parseArgs(parts: List<Node>, compiler: Compiler, ctx: CompilationContext, cctx: ClassContext, gctx: GlobalContext, generics: Generics): Triple<List<Pair<String, String>>, List<VirtualType>, List<TypeOrGeneric>> =
        parseArgs(parseArgs(parts, compiler, ctx), compiler, cctx, gctx, generics)
}