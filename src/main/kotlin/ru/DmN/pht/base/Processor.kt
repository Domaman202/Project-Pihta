package ru.DmN.pht.base

import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ProcessingStage
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.utils.DefaultEnumMap
import ru.DmN.pht.base.utils.TypesProvider
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.getRegex
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