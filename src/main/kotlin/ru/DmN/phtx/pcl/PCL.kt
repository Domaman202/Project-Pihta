package ru.DmN.phtx.pcl

import ru.DmN.phtx.pcl.compiler.json.PCLJson
import ru.DmN.siberia.utils.Module

/**
 * Pihta Config Library
 */
object PCL : Module("phtx/pcl") {
    init {
        PCLJson.init()
    }
}