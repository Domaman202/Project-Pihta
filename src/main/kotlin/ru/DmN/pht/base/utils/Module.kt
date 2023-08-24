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
import ru.DmN.pht.std.macro.StdMacro

open class Module(val name: String) {
    val parsers: MutableMap<String, NodeParser> = HashMap()
    val unparsers: MutableMap<String, NodeUnparser<*>> = HashMap()
    val compilers: MutableMap<String, INodeCompiler<*>> = HashMap()

    fun inject(parser: Parser, ctx: ParsingContext) {
        if (!ctx.modules.contains(this)) {
            ctx.modules += this
        }
    }

    fun inject(unparser: Unparser, ctx: UnparsingContext) {
        if (!ctx.modules.contains(this)) {
            ctx.modules += this
        }
    }

    open fun inject(compiler: Compiler, ctx: CompilationContext) {
        inject(compiler, ctx, false)
    }

    open fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!ctx.modules.contains(this))
            ctx.modules += this
        return null
    }

    fun add(name: String, parser: NodeParser? = null, unparser: NodeUnparser<*>? = null, compiler: INodeCompiler<*>? = null) {
        parser?.let { parsers[name] = it }
        unparser?.let { unparsers[name] = it }
        compiler?.let { compilers[name] = it }
    }

    companion object {
        val MODULES: MutableMap<String, Module> = HashMap()

        init {
            for (module in listOf(StdBase, StdCollections, StdDecl, StdEnums, StdMacro, StdMath, StdUtil, BF)) {
                MODULES[module.name] = module
            }
        }
    }
}