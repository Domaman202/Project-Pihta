@file:Suppress("NOTHING_TO_INLINE")
package ru.DmN.pht.std.processor.utils

import ru.DmN.pht.ctx.ContextKeys
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.ast.NodeModifierNodesList
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.node.NodeParsedTypes
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.ctx.EnumContext
import ru.DmN.pht.std.processor.ctx.GlobalContext
import ru.DmN.pht.std.processor.ctx.MacroContext
import ru.DmN.pht.std.utils.mapMutable
import ru.DmN.pht.utils.LinkedNode
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.ctx.IContextCollection
import ru.DmN.siberia.ctx.IContextKey
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

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

// a
inline fun nodeAGet(info: INodeInfo, name: String, index: String) =
    NodeNodesList(info.withType(NodeTypes.AGET),
        mutableListOf(nodeGetOrName(info, name), nodeGetOrName(info, index)))
inline fun nodeArrayOf(info: INodeInfo, elements: MutableList<Node>) =
    NodeNodesList(info.withType(NodeTypes.ARRAY_OF), elements)
inline fun nodeArraySize(info: INodeInfo, name: String) =
    NodeNodesList(info.withType(NodeTypes.ARRAY_SIZE),
        mutableListOf(nodeGetOrName(info, name)))
inline fun nodeArrayType(info: INodeInfo, type: String, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(NodeTypes.ARRAY_OF_TYPE), nodes.apply { add(0, nodeValueClass(info, type)) })
inline fun nodeAs(info: INodeInfo, node: Node, type: String) =
    NodeNodesList(info.withType(NodeParsedTypes.AS),
        mutableListOf(nodeValueClass(info, type), node))
inline fun nodeAs(info: INodeInfo, nodes: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.AS), nodes.toMutableList())
inline fun nodeASet(info: INodeInfo, name: String, index: Int, value: Node) =
    NodeNodesList(info.withType(NodeParsedTypes.ASET),
        mutableListOf(nodeGetOrName(info, name), nodeValue(info, index), value))
// b
inline fun nodeBody(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.BODY), nodes)
// c
inline fun nodeCls(info: INodeInfo, name: String, parents: List<String>, nodes: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.CLS),
        mutableListOf(nodeValue(info, name), nodeValn(info, parents.mapMutable { nodeValue(info, it) })).apply { addAll(nodes) })
inline fun nodeCls(info: INodeInfo, name: String, parent: String, node: Node) =
    nodeCls(info, name, listOf(parent), listOf(node))
inline fun nodeCycle(info: INodeInfo, cond: Node, body: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.CYCLE),
        mutableListOf(cond).apply { addAll(body) })
// d
inline fun nodeDef(info: INodeInfo, name: String, value: Node) =
    NodeNodesList(info.withType(NodeTypes.DEF),
        mutableListOf(nodeValn(info, nodeValn(info, mutableListOf(nodeGetOrName(info, name), value)))))
inline fun nodeDefn(info: INodeInfo, name: String, ret: String, args: List<Pair<String, String>>, nodes: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.DEFN),
        mutableListOf(
            nodeValue(info, name),
            nodeValue(info, ret),
            nodeValn(info, args.mapMutable { nodeValn(info, mutableListOf(nodeValue(info, it.first), nodeValueClass(info, it.second))) }))
            .apply { addAll(nodes) })
inline fun nodeDefn(info: INodeInfo, name: String, ret: String, args: List<Pair<String, String>>, node: Node) =
    nodeDefn(info, name, ret, args, mutableListOf(node))
inline fun nodeDefn(info: INodeInfo, name: String, ret: String, node: Node) =
    nodeDefn(info, name, ret, emptyList(), mutableListOf(node))
inline fun nodeDefn(info: INodeInfo, name: String, ret: String, nodes: MutableList<Node>) =
    nodeDefn(info, name, ret, emptyList(), nodes)
// g
inline fun nodeGetOrName(info: INodeInfo, name: String) =
    NodeGetOrName(info.withType(NodeTypes.GET_OR_NAME), name, false)
// i
inline fun nodeIf(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(NodeTypes.IF), nodes)
// m
inline fun nodeMCall(info: INodeInfo, instance: Node, name: String, args: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.MCALL),
        mutableListOf(instance, nodeValue(info, name)).apply { addAll(args) })
inline fun nodeMCall(info: INodeInfo, type: String, name: String, args: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.MCALL),
        mutableListOf<Node>(nodeValueClass(info, type), nodeValue(info, name)).apply { addAll(args) })
// n
inline fun nodeNew(info: INodeInfo, type: String, args: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.NEW),
        mutableListOf<Node>(nodeValueClass(info, type)).apply { addAll(args) })
inline fun nodeNewArray(info: INodeInfo, type: String, size: Int) =
    NodeNodesList(info.withType(NodeParsedTypes.NEW_ARRAY),
        mutableListOf(nodeValueClass(info, type), nodeValue(info, size)))
// v
inline fun nodeValn(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.VALN), nodes)
inline fun nodeValn(info: INodeInfo, node: Node) =
    NodeNodesList(info.withType(NodeParsedTypes.VALN), mutableListOf(node))
inline fun nodeValueNil(info: INodeInfo) =
    NodeValue.of(info, NodeValue.Type.NIL, "nil")
inline fun nodeValueClass(info: INodeInfo, name: String) =
    NodeValue.of(info, NodeValue.Type.CLASS, name)
inline fun nodeValue(info: INodeInfo, value: String) =
    NodeValue.of(info, NodeValue.Type.STRING, value)
inline fun nodeValue(info: INodeInfo, value: Int) =
    NodeValue.of(info, NodeValue.Type.INT, value.toString())
inline fun nodeValue(info: INodeInfo, value: Boolean) =
    NodeValue.of(info, NodeValue.Type.BOOLEAN, value.toString())
// w
inline fun nodeWithGens(info: INodeInfo, node: Node, generics: Sequence<Node>) =
    NodeNodesList(info.withType(NodeTypes.WITH_GENS),
        mutableListOf(node).apply { this.addAll(generics) })

// Аннотации
inline fun nodeStatic(info: INodeInfo, nodes: MutableList<Node>) =
    NodeModifierNodesList(info.withType(NodeParsedTypes.ANN_STATIC), nodes)
inline fun nodeStatic(info: INodeInfo, node: Node) =
    nodeStatic(info, mutableListOf(node))

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
    this.with(ContextKeys.ENUM, ctx).apply { this.clazz = ctx.type; this.classes = LinkedNode(this.classes, ctx.type) }
inline fun <T : IContextCollection<T>> T.with(ctx: VirtualType) =
    this.with(ContextKeys.CLASS, ctx).apply { this.classes = LinkedNode(this.classes, ctx) }
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
    contexts.containsKey(ContextKeys.METHOD)
inline fun IContextCollection<*>.isBody() =
    contexts.containsKey(ContextKeys.BODY)
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
    get() = contexts[ContextKeys.CLASSES] as LinkedNode<VirtualType>
inline val IContextCollection<*>.clazzOrNull
    get() = contexts[ContextKeys.CLASS] as VirtualType?
inline val IContextCollection<*>.method
    get() = contexts[ContextKeys.METHOD] as VirtualMethod
inline val IContextCollection<*>.body
    get() = this.bodyOrNull!!
inline val IContextCollection<*>.bodyOrNull
    get() = contexts[ContextKeys.BODY] as BodyContext?
inline val IContextCollection<*>.macro
    get() = contexts[ContextKeys.MACRO] as MacroContext

inline var MutableMap<IContextKey, Any?>.macros
    set(value) { this[ContextKeys.MACROS] = value }
    get() = this[ContextKeys.MACROS] as MutableMap<String, MutableList<MacroDefine>>