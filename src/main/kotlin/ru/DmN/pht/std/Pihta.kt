package ru.DmN.pht.std

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.app.StdApp
import ru.DmN.pht.std.base.StdBase
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.collections.StdCollections
import ru.DmN.pht.std.enums.StdEnums
import ru.DmN.pht.std.fp.StdFP
import ru.DmN.pht.std.macro.StdMacro
import ru.DmN.pht.std.math.StdMath
import ru.DmN.pht.std.oop.StdOOP
import ru.DmN.pht.std.out.StdOut
import ru.DmN.pht.std.util.StdUtil
import ru.DmN.pht.std.value.StdValue

object Pihta : StdModule("pht") {
    val STD_MODULES
        get() = listOf(StdApp, StdBase, StdCollections, StdEnums, StdFP, StdMacro, StdMath, StdOOP, StdOut, StdUtil, StdValue)
            .map { it.apply { init() } }
            .sortedWith { a, b -> a.deps.size.compareTo(b.deps.size) }

    override fun inject(parser: Parser, ctx: ParsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(parser, ctx)
            STD_MODULES.forEach { it.init() }
            STD_MODULES.forEach { it.inject(parser, ctx) }
        }
    }

    override fun inject(unparser: Unparser, ctx: UnparsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(unparser, ctx)
            STD_MODULES.forEach { it.inject(unparser, ctx) }
        }
    }

    override fun inject(processor: Processor, ctx: ProcessingContext, mode: ValType): List<Node>? =
        super.inject(processor, ctx, mode)?.let {
            val list = ArrayList<Node>()
            STD_MODULES.forEach { it.inject(processor, ctx, mode)?.let { list += it } }
            list
        }

    override fun inject(compiler: Compiler, ctx: CompilationContext): Variable? {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(compiler, ctx)
            STD_MODULES.forEach { it.inject(compiler, ctx) }
        }
        return null
    }
}