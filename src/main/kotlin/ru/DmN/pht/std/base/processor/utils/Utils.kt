package ru.DmN.pht.std.base.processor.utils

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.IContextCollection
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.fp.processor.ctx.BodyContext
import ru.DmN.pht.std.base.processor.ctx.EnumContext
import ru.DmN.pht.std.base.processor.ctx.GlobalContext
import ru.DmN.pht.std.base.processor.ctx.MacroContext
import ru.DmN.pht.std.fp.ast.NodeValue
import ru.DmN.pht.std.value.ast.NodeGetOrName

fun nodePrognOf(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "progn"), nodes)
fun nodeValnOf(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "valn"), nodes)
fun nodeValnOf(line: Int, node: Node) =
    NodeNodesList(Token.operation(line, "valn"), mutableListOf(node))
fun nodeBodyOf(line: Int, nodes: MutableList<Node>) =
    NodeNodesList(Token.operation(line, "body"), nodes)
fun nodeDefOf(line: Int, name: String, value: Node) =
    NodeNodesList(Token.operation(line, "def"), mutableListOf(nodeValnOf(line, nodeValnOf(line, mutableListOf(nodeValueOf(line, name), value)))))
fun nodeASet(line: Int, name: String, index: Int, value: Node) =
    NodeNodesList(Token.operation(line, "aset"), mutableListOf(nodeGetOrNameOf(line, name), nodeValueOf(line, index), value))
fun nodeNewArray(line: Int, type: String, size: Int) =
    NodeNodesList(Token.operation(line, "new-array"), mutableListOf(nodeClassOf(line, type), nodeValueOf(line, size)))
fun nodeGetOrNameOf(line: Int, name: String) =
    NodeGetOrName(Token.operation(line, "get-or-name!"), name, false)
fun nodeClassOf(line: Int, name: String) =
    NodeValue.of(line, NodeValue.Type.CLASS, name)
fun nodeValueOf(line: Int, value: Int) =
    NodeValue.of(line, NodeValue.Type.INT, value.toString())
fun nodeValueOf(line: Int, value: String) =
    NodeValue.of(line, NodeValue.Type.STRING, value)

fun sliceInsert(list: MutableList<Any?>, index: Int, elements: List<Any?>) {
    val right = list.subList(index + 1, list.size).toList()
    for (i in list.size until elements.size + index + right.size)
        list.add(null)
    elements.forEachIndexed { i, it -> list[index + i] = it }
    right.forEachIndexed { i, it -> list[index + i + elements.size] = it }
}

fun <T : IContextCollection<T>> T.with(ctx: GlobalContext) =
    this.with("std/base/global", ctx)
fun <T : IContextCollection<T>> T.with(ctx: EnumContext) =
    this.with("std/base/enum", ctx).apply { this.contexts["std/base/class"] = ctx.type }
fun <T : IContextCollection<T>> T.with(ctx: VirtualType) =
    this.with("std/base/class", ctx)
fun <T : IContextCollection<T>> T.with(ctx: VirtualMethod) =
    this.with("std/base/method", ctx)
fun <T : IContextCollection<T>> T.with(ctx: BodyContext) =
    this.with("std/base/body", ctx)
fun <T : IContextCollection<T>> T.with(ctx: MacroContext) =
    this.with("std/base/macro", ctx)

fun IContextCollection<*>.isEnum() =
    contexts.containsKey("std/base/enum")
fun IContextCollection<*>.isClass() =
    contexts.containsKey("std/base/class") || isEnum()
fun IContextCollection<*>.isMethod() =
    contexts.containsKey("std/base/method")
fun IContextCollection<*>.isBody() =
    contexts.containsKey("std/base/body")
fun IContextCollection<*>.isMacro() =
    contexts.containsKey("std/base/macro")

val IContextCollection<*>.global
    get() = contexts["std/base/global"] as GlobalContext
val IContextCollection<*>.enum
    get() = contexts["std/base/enum"] as EnumContext
val IContextCollection<*>.clazz
    get() = contexts["std/base/class"] as VirtualType
val IContextCollection<*>.method
    get() = contexts["std/base/method"] as VirtualMethod
val IContextCollection<*>.body
    get() = this.bodyOrNull!!
val IContextCollection<*>.bodyOrNull
    get() = contexts["std/base/body"] as BodyContext?
val IContextCollection<*>.macro
    get() = contexts["std/base/macro"] as MacroContext

var MutableMap<String, Any?>.macros
    set(value) { this["std/base/macros"] = value }
    get() = this["std/base/macros"] as MutableMap<String, MutableList<MacroDefine>>