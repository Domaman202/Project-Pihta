package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeImport
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.macros
import ru.DmN.pht.processor.utils.PhtProcessingStage.*
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.node.NodeTypes.IMPORT_
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.pushOrRunTask

object NRImport : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeImport? {
        val gctx = ctx.global
        //
        val module = processor.computeString(node.nodes[0], ctx)
        val data = processor.computeList(node.nodes[1], ctx)
            .map { processor.computeList(it, ctx) }
            .associate { it -> Pair(processor.computeString(it[0], ctx), processor.computeList(it[1], ctx).map { processor.computeString(it, ctx) }) }

        processor.pushOrRunTask(MACROS_IMPORT, node) {
            data["macros"]?.run {
                val cmacros = gctx.macros
                val pmacros = processor.contexts.macros
                forEach { it ->
                    val i = it.lastIndexOf('.')
                    val name = it.substring(i + 1)
                    cmacros += pmacros[if (i == -1) "" else it.substring(0, i)]!!.find { it.name == name }!!
                }
            }
        }

        processor.pushOrRunTask(TYPES_IMPORT, node) {
            data["types"]?.run {
                val aliases = gctx.aliases
                val imports = gctx.imports
                forEach {
                    if (it.endsWith('*'))
                        imports += it.substring(0, it.length - 2)
                    else aliases[it.substring(it.lastIndexOf('.') + 1)] = it
                }
            }
        }

        processor.pushOrRunTask(EXTENSIONS_IMPORT, node) {
            data["extensions"]?.forEach { it ->
                gctx.getType(it).methods
                    .stream()
                    .filter { it.modifiers.extension }
                    .forEach { gctx.getExtensions(it.extension!!) += it }
            }
            data["methods"]?.forEach { it ->
                val i = it.lastIndexOf('.')
                val name = it.substring(i + 1)
                val methods = gctx.methods.getOrPut(name) { ArrayList() }
                val list = gctx.getType(it.substring(0, i)).methods
                if (name == "*")
                    methods.addAll(list)
                else list.stream().filter { it.name == name }.forEach { methods.add(it) }
            }
        }

        return NodeImport(node.info.withType(IMPORT_), module, data)
    }
}