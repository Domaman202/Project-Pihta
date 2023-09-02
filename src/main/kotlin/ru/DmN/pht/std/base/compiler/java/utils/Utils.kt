package ru.DmN.pht.std.base.compiler.java.utils

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.ctx.*

fun sliceInsert(list: MutableList<Any?>, index: Int, elements: List<Any?>) {
    val right = list.subList(index + 1, list.size).toList()
    for (i in list.size until elements.size + index + right.size)
        list.add(null)
    elements.forEachIndexed { i, it -> list[index + i] = it }
    right.forEachIndexed { i, it -> list[index + i + elements.size] = it }
}

fun Compiler.computeNL(node: Node, ctx: CompilationContext): List<Node> =
    compute<Any?>(node, ctx, ComputeType.NODE).let {
        if (it is List<*>)
            it as List<Node>
        else computeNL(it as Node, ctx)
    }

fun Compiler.computeOnlyNode(node: Node, ctx: CompilationContext): Node =
    compute<Any>(node, ctx, ComputeType.NODE).let { if (it is Node) it else node }

fun Compiler.computeInt(node: Node, ctx: CompilationContext): Int =
    if (node.isConst())
        node.getConstValueAsString().toInt()
    else compute<Any?>(node, ctx, ComputeType.NUMBER).let { if (it is Int) it else computeInt(it as Node, ctx) }

fun Compiler.computeName(node: Node, ctx: CompilationContext): String =
    if (node.isConst())
        node.getConstValueAsString()
    else {
        val result = compute<Any?>(node, ctx, ComputeType.NAME)
        if (result is String)
            result
        else computeName(result as Node, ctx)
    }


fun <T> Compiler.compute(node: Node, ctx: CompilationContext, type: ComputeType): T {
    val nc = this.get(ctx, node)
    return (if (nc is IStdNodeCompiler)
        nc.compute(node, this, ctx, type)
    else node) as T
}

fun Compiler.applyAnnotation(node: Node, ctx: CompilationContext, annotation: Node) {
    val nc = this.get(ctx, node);
    if (nc is IStdNodeCompiler) {
        nc.applyAnnotation(node, this, ctx, annotation)
    }
}

fun CompilationContext.with(ctx: GlobalContext) =
    this.with("std/base/global", ctx)
fun CompilationContext.with(ctx: EnumContext) =
    this.with("std/base/enum", ctx).apply { this.contexts["std/base/class"] = ctx }
fun CompilationContext.with(ctx: ClassContext) =
    this.with("std/base/class", ctx)
fun CompilationContext.with(ctx: MethodContext) =
    this.with("std/base/method", ctx)
fun CompilationContext.with(ctx: BodyContext) =
    this.with("std/base/body", ctx)
fun CompilationContext.with(ctx: MacroContext) =
    this.with("std/base/macro", ctx)

fun CompilationContext.isGlobal() =
    contexts.containsKey("std/base/global")
fun CompilationContext.isEnum() =
    contexts.containsKey("std/base/enum")
fun CompilationContext.isClass() =
    contexts.containsKey("std/base/class") || isEnum()
fun CompilationContext.isMethod() =
    contexts.containsKey("std/base/method")
fun CompilationContext.isBody() =
    contexts.containsKey("std/base/body")
fun CompilationContext.isMacro() =
    contexts.containsKey("std/base/macro")

val CompilationContext.global
    get() = contexts["std/base/global"] as GlobalContext
val CompilationContext.enum
    get() = contexts["std/base/enum"] as EnumContext
val CompilationContext.clazz
    get() = contexts["std/base/class"] as ClassContext
val CompilationContext.method
    get() = contexts["std/base/method"] as MethodContext
val CompilationContext.body
    get() = contexts["std/base/body"] as BodyContext
val CompilationContext.macro
    get() = contexts["std/base/macro"] as MacroContext

val MutableMap<String, Any?>.macros
    get() = this["std/base/macros"] as MutableMap<String, MutableList<MacroDefine>>