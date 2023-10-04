package ru.DmN.pht.std.macro

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.unparsers.NUDefault
import ru.DmN.pht.std.base.processor.utils.macros
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.macro.processors.NRImportMacro
import ru.DmN.pht.std.macro.ups.*

object StdMacro : StdModule("std/macro") {
    init {
        add("import-macro", NPDefault, NUDefault, NRImportMacro)
        add("defmacro",     NUPDefMacro)
        add("macro-unroll", NUPMacroUnroll)
        add("macro-inline", NUPMacroInline)
        add("macro-arg",    NUPMacroArg)
        add("macro!",       NUPMacro)
    }

    override fun inject(processor: Processor, ctx: ProcessingContext, mode: ValType): List<Node>? {
        if (!ctx.loadedModules.contains(this))
            processor.contexts.macros = HashMap()
        return super.inject(processor, ctx, mode)
    }
}