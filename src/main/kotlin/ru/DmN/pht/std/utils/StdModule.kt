package ru.DmN.pht.std.utils

import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.INUP
import ru.DmN.siberia.utils.INUPC

open class StdModule(name: String, init: Boolean = false) : Module(name, init) {
    fun addb(name: String, up: INUPC<*, *, *>?) {
        adda(name, up as INUP<*, *>)
        add(name, up as INodeCompiler<*>)
    }

    fun adda(name: String, up: INUP<*, *>?) =
        add(name, up, up, up)
    fun adda(name: String, up: INUP<*, *>?, parser: INodeParser) =
        add(name, parser, up, up)
    fun adda(name: String, up: INUP<*, *>?, unparser: INodeUnparser<*>) =
        add(name, up, unparser, up)
    fun adda(name: String, up: INUP<*, *>?, processor: INodeProcessor<*>) =
        add(name, up, up, processor)

    fun addb(name: Regex, up: INUPC<*, *, *>?) {
        add(name, up as INUP<*, *>)
        add(name, up as INodeCompiler<*>)
    }

    fun adda(name: Regex, up: INUP<*, *>?) =
        add(name, up, up, up)
    fun adda(name: Regex, up: INUP<*, *>?, parser: INodeParser) =
        add(name, parser, up, up)
    fun adda(name: Regex, up: INUP<*, *>?, unparser: INodeUnparser<*>) =
        add(name, up, unparser, up)
    fun adda(name: Regex, up: INUP<*, *>?, processor: INodeProcessor<*>) =
        add(name, up, up, processor)
}