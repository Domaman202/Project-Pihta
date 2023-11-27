package ru.DmN.siberia.compiler.java

import ru.DmN.siberia.compiler.java.compilers.NCDefault
import ru.DmN.siberia.compiler.java.compilers.NCUse
import ru.DmN.siberia.compiler.java.compilers.NCUseCtx
import ru.DmN.siberia.utils.ModuleCompilers

object BaseJava : ModuleCompilers(ru.DmN.siberia.Siberia) {
    override fun onInitialize() {
        add("progn",    NCDefault)
        add("use-ctx",  NCUseCtx)
        add("use",      NCUse)
    }
}