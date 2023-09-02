package ru.DmN.pht.std.decl.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.TypeOrGeneric
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.*
import ru.DmN.pht.std.decl.compiler.java.utils.clazz
import ru.DmN.pht.std.decl.compiler.java.utils.getType

object NCFn : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val gctx = ctx.global
        val clazz = ctx.clazz
        val generics = clazz.generics
        //
        val parts = node.nodes.map { { type: ComputeType -> compiler.compute<Any?>(it, ctx, type) } }
        val name = parts[0](ComputeType.NAME) as String
        val returnClass = parts[1](ComputeType.NAME) as String
        val returnType = clazz.getType(compiler, gctx, returnClass)
        val args = (parts[2](ComputeType.NODE) as List<Node>).map { compiler.compute<Any?>(it, ctx, ComputeType.NAME) }.map { it ->
            when (it) {
                is String -> Pair(it, "java.lang.Object")
                is List<*> -> it.map { (compiler.computeName(it as Node, ctx)) }
                    .let { Pair(it.first(), it.last()) }

                else -> throw RuntimeException()
            }
        }
        val argsTypes = args.map { clazz.getType(compiler, gctx, it.second) }
        val argsTOGs = argsTypes.mapIndexed { i, it -> TypeOrGeneric.of(generics, args[i].second, it) }
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
        //
        return null
    }

    override fun applyAnnotation(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        val text = annotation.tkOperation.text!!
        node.attributes[text.substring(text.lastIndexOf('@') + 1)] = true
    }
}