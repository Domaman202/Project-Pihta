package ru.DmN.pht.std.processor.utils

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.utils.IContextCollection
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.*
import ru.DmN.pht.std.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.ctx.EnumContext
import ru.DmN.pht.std.processor.ctx.GlobalContext
import ru.DmN.pht.std.processor.ctx.MacroContext
import java.util.Stack

fun nodeProgn(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "progn"), nodes)
fun nodeValn(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "valn"), nodes)
fun nodeValn(line: Int, node: Node) =
    NodeNodesList(Token.operation(line, "valn"), mutableListOf(node))
fun nodeClass(line: Int, name: String, parents: List<String>, nodes: List<Node>) =
    NodeNodesList(Token.operation(line, "cls"),
        mutableListOf(nodeValueOf(line, name), nodeValn(line, parents.map { nodeValueOf(line, it) }.toMutableList())).apply { addAll(nodes) })
fun nodeFields(line: Int, fields: List<Pair<String, String>>) =
    NodeNodesList(Token.operation(line, "field"),
        fields.map { nodeValn(line, nodeValn(line, mutableListOf(nodeValueOf(line, it.first), nodeClass(line, it.second)))) }.toMutableList())
fun nodeDefn(line: Int, name: String, ret: String, args: List<Pair<String, String>>, nodes: List<Node>) =
    NodeNodesList(Token.operation(line, "defn"),
        mutableListOf(
            nodeValueOf(line, name),
            nodeValueOf(line, ret),
            nodeValn(line, args.map { nodeValn(line, mutableListOf(nodeValueOf(line, it.first), nodeClass(line, it.second))) }.toMutableList()))
            .apply { addAll(nodes) })
fun nodeBody(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "body"), nodes)
fun nodeDef(line: Int, name: String, value: Node) =
    NodeNodesList(Token.operation(line, "def"),
        mutableListOf(nodeValn(line, nodeValn(line, mutableListOf(nodeValueOf(line, name), value)))))
fun nodeAs(line: Int, node: Node, type: String) =
    NodeNodesList(Token.operation(line, "as"),
        mutableListOf(nodeClass(line, type), node))
fun nodeCycle(line: Int, cond: Node, body: List<Node>) =
    NodeNodesList(Token.operation(line, "cycle"),
        mutableListOf(cond).apply { addAll(body) })
fun nodeArraySize(line: Int, name: String) =
    NodeNodesList(Token.operation(line, "array-size"),
        mutableListOf(nodeGetOrNameOf(line, name)))
fun nodeASet(line: Int, name: String, index: Int, value: Node) =
    NodeNodesList(Token.operation(line, "aset"),
        mutableListOf(nodeGetOrNameOf(line, name), nodeValueOf(line, index), value))
fun nodeAGet(line: Int, name: String, index: String) =
    NodeNodesList(Token.operation(line, "aget"),
        mutableListOf(nodeGetOrNameOf(line, name), nodeGetOrNameOf(line, index)))
fun nodeArrayOf(line: Int, elements: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "array-of"), elements)
fun nodeNewArray(line: Int, type: String, size: Int) =
    NodeNodesList(Token.operation(line, "new-array"),
        mutableListOf(nodeClass(line, type), nodeValueOf(line, size)))
fun nodeArray(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "array-of"), nodes)
fun nodeNew(line: Int, type: String, args: List<Node>) =
    NodeNodesList(Token.operation(line, "new"),
        mutableListOf<Node>(nodeClass(line, type)).apply { addAll(args) })
fun nodeCCall(line: Int, args: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "ccall"), args)
fun nodeMCall(line: Int, instance: Node, name: String, args: List<Node>) =
    NodeNodesList(Token.operation(line, "mcall"),
        mutableListOf(instance, nodeValueOf(line, name)).apply { addAll(args) })
fun nodeMCall(line: Int, type: String, name: String, args: List<Node>) =
    NodeNodesList(Token.operation(line, "mcall"),
        mutableListOf<Node>(nodeClass(line, type), nodeValueOf(line, name)).apply { addAll(args) })
fun nodeGetOrNameOf(line: Int, name: String) =
    NodeGetOrName(Token.operation(line, "get-or-name!"), name, false)
fun nodeClass(line: Int, name: String) =
    NodeValue.of(line, NodeValue.Type.CLASS, name)
fun nodeValueOf(line: Int, value: String) =
    NodeValue.of(line, NodeValue.Type.STRING, value)
fun nodeValueOf(line: Int, value: Int) =
    NodeValue.of(line, NodeValue.Type.INT, value.toString())
fun nodeValueOf(line: Int, value: Boolean) =
    NodeValue.of(line, NodeValue.Type.BOOLEAN, value.toString())

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

fun IContextCollection<*>.isModule() =
    contexts.containsKey("pht/module")
fun IContextCollection<*>.isExports() =
    contexts.containsKey("pht/exports")
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

var IContextCollection<*>.module
    set(value) { contexts["pht/module"] = value }
    get() = contexts["pht/module"] as Module
var IContextCollection<*>.exports
    set(value) { contexts["pht/exports"] = value }
    get() = contexts["pht/exports"] as Stack<MutableList<NodeNodesList>>
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