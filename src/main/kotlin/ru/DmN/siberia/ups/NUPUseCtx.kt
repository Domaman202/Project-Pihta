package ru.DmN.siberia.ups

import ru.DmN.pht.std.module.StdModule
import ru.DmN.pht.std.processor.utils.exports
import ru.DmN.pht.std.processor.utils.isExports
import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.*
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.INUPC
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.Variable
import java.util.*

/**
 * Универсальный обработчик инструкции использования в контексте.
 */
object NUPUseCtx : INUPC<NodeUse, NodeParsedUse, NodeProcessedUse> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val names = ArrayList<String>()
        val exports = ArrayList<NodeNodesList>()
        var tk = parser.nextToken()!!
        while (tk.type == Token.Type.OPERATION) {
            names.add(tk.text!!)
            tk = parser.nextToken()!!
        }
        parser.tokens.push(tk)
        val context = ctx.subCtx()
        if (context.isExports())
            context.exports.push(exports)
        else context.exports = Stack<MutableList<NodeNodesList>>().apply { push(exports) }
        loadModules(names, parser, context)
        return NPDefault.parse(parser, context) { NodeParsedUse(token, names, it, exports) }.apply {
            context.exports.pop()
            context.loadedModules.filter { names.contains(it.name) }.forEach { it.clear(parser, context) }
        }
    }

    override fun unparse(node: NodeUse, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        loadModules(node.names, unparser, ctx)
        unparser.out.apply {
            append('(').append(node.text).append(' ')
            node.names.forEachIndexed { i, it ->
                append(it)
                if (node.names.size + 1 < i) {
                    append(' ')
                }
            }
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    override fun process(node: NodeParsedUse, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val processed = ArrayList<Node>()
        processor.pushTask(ctx, ProcessingStage.MODULE_POST_INIT) {
            val context = ctx.subCtx()
            injectModules(node, processor, context, ValType.NO_VALUE, processed)
            node.exports.forEach { NRDefault.process(it, processor, ctx, ValType.NO_VALUE) }
            NRDefault.process(node, processor, context, mode)
        }
        return NodeProcessedUse(node.token, node.names, node.nodes, node.exports, processed)
    }
    override fun compile(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext) {
        val context = injectModules(node, compiler, ctx)
        node.exports.forEach { NCDefault.compile(it, compiler, ctx) }
        NCDefault.compile(node, compiler, context)
    }

    override fun compileVal(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext): Variable {
        val context = injectModules(node, compiler, ctx)
        node.exports.forEach { NCDefault.compile(it, compiler, ctx) }
        return NCDefault.compileVal(node, compiler, context)
    }

    /**
     * Загружает модули в контекст парсинга.
     *
     * @param names Имена модулей.
     */
    fun loadModules(names: List<String>, parser: Parser, context: ParsingContext) {
        names.forEach { name ->
            val module = Module[name]
            if (module?.init != true)
                Parser(Module.getModuleFile(name)).parseNode(ParsingContext.of(ru.DmN.siberia.Siberia, StdModule))
            (module ?: Module.getOrThrow(name)).load(parser, context)
        }
    }

    /**
     * Загружает модули в контекст де-парсинга.
     *
     * @param names Имена модулей.
     */
    fun loadModules(names: List<String>, unparser: Unparser, context: UnparsingContext) {
        names.forEach { name ->
            val module = Module[name]
            if (module?.init != true)
                Parser(Module.getModuleFile(name)).parseNode(ParsingContext.of(ru.DmN.siberia.Siberia, StdModule))
            (module ?: Module.getOrThrow(name)).load(unparser, context)
        }
    }

    /**
     * Загружает модули в контекст процессинга из ноды.
     *
     * @param list Список в который будут помещены обработанные ноды из модулей.
     */
    fun injectModules(node: NodeUse, processor: Processor, ctx: ProcessingContext, mode: ValType, list: MutableList<Node>) =
        node.names.forEachIndexed { i, it -> Module.getOrThrow(it).load(processor, ctx, if (i + 1 < node.names.size) ValType.NO_VALUE else mode)?.let { list += it } }

    /**
     * Загружает модули в локальный контекст.
     *
     * @return Локальный контекст с модулями.
     */
    private fun injectModules(node: NodeProcessedUse, compiler: Compiler, ctx: CompilationContext): CompilationContext {
        val context = ctx.subCtx()
        node.names.forEach{ Module.getOrThrow(it).load(compiler, context) }
        node.processed.forEach { compiler.compile(it, context) }
        return context
    }
}