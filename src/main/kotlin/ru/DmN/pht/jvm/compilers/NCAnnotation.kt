package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import ru.DmN.pht.jvm.ast.NodeAnnotation
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

// TODO: реализовать поддержку аргументов
object NCAnnotation : INodeCompiler<NodeAnnotation> {
    override fun compile(node: NodeAnnotation, compiler: Compiler, ctx: CompilationContext) {
        node.nodes.forEach {
            val nc = compiler.get(ctx, it)
            if (nc is IStdNodeCompiler<*, *>)
                process(node, (nc as IStdNodeCompiler<Node, Any?>).compileAsm(it, compiler, ctx), compiler, ctx)
            else nc.compile(node, compiler, ctx)
        }
    }

    override fun compileVal(node: NodeAnnotation, compiler: Compiler, ctx: CompilationContext): Variable {
        node.nodes.stream().skip(1).forEach {
            val nc = compiler.get(ctx, it)
            if (nc is IStdNodeCompiler<*, *>)
                process(node, (nc as IStdNodeCompiler<Node, Any?>).compileAsm(it, compiler, ctx), compiler, ctx)
            else nc.compile(node, compiler, ctx)
        }
        return node.nodes.last().run {
            val nc = compiler.get(ctx, this)
            if (nc is IStdNodeCompiler<*, *>) {
                val pair = (nc as IStdNodeCompiler<Node, Any?>).compileValAsm(this, compiler, ctx)
                process(node, pair.second, compiler, ctx)
                pair.first
            } else nc.compileVal(this, compiler, ctx)
        }
    }

    private fun process(annotation: NodeAnnotation, asm: Any?, compiler: Compiler, ctx: CompilationContext) {
        when (asm) {
            is FieldVisitor -> {
                asm.visitAnnotation(annotation.type.desc, true)
            }

            is MethodVisitor -> {
                asm.visitAnnotation(annotation.type.desc, true)
            }

            is ClassVisitor -> {
                asm.visitAnnotation(annotation.type.desc, true)
            }

            is List<*> -> {
                asm.forEach {
                    process(annotation, it, compiler, ctx)
                }
            }
        }
    }
}