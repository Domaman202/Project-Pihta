package ru.DmN.pht.cpp.compiler.utils

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

/**
 * Нормализация имени для c++:
 *
 * "set-value" -> "setValue"
 * "set-" -> "set_"
 *
 * "dmn.pht.Object" -> "dmn::pht::object"
 */
fun String.normalizeName(): String =
    if (this.contains('-')) {
        val sb = StringBuilder()
        var i = 0
        while (i < this.length) {
            sb.append(
                when (val c = this[i]) {
                    '-' -> if (i == this.lastIndex) '_' else this[++i].uppercase()
                    '.' -> "::"
                    else -> c
                }
            )
            i++
        }
        sb.toString()
    } else this


fun VirtualType.name() =
    name.replace(".", "::")

fun VirtualType.nameStaticType() =
    if (isPrimitive)
        name()
    else "${name()}*"

fun VirtualType.nameType() =
    if (isPrimitive)
        name()
    else "dmn::pht::auto_ptr<${name()}>"

fun Variable.load(builder: StringBuilder) {
    if (!tmp) {
        builder.append(this.name)
    }
}

fun Compiler.compileBlock(node: Node, ctx: CompilationContext, builder: StringBuilder) {
    builder.append("[&]() { return ")
    compileVal(node, ctx).load(builder)
    builder.append("; }()")
}