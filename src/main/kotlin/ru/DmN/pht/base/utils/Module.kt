package ru.DmN.pht.base.utils

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.INodeUnparser
import ru.DmN.pht.example.bf.BF
import ru.DmN.pht.example.lkl.LKL
import ru.DmN.pht.std.Pihta
import ru.DmN.pht.std.base.StdBase
import ru.DmN.pht.std.collections.StdCollections
import ru.DmN.pht.std.decl.StdDecl
import ru.DmN.pht.std.enums.StdEnums
import ru.DmN.pht.std.macro.StdMacro
import ru.DmN.pht.std.math.StdMath
import ru.DmN.pht.std.module.StdModule
import ru.DmN.pht.std.util.StdUtil
import ru.DmN.pht.std.value.StdValue
import java.io.FileNotFoundException

open class Module(val name: String, var init: Boolean = false) {
    val deps: MutableList<String> = ArrayList()
    val files: MutableList<String> = ArrayList()
    val parsers: MutableMap<Regex, INodeParser> = HashMap()
    val unparsers: MutableMap<Regex, INodeUnparser<*>> = HashMap()
    val processors: MutableMap<Regex, INodeProcessor<*>> = HashMap()
    val compilers: MutableMap<Regex, INodeCompiler<*>> = HashMap()

    fun init() {
        if (!init) {
            Parser(getModuleFile()).parseNode(ParsingContext.of(StdModule))
        }
    }

    open fun inject(parser: Parser, ctx: ParsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            ctx.loadedModules += this
//            Parser(getModuleFile()).parseNode(ParsingContext(SubList(listOf(Base, StdModule), ctx.modules), mutableListOf(Base, StdModule)))
        }
    }

    open fun inject(unparser: Unparser, ctx: UnparsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            ctx.loadedModules += this
        }
    }

    open fun inject(processor: Processor, ctx: ProcessingContext) {
        if (!ctx.loadedModules.contains(this)) {
            ctx.loadedModules += this
        }
    }

    open fun inject(compiler: Compiler, ctx: CompilationContext) =
        inject(compiler, ctx, false)

    open fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!ctx.loadedModules.contains(this)) {
            ctx.loadedModules += this
            files.forEach {
                compiler.compile( // todo:
                    getModuleFile(it),
                    ParsingContext.of(StdValue),
                    ctx
                )
            }
        }
        return null
    }

    private fun getModuleFile() =
        String((this.javaClass.getResourceAsStream("/$name/module.pht") ?: throw FileNotFoundException("/$name/module.pht")).readAllBytes())
    private fun getModuleFile(file: String) =
        String((Module::class.java.getResourceAsStream("/$name/$file") ?: throw FileNotFoundException("/$name/$file")).readAllBytes())

    fun add(name: String, parser: INodeParser? = null, unparser: INodeUnparser<*>? = null, processor: INodeProcessor<*>? = null, compiler: INodeCompiler<*>? = null): Unit =
        add(
            name
                .replace(".", "\\.")
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
                .toRegex(),
            parser, unparser, processor, compiler)

    fun add(name: Regex, parser: INodeParser? = null, unparser: INodeUnparser<*>? = null, processor: INodeProcessor<*>? = null, compiler: INodeCompiler<*>? = null) {
        parser      ?.let { parsers     [name] = it }
        unparser    ?.let { unparsers   [name] = it }
        processor   ?.let { processors  [name] = it }
        compiler    ?.let { compilers   [name] = it }
    }

    override fun toString(): String =
        "Module[$name]"

    companion object {
        val MODULES: MutableMap<String, Module> = HashMap()

        init {
            for (module in listOf(Pihta, StdBase, StdCollections, StdDecl, StdEnums, StdMacro, StdMath, StdModule, StdUtil, StdValue, BF, LKL)) {
                MODULES[module.name] = module
            }
        }

        fun getModuleFile(name: String) =
            String((Module::class.java.getResourceAsStream("/$name/module.pht") ?: throw FileNotFoundException("/$name/module.pht")).readAllBytes())
    }
}