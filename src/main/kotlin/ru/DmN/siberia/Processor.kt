package ru.DmN.siberia

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.DefaultEnumMap
import ru.DmN.siberia.utils.TypesProvider
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.getRegex
import ru.DmN.pht.std.utils.text

class Processor(val tp: TypesProvider) {
    val tasks: DefaultEnumMap<ProcessingStage, MutableList<() -> Unit>> = DefaultEnumMap(ProcessingStage::class.java) { java.util.ArrayList() }
    val contexts: MutableMap<String, Any?> = HashMap()

    fun calc(node: Node, ctx: ProcessingContext): VirtualType? =
        get(node, ctx).calc(node, this, ctx)
    fun process(node: Node, ctx: ProcessingContext, mode: ValType): Node? =
        get(node, ctx).process(node, this, ctx, mode)

    fun get(node: Node, ctx: ProcessingContext): INodeProcessor<Node> {
        val name = node.text
        val i = name.lastIndexOf('/')
        if (i < 1) {
            ctx.loadedModules.forEach { it -> it.processors.getRegex(name)?.let { return it as INodeProcessor<Node> } }
            throw RuntimeException("Processor for \"$name\" not founded!")
        } else {
            val module = name.substring(0, i)
            return ctx.loadedModules.find { it.name == module }!!.processors.getRegex(name.substring(i + 1)) as INodeProcessor<Node>
        }
    }

    fun pushTask(ctx: ProcessingContext, stage: ProcessingStage, task: () -> Unit) {
        if (stage.ordinal <= ctx.stage.get().ordinal)
            task()
        else tasks[stage] += task
    }
}