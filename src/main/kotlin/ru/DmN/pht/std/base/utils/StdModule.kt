package ru.DmN.pht.std.base.utils

import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.base.unparser.unparsers.INodeUnparser
import ru.DmN.pht.base.utils.Module

open class StdModule(name: String, init: Boolean = false) : Module(name, init) {
    fun add(name: String, up: IStdUniversalProcessor<*>) =
        add(name, up, up, up, up)
    fun add(name: String, up: IStdUniversalProcessor<*>, parser: INodeParser) =
        add(name, parser, up, up, up)
    fun add(name: String, up: IStdUniversalProcessor<*>, unparser: INodeUnparser<*>) =
        add(name, up, unparser, up, up)
    fun add(name: String, up: IStdUniversalProcessor<*>, processor: IStdUniversalProcessor<*>) =
        add(name, up, up, processor, up)
    fun add(name: String, up: IStdUniversalProcessor<*>, compiler: INodeCompiler<*>) =
        add(name, up, up, up, compiler)

    fun add(name: Regex, up: IStdUniversalProcessor<*>) =
        add(name, up, up, up, up)
    fun add(name: Regex, up: IStdUniversalProcessor<*>, parser: INodeParser) =
        add(name, parser, up, up, up)
    fun add(name: Regex, up: IStdUniversalProcessor<*>, unparser: INodeUnparser<*>) =
        add(name, up, unparser, up, up)
    fun add(name: Regex, up: IStdUniversalProcessor<*>, processor: IStdUniversalProcessor<*>) =
        add(name, up, up, processor, up)
    fun add(name: Regex, up: IStdUniversalProcessor<*>, compiler: INodeCompiler<*>) =
        add(name, up, up, up, compiler)
}