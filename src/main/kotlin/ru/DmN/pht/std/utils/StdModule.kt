package ru.DmN.pht.std.utils

import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.Module
import ru.DmN.pht.std.processors.INodeUniversalProcessor

open class StdModule(name: String, init: Boolean = false) : Module(name, init) {
    fun add(name: String, up: INodeUniversalProcessor<*, *>?) =
        add(name, up, up, up)
    fun add(name: String, up: INodeUniversalProcessor<*, *>?, parser: INodeParser) =
        add(name, parser, up, up)
    fun add(name: String, up: INodeUniversalProcessor<*, *>?, unparser: INodeUnparser<*>) =
        add(name, up, unparser, up)
    fun add(name: String, up: INodeUniversalProcessor<*, *>?, processor: INodeProcessor<*>) =
        add(name, up, up, processor)

    fun add(name: Regex, up: INodeUniversalProcessor<*, *>?) =
        add(name, up, up, up)
    fun add(name: Regex, up: INodeUniversalProcessor<*, *>?, parser: INodeParser) =
        add(name, parser, up, up)
    fun add(name: Regex, up: INodeUniversalProcessor<*, *>?, unparser: INodeUnparser<*>) =
        add(name, up, unparser, up)
    fun add(name: Regex, up: INodeUniversalProcessor<*, *>?, processor: INodeProcessor<*>) =
        add(name, up, up, processor)
}