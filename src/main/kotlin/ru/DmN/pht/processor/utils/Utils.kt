@file:Suppress("NOTHING_TO_INLINE")
package ru.DmN.pht.processor.utils

import ru.DmN.pht.ctx.ContextKeys
import ru.DmN.pht.processor.utils.LinkedClassesNode
import ru.DmN.pht.compiler.java.utils.MacroDefine
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.ctx.EnumContext
import ru.DmN.pht.processor.ctx.GlobalContext
import ru.DmN.pht.processor.ctx.MacroContext
import ru.DmN.pht.utils.compute
import ru.DmN.pht.utils.lenArgs
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.ctx.IContextCollection
import ru.DmN.siberia.ctx.IContextKey
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

fun getMethodVariants(variants: Sequence<VirtualMethod>, args: List<ICastable>): Sequence<Pair<VirtualMethod, Boolean>> =
    variants
        .map { Pair(it, if (it.modifiers.extension) listOf(ICastable.of(it.extension!!)) + args else args) }
        .filter { it.first.argsc.size == it.second.size || it.first.modifiers.varargs }
        .map { Pair(it.first, lenArgs(it.first.argsc, it.second, it.first.modifiers.varargs)) }
        .filter { it.second.first > -1 }
        .sortedBy { it.second.first }
        .map { Pair(it.first, it.second.second) }

fun Sequence<Node>.processValues(processor: Processor, ctx: ProcessingContext): Sequence<Node> =
    this.map { processor.process(it, ctx, ValType.VALUE)!! }
fun Sequence<Node>.computeValues(processor: Processor, ctx: ProcessingContext): Sequence<Node> =
    this.map { processor.compute(it, ctx) }

/**
 * Обрабатывает все под-ноды в режиме "VALUE".
 */
fun <T : NodeNodesList> processValue(node: T, processor: Processor, ctx: ProcessingContext): T {
    for (i in 0 until node.nodes.size)
        node.nodes[i] = processor.process(node.nodes[i], ctx, ValType.VALUE)!!
    return node
}

/**
 * Обрабатывает все под-ноды в режиме "NO_VALUE".
 */
fun <T : NodeNodesList> processNoValue(node: T, processor: Processor, ctx: ProcessingContext): T {
    for (i in 0 until node.nodes.size)
        node.nodes[i] = processor.process(node.nodes[i], ctx, ValType.NO_VALUE)!!
    return node
}

inline fun sliceInsert(list: MutableList<Any?>, index: Int, elements: List<Any?>) {
    val right = list.subList(index + 1, list.size).toList()
    for (i in list.size until elements.size + index + right.size)
        list.add(null)
    elements.forEachIndexed { i, it -> list[index + i] = it }
    right.forEachIndexed { i, it -> list[index + i + elements.size] = it }
}

inline fun <T : IContextCollection<T>> T.with(ctx: GlobalContext) =
    this.with(ContextKeys.GLOBAL, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: EnumContext) =
    this.with(ContextKeys.ENUM, ctx).apply {
        this.clazz = ctx.type
        this.classes = LinkedClassesNode(this.classes, ctx.type)
        this.methodOrNull = null
        this.bodyOrNull = null
    }
inline fun <T : IContextCollection<T>> T.with(ctx: VirtualType) =
    this.with(ContextKeys.CLASS, ctx).apply {
        this.classes = LinkedClassesNode(this.classes, ctx)
        this.methodOrNull = null
        this.bodyOrNull = null
    }
inline fun <T : IContextCollection<T>> T.with(ctx: VirtualMethod?) =
    this.with(ContextKeys.METHOD, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: BodyContext) =
    this.with(ContextKeys.BODY, ctx)
inline fun <T : IContextCollection<T>> T.with(ctx: MacroContext) =
    this.with(ContextKeys.MACRO, ctx)

inline fun IContextCollection<*>.isEnum() =
    contexts.containsKey(ContextKeys.ENUM)
inline fun IContextCollection<*>.isClass() =
    contexts.containsKey(ContextKeys.CLASS) || isEnum()
inline fun IContextCollection<*>.isMethod() =
    this.methodOrNull != null
inline fun IContextCollection<*>.isBody() =
    this.bodyOrNull != null
inline fun IContextCollection<*>.isMacro() =
    contexts.containsKey(ContextKeys.MACRO)

inline var IContextCollection<*>.global
    set(value) { contexts[ContextKeys.GLOBAL] = value }
    get() = contexts[ContextKeys.GLOBAL] as GlobalContext
inline val IContextCollection<*>.enum
    get() = contexts[ContextKeys.ENUM] as EnumContext
inline var IContextCollection<*>.clazz
    set(value) { contexts[ContextKeys.CLASS] = value }
    get() = this.clazzOrNull!!
inline var IContextCollection<*>.classes
    set(value) { contexts[ContextKeys.CLASSES] = value }
    get() = contexts[ContextKeys.CLASSES] as LinkedClassesNode<VirtualType>
inline val IContextCollection<*>.clazzOrNull
    get() = contexts[ContextKeys.CLASS] as VirtualType?
inline var IContextCollection<*>.method
    set(value) { this.methodOrNull = value }
    get() = this.methodOrNull!!
inline var IContextCollection<*>.methodOrNull
    set(value) { contexts[ContextKeys.METHOD] = value }
    get() = contexts[ContextKeys.METHOD] as VirtualMethod?
inline var IContextCollection<*>.body
    set(value) { this.bodyOrNull = value }
    get() = this.bodyOrNull!!
inline var IContextCollection<*>.bodyOrNull
    set(value) { contexts[ContextKeys.BODY] = value }
    get() = contexts[ContextKeys.BODY] as BodyContext?
inline val IContextCollection<*>.macro
    get() = contexts[ContextKeys.MACRO] as MacroContext

inline var MutableMap<IContextKey, Any?>.macros
    set(value) { this[ContextKeys.MACROS] = value }
    get() = this[ContextKeys.MACROS] as MutableMap<String, MutableList<MacroDefine>>