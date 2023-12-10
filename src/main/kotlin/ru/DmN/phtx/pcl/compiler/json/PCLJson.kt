package ru.DmN.phtx.pcl.compiler.json

import ru.DmN.phtx.pcl.PCL
import ru.DmN.phtx.pcl.compiler.json.compilers.NCArray
import ru.DmN.phtx.pcl.compiler.json.compilers.NCValue
import ru.DmN.siberia.utils.ModuleCompilers

object PCLJson : ModuleCompilers(PCL) {
    override fun onInitialize() {
        // a
        add("array", NCArray)
        // v
        add("value", NCValue)
    }
}