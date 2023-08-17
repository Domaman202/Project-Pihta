package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.CompileStage
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.ClassContext
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.GlobalContext
import ru.DmN.pht.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.*

object NCClass : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type == CompilationContext.Type.GLOBAL) { // todo: subclass
            compiler.tasks[CompileStage.TYPES_PREDEFINE].add {
                val parts = node.nodes.map { { name: Boolean -> compiler.compute<Any?>(it, ctx, name) } }
                val name = ctx.global.name(parts[0](true) as String)
                val parents = (parts[1](false) as List<Node>)
                    .map { compiler.computeStringConst(it, ctx) }
                    .map { ctx.global.getType(compiler, it) }.toMutableList()
                //
                val isInterface = node.tkOperation.text == "intf"
                val isClass = node.tkOperation.text == "cls"
                val isObject = node.tkOperation.text == "obj"
                //
                val generics = Generics()
                (node.attributes.getOrDefault("generics", emptyList<(Pair<String, String>)>()) as List<Pair<String, String>>).forEach {
                    generics.list += Generic(it.first, it.second)
                }
                //
                val cnode = ClassNode()
                val type = VirtualType(name, isInterface = isInterface, generics = generics)
                val context = ClassContext(cnode, type)
                //
                compiler.classes += context
                compiler.tasks[CompileStage.TYPES_DEFINE].add {
                    type.parents = parents
                    cnode.visit(
                        Opcodes.V19,
                        if (isInterface)
                            Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE
                        else if (isClass)
                            Opcodes.ACC_PUBLIC
                        else if (isObject)
                            Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL
                        else throw Error(),
                        type.className,
                        getSignature(compiler, ctx.global, parents, generics),
                        type.parents.firstOrNull()?.className ?: "java/lang/Object",
                        type.parents.drop(1).map { it.className }.toTypedArray()
                    )
                    if (isObject) {
                        type.fields += VirtualField("INSTANCE", type, static = true, enum = false)
                        cnode.visitField(
                            Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL,
                            "INSTANCE",
                            type.desc,
                            null,
                            null
                        )
                        cnode.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "<clinit>", "()V", null, null).run {
                            visitCode()
                            visitTypeInsn(Opcodes.NEW, cnode.name)
                            visitInsn(Opcodes.DUP)
                            visitMethodInsn(Opcodes.INVOKESPECIAL, cnode.name, "<init>", "()V", false)
                            visitFieldInsn(Opcodes.PUTSTATIC, cnode.name, "INSTANCE", type.desc)
                            visitInsn(Opcodes.RETURN)
                            visitEnd()
                        }
                    }
                    compiler.tasks[CompileStage.METHODS_PREDEFINE].add {
                        val nctx = ctx.with(CompilationContext.Type.CLASS).with(context)
                        if(parts.size > 2)
                            compiler.compile(parts[2](false) as Node, nctx, false)
                        if (isObject) {
                            if (type.methods.find { it.name == "<init>" } == null) {
                                val method = VirtualMethod(
                                    type,
                                    "<init>",
                                    TypeOrGeneric.of(generics, VirtualType.VOID),
                                    emptyList(),
                                    emptyList(),
                                    varargs = false,
                                    static = false,
                                    abstract = isInterface,
                                    generics = generics
                                )
                                type.methods += method
                                val mnode = cnode.visitMethod(Opcodes.ACC_PRIVATE, "<init>", "()V", null, null) as MethodNode
                                val mcontext = MethodContext(mnode, method)
                                context.methods += mcontext
                                mnode.run {
                                    visitCode()
                                    val labelStart = Label()
                                    visitLabel(labelStart)
                                    visitVarInsn(Opcodes.ALOAD, 0)
                                    visitMethodInsn(Opcodes.INVOKESPECIAL, cnode.superName, "<init>", "()V", false)
                                    visitInsn(Opcodes.RETURN)
                                    val labelStop = Label()
                                    visitLabel(labelStop)
                                    visitLocalVariable("this", type.desc, null, labelStart, labelStop, 0)
                                    visitEnd()
                                }
                            }
                        }
                    }
                }
            }
        }
        return null
    }

    fun getSignature(compiler: Compiler, gctx: GlobalContext, parents: List<VirtualType>, generics: Generics): String? {
        val gensig = generics.getSignature(compiler, gctx)
        return if (gensig.isNotEmpty()) {
            val sb = StringBuilder()
            sb.append('<').append(gensig).append('>')
            parents.forEach { sb.append(it.signature) }
            sb.toString()
        } else null
    }
}