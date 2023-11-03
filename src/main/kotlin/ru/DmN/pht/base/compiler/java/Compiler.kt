package ru.DmN.pht.base.compiler.java

import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompilingStage
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processor.JavaTypesProvider
import ru.DmN.pht.base.utils.DefaultEnumMap
import ru.DmN.pht.base.utils.TypesProvider
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.getRegex

class Compiler(val tp: TypesProvider = JavaTypesProvider()) {
    val tasks: DefaultEnumMap<CompilingStage, MutableList<() -> Unit>> = DefaultEnumMap(CompilingStage::class.java) { ArrayList() }
    val contexts: MutableMap<String, Any?> = HashMap()
    val classes: MutableMap<String, ClassNode> = HashMap()
    fun compile(node: Node, ctx: CompilationContext) =
        get(ctx, node).compile(node, this, ctx)
    fun compileVal(node: Node, ctx: CompilationContext): Variable =
        get(ctx, node).compileVal(node, this, ctx)

    fun get(ctx: CompilationContext, node: Node): INodeCompiler<Node> {
        val name = node.token.text!!
        val i = name.lastIndexOf('/')
        if (i < 1) {
            ctx.loadedModules.forEach { it -> it.javaCompilers.getRegex(name)?.let { return it as INodeCompiler<Node> } }
            throw RuntimeException("Compiler for \"$name\" not founded!")
        } else {
            val module = name.substring(0, i)
            return ctx.loadedModules.find { it.name == module }!!.javaCompilers.getRegex(name.substring(i + 1)) as INodeCompiler<Node>
        }
    }

    fun pushTask(ctx: CompilationContext, stage: CompilingStage, task: () -> Unit) {
        if (stage.ordinal <= ctx.stage.get().ordinal)
            task()
        else tasks[stage] += task
    }
}