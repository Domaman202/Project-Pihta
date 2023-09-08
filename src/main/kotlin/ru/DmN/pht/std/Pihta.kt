package ru.DmN.pht.std

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.StdBase
import ru.DmN.pht.std.collections.StdCollections
import ru.DmN.pht.std.enums.StdEnums
import ru.DmN.pht.std.macro.StdMacro
import ru.DmN.pht.std.math.StdMath
import ru.DmN.pht.std.util.StdUtil

object Pihta : Module("pht") { // todo: ебучие циклические зависимости
    val STD_MODULES
        get() = listOf(StdBase, StdCollections, StdEnums, StdMacro, StdMath, StdUtil)
            .map { it.apply { init() } }
            .sortedWith { a, b -> a.deps.size.compareTo(b.deps.size) }

    override fun inject(parser: Parser, ctx: ParsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(parser, ctx)
            STD_MODULES.forEach { it.inject(parser, ctx) }
        }
    }

    override fun inject(unparser: Unparser, ctx: UnparsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(unparser, ctx)
            STD_MODULES.forEach { it.inject(unparser, ctx) }
        }
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext): Variable? {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(compiler, ctx)
            STD_MODULES.forEach { it.inject(compiler, ctx) }
        }
        return null
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(compiler, ctx, ret)
            STD_MODULES.forEach { it.inject(compiler, ctx, false) }
        }
        return null
    }
}