package ru.DmN.siberia

import org.objectweb.asm.tree.ClassNode
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.utils.CompilingStage
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.DefaultEnumMap
import ru.DmN.siberia.utils.TypesProvider
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.getRegex
import ru.DmN.pht.std.utils.text

/**
 * Компилятор.
 */
class Compiler(
    /**
     * Провайдер типов.
     */
    val tp: TypesProvider
) {
    /**
     * Список задач.
     */
    val tasks: DefaultEnumMap<CompilingStage, MutableList<() -> Unit>> = DefaultEnumMap(CompilingStage::class.java) { ArrayList() }

    /**
     * Глобальные контексты.
     */
    val contexts: MutableMap<String, Any?> = HashMap()

    /**
     * Список классов.
     */
    val classes: MutableMap<String, ClassNode> = HashMap()

    /**
     * Компилирует ноду без значения.
     */
    fun compile(node: Node, ctx: CompilationContext) =
        get(ctx, node).compile(node, this, ctx)

    /**
     * Компилирует ноду со значением.
     */
    fun compileVal(node: Node, ctx: CompilationContext): Variable =
        get(ctx, node).compileVal(node, this, ctx)

    /**
     * Возвращает компилятор нод.
     */
    fun get(ctx: CompilationContext, node: Node): INodeCompiler<Node> {
        val name = node.text
        val i = name.lastIndexOf('/')
        if (i < 1) {
            ctx.loadedModules.forEach { it -> it.javaCompilers.getRegex(name)?.let { return it as INodeCompiler<Node> } }
            throw RuntimeException("Compiler for \"$name\" not founded!")
        } else {
            val module = name.substring(0, i)
            return ctx.loadedModules.find { it.name == module }!!.javaCompilers.getRegex(name.substring(i + 1)) as INodeCompiler<Node>
        }
    }

    /**
     * Помещает новую задачу в список.
     *
     * @param stage Стадия компиляции.
     */
    fun pushTask(ctx: CompilationContext, stage: CompilingStage, task: () -> Unit) {
        if (stage.ordinal <= ctx.stage.get().ordinal)
            task()
        else tasks[stage] += task
    }
}