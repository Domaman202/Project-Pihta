package ru.DmN.pht.base.utils

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser
import ru.DmN.pht.std.base.StdBase
import ru.DmN.pht.std.collections.StdCollections
import ru.DmN.pht.std.decl.StdDecl
import ru.DmN.pht.std.enums.StdEnums
import ru.DmN.pht.std.math.StdMath
import ru.DmN.pht.std.util.StdUtil
import ru.DmN.pht.example.bf.BF
import ru.DmN.pht.example.lkl.LKL
import ru.DmN.pht.std.all.StdAll
import ru.DmN.pht.std.macro.StdMacro
import ru.DmN.pht.std.value.StdValue

open class Module(val name: String) {
    val parsers: MutableMap<Regex, NodeParser> = HashMap()
    val unparsers: MutableMap<Regex, NodeUnparser<*>> = HashMap()
    val compilers: MutableMap<Regex, INodeCompiler<*>> = HashMap()

    open fun inject(parser: Parser, ctx: ParsingContext) {
        if (!ctx.modules.contains(this)) {
            ctx.modules += this
        }
    }

    open fun inject(unparser: Unparser, ctx: UnparsingContext) {
        if (!ctx.modules.contains(this)) {
            ctx.modules += this
        }
    }

    open fun inject(compiler: Compiler, ctx: CompilationContext) =
        inject(compiler, ctx, false)

    open fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!ctx.modules.contains(this))
            ctx.modules += this
        return null
    }

    fun add(name: String, parser: NodeParser? = null, unparser: NodeUnparser<*>? = null, compiler: INodeCompiler<*>? = null): Unit =
        add(
            name
                .replace(".", "\\.")
                .replace("^", "\\^")
                .replace("$", "\\$")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("\\", "\\\\")
                .replace("?", "\\?")
                .replace("*", "\\*")
                .replace("+", "\\+")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .toRegex(),
            parser, unparser, compiler)

    fun add(name: Regex, parser: NodeParser? = null, unparser: NodeUnparser<*>? = null, compiler: INodeCompiler<*>? = null) {
        parser?.let { parsers[name] = it }
        unparser?.let { unparsers[name] = it }
        compiler?.let { compilers[name] = it }
    }

    companion object {
        val MODULES: MutableMap<String, Module> = HashMap()

        init {
            for (module in listOf(StdAll, StdBase, StdCollections, StdDecl, StdEnums, StdMacro, StdMath, StdUtil, StdValue, BF, LKL)) {
                MODULES[module.name] = module
            }
        }
    }
}