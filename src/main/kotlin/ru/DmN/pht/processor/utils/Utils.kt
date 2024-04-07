@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
package ru.DmN.pht.processor.utils

import ru.DmN.pht.module.utils.Module
import ru.DmN.pht.utils.compute
import ru.DmN.pht.utils.lenArgs
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualMethod

/**
 * Возвращает обработчик нод из прошлых (вышестоящих) модулей.
 */
fun Processor.getOther(node: Node, ctx: ProcessingContext, module: Module): INodeProcessor<Node> {
    val iter = ctx.loadedModules.iterator()
    while (iter.hasNext())
        if (iter.next() == module)
            break
    val type = node.info.type
    if (iter.hasNext())
        iter.forEach { it -> it.processors[type]?.let { return it as INodeProcessor<Node> } }
    throw RuntimeException("Processor for \"$type\" not founded!")
}

fun getMethodVariants(variants: Sequence<VirtualMethod>, args: List<ICastable>): Sequence<Pair<VirtualMethod, Boolean>> =
    variants
        .map { Pair(it, if (it.modifiers.extension) listOf(ICastable.of(it.extension!!)) + args else args) }
        .filter { it.first.argsc.size == it.second.size || it.first.modifiers.varargs }
        .map { Pair(it.first, lenArgs(it.first.argsc, it.second, it.first.modifiers.varargs)) }
        .filter { it.second.first > -1 }
        .sortedBy { it.second.first }
        .map { Pair(it.first, it.second.second) }

fun Sequence<Node>.processValues(processor: Processor, ctx: ProcessingContext): Sequence<Node> =
    this.map { processor.process(it, ctx, true)!! }
fun Sequence<Node>.computeValues(processor: Processor, ctx: ProcessingContext): Sequence<Node> =
    this.map { processor.compute(it, ctx) }

/**
 * Обрабатывает все под-ноды в режиме "VALUE".
 */
fun <T : NodeNodesList> processValue(node: T, processor: Processor, ctx: ProcessingContext): T {
    for (i in 0 until node.nodes.size)
        node.nodes[i] = processor.process(node.nodes[i], ctx, true)!!
    return node
}

/**
 * Обрабатывает все под-ноды в режиме "NO_VALUE".
 */
fun <T : NodeNodesList> processNoValue(node: T, processor: Processor, ctx: ProcessingContext): T {
    for (i in 0 until node.nodes.size)
        node.nodes[i] = processor.process(node.nodes[i], ctx, false)!!
    return node
}

inline fun sliceInsert(list: MutableList<Any?>, index: Int, elements: List<Any?>) {
    val right = list.subList(index + 1, list.size).toList()
    for (i in list.size until elements.size + index + right.size)
        list.add(null)
    elements.forEachIndexed { i, it -> list[index + i] = it }
    right.forEachIndexed { i, it -> list[index + i + elements.size] = it }
}