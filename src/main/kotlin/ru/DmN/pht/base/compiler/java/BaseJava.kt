package ru.DmN.pht.base.compiler.java

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.compilers.NCUse
import ru.DmN.pht.base.compiler.java.compilers.NCUseCtx
import ru.DmN.pht.base.utils.ModuleCompilers

object BaseJava : ModuleCompilers(Base) {
    override fun onInitialize() {
        add("progn",    NCDefault)
        add("use-ctx",  NCUseCtx)
    }
}