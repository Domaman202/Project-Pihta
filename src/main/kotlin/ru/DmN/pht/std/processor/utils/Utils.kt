package ru.DmN.pht.std.processor.utils

import ru.DmN.pht.std.ast.NodeFieldA
import ru.DmN.pht.std.ast.NodeGensNodesList
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.ctx.EnumContext
import ru.DmN.pht.std.processor.ctx.GlobalContext
import ru.DmN.pht.std.processor.ctx.MacroContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.utils.IContextCollection
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

// a
fun nodeAGet(line: Int, name: String, index: String) =
    NodeNodesList(Token.operation(line, "aget"),
        mutableListOf(nodeGetOrName(line, name), nodeGetOrName(line, index)))
fun nodeArrayOf(line: Int, elements: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "array-of"), elements)
fun nodeArraySize(line: Int, name: String) =
    NodeNodesList(Token.operation(line, "array-size"),
        mutableListOf(nodeGetOrName(line, name)))
fun nodeArrayType(line: Int, type: String, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "array-of-type"), nodes.apply { add(0, nodeValueClass(line, type)) })
fun nodeAs(line: Int, node: Node, type: String) =
    NodeGensNodesList(Token.operation(line, "as"),
        mutableListOf(nodeValueClass(line, type), node),
        emptyList())
fun nodeAs(line: Int, nodes: List<Node>) =
    NodeGensNodesList(Token.operation(line, "as"), nodes.toMutableList(), emptyList())
fun nodeASet(line: Int, name: String, index: Int, value: Node) =
    NodeNodesList(Token.operation(line, "aset"), mutableListOf(nodeGetOrName(line, name), nodeValue(line, index), value))
// b
fun nodeBody(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "body"), nodes)
// c
fun nodeCCall(line: Int) =
    NodeNodesList(Token.operation(line, "ccall"))
fun nodeCls(line: Int, name: String, parents: List<String>, nodes: List<Node>) =
    NodeNodesList(Token.operation(line, "cls"),
        mutableListOf(nodeValue(line, name), nodeValn(line, parents.map { nodeValue(line, it) }.toMutableList())).apply { addAll(nodes) })
fun nodeCls(line: Int, name: String, parent: String, node: Node) =
    nodeCls(line, name, listOf(parent), listOf(node))
fun nodeCtor(line: Int, nodes: List<Node>) =
    NodeNodesList(Token.operation(line, "ctor"),
        mutableListOf<Node>(nodeValn(line, mutableListOf())).apply { addAll(nodes) })
fun nodeCycle(line: Int, cond: Node, body: List<Node>) =
    NodeNodesList(Token.operation(line, "cycle"), mutableListOf(cond).apply { addAll(body) })
fun nodeCycle(line: Int, cond: Node, body: Node) =
    NodeNodesList(Token.operation(line, "cycle"), mutableListOf(cond, body))
// d
fun nodeDef(line: Int, name: String, type: String) =
    NodeNodesList(Token.operation(line, "def"),
        mutableListOf(nodeValn(line, nodeValn(line, mutableListOf(nodeValueClass(line, type), nodeGetOrName(line, name))))))
fun nodeDef(line: Int, name: String, value: Node) =
    NodeNodesList(Token.operation(line, "def"),
        mutableListOf(nodeValn(line, nodeValn(line, mutableListOf(nodeGetOrName(line, name), value)))))
fun nodeDefSet(line: Int, name: Node, value: Node) =
    NodeNodesList(Token.operation(line, "def-set"), mutableListOf(name, value))
fun nodeDefn(line: Int, name: String, ret: String, args: List<Pair<String, String>>, nodes: List<Node>) =
    NodeNodesList(Token.operation(line, "defn"),
        mutableListOf(
            nodeValue(line, name),
            nodeValue(line, ret),
            nodeValn(line, args.map { nodeValn(line, mutableListOf(nodeValue(line, it.first), nodeValueClass(line, it.second))) }.toMutableList()))
            .apply { addAll(nodes) })
fun nodeDefn(line: Int, name: String, ret: String, args: List<Pair<String, String>>, node: Node) =
    nodeDefn(line, name, ret, args, mutableListOf(node))
fun nodeDefn(line: Int, name: String, ret: String, node: Node) =
    nodeDefn(line, name, ret, emptyList(), mutableListOf(node))
fun nodeDefn(line: Int, name: String, ret: String, nodes: MutableList<Node>) =
    nodeDefn(line, name, ret, emptyList(), nodes)
// f
fun nodeFld(line: Int, args: List<Pair<String, String>>) =
    NodeFieldA(Token.operation(line, "fld"),
        mutableListOf(nodeValn(line, args.map { (nodeValn(line, mutableListOf(nodeGetOrName(line, it.first), nodeValueClass(line, it.second)))) }.toMutableList())))
fun nodeFor(line: Int, variable: String, iterable: Node, body: Node) =
    NodeNodesList(Token.operation(line, "for"),
        mutableListOf(nodeValn(line, mutableListOf(nodeGetOrName(line, variable), iterable)), body))
// g
fun nodeGetOrName(line: Int, name: String) =
    NodeGetOrName(Token.operation(line, "get-or-name!"), name, false)
// i
fun nodeIf(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "if"), nodes)
// m
fun nodeMCall(line: Int, instance: Node, name: String, args: List<Node>) =
    NodeNodesList(Token.operation(line, "mcall"),
        mutableListOf(instance, nodeValue(line, name)).apply { addAll(args) })
fun nodeMCall(line: Int, type: String, name: String, args: List<Node>) =
    NodeNodesList(Token.operation(line, "mcall"),
        mutableListOf<Node>(nodeValueClass(line, type), nodeValue(line, name)).apply { addAll(args) })
// n
fun nodeNew(line: Int, type: String, args: List<Node>) =
    NodeNodesList(Token.operation(line, "new"),
        mutableListOf<Node>(nodeValueClass(line, type)).apply { addAll(args) })
fun nodeNewArray(line: Int, type: String, size: Int) =
    NodeNodesList(Token.operation(line, "new-array"),
        mutableListOf(nodeValueClass(line, type), nodeValue(line, size)))
// p
fun nodePrintln(line: Int, msg: Node) =
    NodeNodesList(Token.operation(line, "println"), mutableListOf(msg))
// r
fun nodeRet(line: Int, value: Node) =
    NodeNodesList(Token.operation(line, "ret"), mutableListOf(value))
// v
fun nodeValn(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "valn"), nodes)
fun nodeValn(line: Int, node: Node) =
    NodeNodesList(Token.operation(line, "valn"), mutableListOf(node))
fun nodeValueNil(line: Int) =
    NodeValue.of(line, NodeValue.Type.NIL, "nil")
fun nodeValueClass(line: Int, name: String) =
    NodeValue.of(line, NodeValue.Type.CLASS, name)
fun nodeValue(line: Int, value: String) =
    NodeValue.of(line, NodeValue.Type.STRING, value)
fun nodeValue(line: Int, value: Int) =
    NodeValue.of(line, NodeValue.Type.INT, value.toString())
fun nodeValue(line: Int, value: Boolean) =
    NodeValue.of(line, NodeValue.Type.BOOLEAN, value.toString())
// w
fun nodeWithGens(line: Int, node: Node, generics: Sequence<Node>) =
    NodeNodesList(Token.operation(line, "with-gens"),
        mutableListOf(node).apply { this.addAll(generics) })

// Аннотации
fun nodeStatic(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "@static"), nodes)
fun nodeStatic(line: Int, node: Node) =
    nodeStatic(line, mutableListOf(node))

fun sliceInsert(list: MutableList<Any?>, index: Int, elements: List<Any?>) {
    val right = list.subList(index + 1, list.size).toList()
    for (i in list.size until elements.size + index + right.size)
        list.add(null)
    elements.forEachIndexed { i, it -> list[index + i] = it }
    right.forEachIndexed { i, it -> list[index + i + elements.size] = it }
}

fun <T : IContextCollection<T>> T.with(ctx: GlobalContext) =
    this.with("pht/global", ctx)
fun <T : IContextCollection<T>> T.with(ctx: EnumContext) =
    this.with("pht/enum", ctx).apply { this.contexts["pht/class"] = ctx.type }
fun <T : IContextCollection<T>> T.with(ctx: VirtualType?) =
    this.with("pht/class", ctx)
fun <T : IContextCollection<T>> T.with(ctx: VirtualMethod?) =
    this.with("pht/method", ctx)
fun <T : IContextCollection<T>> T.with(ctx: BodyContext) =
    this.with("pht/body", ctx)
fun <T : IContextCollection<T>> T.with(ctx: MacroContext) =
    this.with("pht/macro", ctx)

fun IContextCollection<*>.isEnum() =
    contexts.containsKey("pht/enum")
fun IContextCollection<*>.isClass() =
    contexts.containsKey("pht/class") || isEnum()
fun IContextCollection<*>.isMethod() =
    contexts.containsKey("pht/method")
fun IContextCollection<*>.isBody() =
    contexts.containsKey("pht/body")
fun IContextCollection<*>.isMacro() =
    contexts.containsKey("pht/macro")

var IContextCollection<*>.global
    set(value) { contexts["pht/global"] = value }
    get() = contexts["pht/global"] as GlobalContext
val IContextCollection<*>.enum
    get() = contexts["pht/enum"] as EnumContext
val IContextCollection<*>.clazz
    get() = this.clazzOrNull!!
val IContextCollection<*>.clazzOrNull
    get() = contexts["pht/class"] as VirtualType?
val IContextCollection<*>.method
    get() = contexts["pht/method"] as VirtualMethod
val IContextCollection<*>.body
    get() = this.bodyOrNull!!
val IContextCollection<*>.bodyOrNull
    get() = contexts["pht/body"] as BodyContext?
val IContextCollection<*>.macro
    get() = contexts["pht/macro"] as MacroContext

var MutableMap<String, Any?>.macros
    set(value) { this["pht/macros"] = value }
    get() = this["pht/macros"] as MutableMap<String, MutableList<MacroDefine>>