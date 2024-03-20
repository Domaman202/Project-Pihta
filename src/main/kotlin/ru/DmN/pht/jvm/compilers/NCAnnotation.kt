package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import ru.DmN.pht.compiler.java.utils.computeValue
import ru.DmN.pht.jvm.ast.NodeAnnotation
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

// TODO: реализовать поддержку аргументов
object NCAnnotation : INodeCompiler<NodeAnnotation> {
    override fun compile(node: NodeAnnotation, compiler: Compiler, ctx: CompilationContext) {
        node.nodes.forEach {
            val nc = compiler.get(ctx, it)
            if (nc is IStdNodeCompiler<*, *, *>)
                process(node, (nc as IStdNodeCompiler<Node, *, *>).compileAsm(it, compiler, ctx), compiler, ctx)
            else nc.compile(node, compiler, ctx)
        }
    }

    override fun compileVal(node: NodeAnnotation, compiler: Compiler, ctx: CompilationContext): Variable {
        node.nodes.stream().skip(1).forEach {
            val nc = compiler.get(ctx, it)
            if (nc is IStdNodeCompiler<*, *, *>)
                process(node, (nc as IStdNodeCompiler<Node, *, *>).compileAsm(it, compiler, ctx), compiler, ctx)
            else nc.compile(node, compiler, ctx)
        }
        return node.nodes.last().run {
            val nc = compiler.get(ctx, this)
            if (nc is IStdNodeCompiler<*, *, *>) {
                val pair = (nc as IStdNodeCompiler<Node, *, *>).compileValAsm(this, compiler, ctx)
                process(node, pair.second, compiler, ctx)
                pair.first
            } else nc.compileVal(this, compiler, ctx)
        }
    }

    // todo: optimize for list
    private fun process(node: NodeAnnotation, asm: Any?, compiler: Compiler, ctx: CompilationContext) {
        val visitor = when (asm) {
            is FieldVisitor     -> asm.visitAnnotation(node.type!!.desc, true)
            is MethodVisitor    -> asm.visitAnnotation(node.type!!.desc, true)
            is ClassVisitor     -> asm.visitAnnotation(node.type!!.desc, true)

            is List<*> -> {
                asm.forEach { process(node, it, compiler, ctx) }
                return
            }

            else -> return
        }
        //
        val names = node.type!!.methods
        node.arguments.entries
            .asSequence()
            .mapIndexed { i, it -> Pair(it.key ?: names[i].name, compiler.computeValue(it.value, ctx)) }
            .forEachIndexed { i, it ->
                val name = it.first
                val value = it.second
                if (value is Array<*>) {
                    val arr = visitor.visitArray(name)
                    value.forEach { arr.visit(null, it) }
                    arr.visitEnd()
                } else if (names[i].rettype.isArray) {
                    val arr = visitor.visitArray(name)
                    arr.visit(null, it.second)
                    arr.visitEnd()
                } else visitor.visit(name, value)
            }
        //
        visitor.visitEnd()
    }
}