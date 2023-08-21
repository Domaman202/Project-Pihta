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

fun Compiler.computeInt(node: Node, ctx: CompilationContext): Int =
    if (node.isConst())
        node.getConstValueAsString().toInt()
    else {
        val result = compute<Any?>(node, ctx, ComputeType.NUMBER)
        if (result is Int)
            result
        else computeInt(result as Node, ctx)
    }

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

fun CompilationContext.with(ctx: GlobalContext): CompilationContext =
    this.with("std/global", ctx)
fun CompilationContext.with(ctx: EnumContext): CompilationContext =
    this.with("std/enum", ctx).apply { this.contexts["std/class"] = ctx }
fun CompilationContext.with(ctx: ClassContext): CompilationContext =
    this.with("std/class", ctx)
fun CompilationContext.with(ctx: MethodContext): CompilationContext =
    this.with("std/method", ctx)
fun CompilationContext.with(ctx: BodyContext): CompilationContext =
    this.with("std/body", ctx)
fun CompilationContext.with(ctx: MacroContext): CompilationContext =
    this.with("std/macro", ctx)

fun CompilationContext.isGlobal(): Boolean =
    contexts.containsKey("std/global")
fun CompilationContext.isEnum(): Boolean =
    contexts.containsKey("std/enum")
fun CompilationContext.isClass(): Boolean =
    contexts.containsKey("std/class") || isEnum()
fun CompilationContext.isMethod(): Boolean =
    contexts.containsKey("std/method")
fun CompilationContext.isBody(): Boolean =
    contexts.containsKey("std/body")
fun CompilationContext.isMacro(): Boolean =
    contexts.containsKey("std/macro")

val CompilationContext.global
    get() = contexts["std/global"] as GlobalContext
val CompilationContext.enum
    get() = contexts["std/enum"] as EnumContext
val CompilationContext.clazz
    get() = contexts["std/class"] as ClassContext
val CompilationContext.method
    get() = contexts["std/method"] as MethodContext
val CompilationContext.body
    get() = contexts["std/body"] as BodyContext
val CompilationContext.macro
    get() = contexts["std/macro"] as MacroContext

val MutableMap<String, Any?>.macros
    get() = this["std/macros"] as MutableMap<String, MutableList<MacroDefine>>
//    get() = this.getOrPut("std/macros") { HashMap<String, MutableList<MacroDefine>>() } as MutableMap<String, MutableList<MacroDefine>>