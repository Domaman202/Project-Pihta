package ru.DmN.pht.base.utils

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.INodeParser
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.INodeUnparser
import ru.DmN.pht.std.module.StdModule
import java.io.FileNotFoundException
import java.lang.ref.WeakReference
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler as JavaNodeCompiler

open class Module(val name: String, var init: Boolean = false) {
    val deps: MutableList<String> = ArrayList()
    val files: MutableList<String> = ArrayList()
    val parsers: MutableMap<Regex, INodeParser> = HashMap()
    val unparsers: MutableMap<Regex, INodeUnparser<*>> = HashMap()
    val processors: MutableMap<Regex, INodeProcessor<*>> = HashMap()
    val javaCompilers: MutableMap<Regex, JavaNodeCompiler<*>> = HashMap()

    fun init() {
        if (!init) {
            Parser(getModuleFile()).parseNode(ParsingContext.of(StdModule))
        }
    }

    open fun inject(parser: Parser, ctx: ParsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            ctx.loadedModules.add(0, this)
        }
    }

    open fun inject(unparser: Unparser, ctx: UnparsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            ctx.loadedModules.add(0, this)
        }
    }

    open fun inject(processor: Processor, ctx: ProcessingContext, mode: ValType): List<Node>? =
        if (!ctx.loadedModules.contains(this)) {
            ctx.loadedModules.add(0, this)
            files.map { processor.process(Parser(getModuleFile(it)).parseNode(ParsingContext.base())!!, ctx, mode) }.requireNoNulls()
        } else null

    open fun inject(compiler: Compiler, ctx: CompilationContext): Variable? {
        if (!ctx.loadedModules.contains(this)) {
            ctx.loadedModules.add(0, this)
        }
        return null
    }

    private fun getModuleFile() =
        String((this.javaClass.getResourceAsStream("/$name/module.pht") ?: throw FileNotFoundException("/$name/module.pht")).readAllBytes())
    private fun getModuleFile(file: String) =
        String((Module::class.java.getResourceAsStream("/$name/$file") ?: throw FileNotFoundException("/$name/$file")).readAllBytes())

    fun add(name: String, compiler: JavaNodeCompiler<*>) =
        add(name.toRegularExpr(), compiler)

    fun add(name: Regex, compiler: JavaNodeCompiler<*>) {
        javaCompilers[name] = compiler
    }

    fun add(name: String, parser: INodeParser? = null, unparser: INodeUnparser<*>? = null, processor: INodeProcessor<*>? = null): Unit =
        add(name.toRegularExpr(), parser, unparser, processor)

    fun add(name: Regex, parser: INodeParser? = null, unparser: INodeUnparser<*>? = null, processor: INodeProcessor<*>? = null) {
        parser      ?.let { parsers     [name] = it }
        unparser    ?.let { unparsers   [name] = it }
        processor   ?.let { processors  [name] = it }
    }

    override fun toString(): String =
        "Module[$name]"

    companion object {
        val MODULES: MutableList<Module> = ArrayList()

        fun getOrPut(name: String, put: () -> Module): Module =
            get(name) ?: put().apply { MODULES.add(this) }

        fun getOrThrow(name: String) =
            get(name) ?: throw RuntimeException("Module '$name' not founded!")

        operator fun get(name: String): Module? {
            for (i in 0 until MODULES.size) {
                val module = MODULES[i]
                if (module.name == name) {
                    return module
                }
            }

            return null
        }

        fun getModuleFile(name: String) =
            String((Module::class.java.getResourceAsStream("/$name/module.pht") ?: throw FileNotFoundException("/$name/module.pht")).readAllBytes())

        fun String.toRegularExpr(): Regex =
            this.replace(".", "\\.")
                .replace("^", "\\^")
                .replace("$", "\\$")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("\\", "\\\\")
                .replace("?", "\\?")
                .replace("*", "\\*")
                .replace("+", "\\+")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .toRegex()
    }
}