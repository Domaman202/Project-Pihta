package ru.DmN.siberia.compiler.java

import ru.DmN.siberia.Base
import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.compilers.NCUse
import ru.DmN.pht.base.compiler.java.compilers.NCUseCtx
import ru.DmN.pht.base.utils.ModuleCompilers

object BaseJava : ModuleCompilers(ru.DmN.siberia.Base) {
    override fun onInitialize() {
        add("progn",    NCDefault)
        add("use-ctx",  NCUseCtx)
        add("use",      NCUse)
    }
}