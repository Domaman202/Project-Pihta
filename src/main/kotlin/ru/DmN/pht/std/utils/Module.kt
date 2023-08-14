package ru.DmN.pht.std.utils

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.base.unparsers.NodeUnparser
import ru.DmN.pht.std.Std
import ru.DmN.pht.std.collections.StdCollections
import ru.DmN.pht.std.math.StdMath

open class Module(val name: String) {
    val parsers: MutableMap<String, NodeParser> = HashMap()
    val unparsers: MutableMap<String, NodeUnparser<*>> = HashMap()
    val compilers: MutableMap<String, NodeCompiler<*>> = HashMap()

    fun inject(parser: Parser) {
        if (!parser.modules.contains(this)) {
            parser.modules += this
            parser.parsers += parsers
        }
    }

    fun inject(unparser: Unparser) {
        if (!unparser.modules.contains(this)) {
            unparser.modules += this
            unparser.unparsers += unparsers
        }
    }

    open fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!compiler.modules.contains(this)) {
            compiler.modules += this
            compiler.compilers += compilers
        }
        return null
    }

    fun add(name: String, parser: NodeParser? = null, unparser: NodeUnparser<*>? = null, compiler: NodeCompiler<*>? = null) {
        parser?.let { parsers[name] = it }
        unparser?.let { unparsers[name] = it }
        compiler?.let { compilers[name] = it }
    }

    companion object {
        val MODULES: MutableMap<String, Module> = HashMap()

        init {
            for (module in listOf(Std, StdCollections, StdMath)) {
                MODULES[module.name] = module
            }
        }
    }
}