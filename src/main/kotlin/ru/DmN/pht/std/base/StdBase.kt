package ru.DmN.pht.std.base

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NUDefault
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.StdBaseJava
import ru.DmN.pht.std.base.processor.ctx.GlobalContext
import ru.DmN.pht.std.base.parsers.NPGetB
import ru.DmN.pht.std.base.parsers.NPSetA
import ru.DmN.pht.std.base.processor.processors.*
import ru.DmN.pht.std.base.unparsers.NUNs
import ru.DmN.pht.std.base.ups.*
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.util.processors.NRSymbol
import ru.DmN.pht.std.value.StdValue

object StdBase : StdModule("std/base") {
    init {
        // Пространства имён
        add("ns",       NPDefault, NUNs, NRNewNs)
        add("sns",      NPDefault, NUNs, NRSubNs)
        // Пустой блок
        add("unit",     NUPDefault)

        StdBaseJava.init()
    }

    override fun inject(parser: Parser, ctx: ParsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(parser, ctx)
            StdValue.inject(parser, ctx)
        }
    }

    override fun inject(unparser: Unparser, ctx: UnparsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(unparser, ctx)
            StdValue.inject(unparser, ctx)
        }
    }

    override fun inject(processor: Processor, ctx: ProcessingContext, mode: ValType): List<Node>? {
        if (!ctx.loadedModules.contains(this)) {
            StdValue.inject(processor, ctx, ValType.NO_VALUE)
            ctx.contexts["std/base/global"] = GlobalContext()
        }
        return super.inject(processor, ctx, mode)
    }
}