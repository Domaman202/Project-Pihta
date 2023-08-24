package ru.DmN.pht.std.enums.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.*
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.compilers.NCMethodCallA
import ru.DmN.pht.std.base.compiler.java.ctx.EnumContext
import ru.DmN.pht.std.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.std.base.compiler.java.utils.*

object NCEnum : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        compiler.pushTask(ctx, CompileStage.TYPES_PREDEFINE) {
            val gctx = ctx.global
            val parts = node.nodes.map { { type: ComputeType -> compiler.compute<Any?>(it, ctx, type) } }
            val name = gctx.name(parts[0](ComputeType.NAME) as String)
            val parents = (parts[1](ComputeType.NODE) as List<Node>)
                .map { compiler.computeName(it, ctx) }
                .map { gctx.getType(compiler, it) }.toMutableList()
            //
            val generics = Generics()
            (node.attributes.getOrDefault("generics", emptyList<(Pair<String, String>)>()) as List<Pair<String, String>>).forEach {
                generics.list += Generic(it.first, it.second)
            }
            //
            val cnode = ClassNode()
            val type = VirtualType(name)
            val cctx = EnumContext(cnode, type)
            compiler.classes += Pair(type, cnode)
            compiler.pushTask(ctx, CompileStage.TYPES_DEFINE) {
                type.parents = mutableListOf(VirtualType.ofKlass(Enum::class.java))
                type.parents += parents
                cnode.visit(
                    Opcodes.V19,
                    Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_ENUM,
                    type.className,
                    null,
                    "java/lang/Enum",
                    type.parents.drop(1).map { it.className }.toTypedArray()
                )
                //
                val typeDesc = type.desc
                val arrayType = type.arrayType
                val arrayTypeDesc = arrayType.desc
                //
                cnode.visitField(
                    Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL + Opcodes.ACC_SYNTHETIC,
                    "\$VALUES",
                    arrayTypeDesc,
                    null,
                    null
                )
                //
                val valuesMethodNode = cnode.visitMethod(
                    Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
                    "values",
                    "()$arrayTypeDesc",
                    null,
                    null
                ).apply {
                    visitFieldInsn(Opcodes.GETSTATIC, cnode.name, "\$VALUES", arrayTypeDesc)
                    visitMethodInsn(Opcodes.INVOKEVIRTUAL, arrayTypeDesc, "clone", "()Ljava/lang/Object;", false)
                    visitTypeInsn(Opcodes.CHECKCAST, arrayTypeDesc)
                    visitInsn(Opcodes.ARETURN)
                } as MethodNode
                val valuesMethod = VirtualMethod(
                    type,
                    "values",
                    TypeOrGeneric.of(Generics.EMPTY, arrayType),
                    emptyList(),
                    emptyList(),
                    varargs = false,
                    static = true
                )
                type.methods += valuesMethod
                cctx.methods += MethodContext(valuesMethodNode, valuesMethod)
                //
                val valueOfMethodNode = cnode.visitMethod(
                    Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
                    "valueOf",
                    "(Ljava/lang/String;)$typeDesc",
                    null,
                    null
                ).apply {
                    val start = Label()
                    visitLabel(start)
                    visitLdcInsn(Type.getType(typeDesc))
                    visitVarInsn(Opcodes.ALOAD, 0)
                    visitMethodInsn(
                        Opcodes.INVOKESTATIC,
                        "java/lang/Enum",
                        "valueOf",
                        "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;",
                        false
                    )
                    visitTypeInsn(Opcodes.CHECKCAST, cnode.name)
                    visitInsn(Opcodes.ARETURN)
                    val stop = Label()
                    visitLabel(stop)
                    visitLocalVariable("name", "Ljava/lang/String;", null, start, stop, 0)
                } as MethodNode
                val valueOfMethod = VirtualMethod(
                    type,
                    "valueOf",
                    TypeOrGeneric.of(Generics.EMPTY, type),
                    listOf(TypeOrGeneric.of(Generics.EMPTY, String::class.java)),
                    listOf("name"),
                    false
                )
                type.methods += valuesMethod
                cctx.methods += MethodContext(valueOfMethodNode, valueOfMethod)

                //
                compiler.pushTask(ctx, CompileStage.METHODS_PREDEFINE) {
                    val nctx = ctx.with(cctx)
                    if (parts.size > 2)
                        compiler.compile(parts[2](ComputeType.NODE) as Node, nctx, false)
                    //
                    cnode.visitMethod(
                        Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC + Opcodes.ACC_SYNTHETIC,
                        "\$values",
                        "()${arrayTypeDesc}",
                        null,
                        null
                    ).run {
                        visitLdcInsn(cctx.enums.size)
                        visitTypeInsn(Opcodes.ANEWARRAY, cnode.name)
                        visitInsn(Opcodes.DUP)
                        cctx.enums.forEachIndexed { i, it ->
                            visitLdcInsn(i)
                            visitFieldInsn(Opcodes.GETSTATIC, cnode.name, it.name, typeDesc)
                            visitInsn(Opcodes.AASTORE)
                            visitInsn(Opcodes.DUP)
                        }
                        visitInsn(Opcodes.ARETURN)
                    }
                    cnode.visitMethod(
                        Opcodes.ACC_STATIC,
                        "<clinit>",
                        "()V",
                        null,
                        null
                    ).run {
                        val method = VirtualMethod(
                            type,
                            "<clinit>",
                            TypeOrGeneric.of(Generics.EMPTY, VirtualType.VOID),
                            emptyList(),
                            emptyList(),
                            static = true
                        )
                        type.methods += method
                        val mctx = MethodContext(this as MethodNode, method)
                        cctx.methods += mctx
                        val context = ctx.with(mctx)
                        cctx.enums.forEachIndexed { i, it ->
                            visitTypeInsn(Opcodes.NEW, cnode.name)
                            visitInsn(Opcodes.DUP)
                            visitLdcInsn(it.name)
                            visitLdcInsn(i)
                            NCMethodCallA.compileWithOutRet(
                                compiler,
                                context,
                                type,
                                "<init>",
                                it.args,
                                {},
                                enumCtor = true,
                                special = true
                            )
                            visitFieldInsn(Opcodes.PUTSTATIC, cnode.name, it.name, typeDesc)
                        }
                        visitMethodInsn(Opcodes.INVOKESTATIC, cnode.name, "\$values", "()$arrayTypeDesc", false)
                        visitFieldInsn(Opcodes.PUTSTATIC, cnode.name, "\$VALUES", arrayTypeDesc)
                        visitInsn(Opcodes.RETURN)
                    }
                }
            }
        }
        return null
    }
}