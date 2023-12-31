package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeImport
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.macros
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platform
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRImport : INodeProcessor<NodeImport> {
    override fun process(node: NodeImport, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeImport? {
        val gctx = ctx.global

        processor.stageManager.pushTask(ProcessingStage.MACROS_IMPORT) {
            node.data["macros"]?.run {
                val cmacros = gctx.macros
                val pmacros = processor.contexts.macros
                forEach { it ->
                    it as String
                    val i = it.lastIndexOf('.')
                    val name = it.substring(i + 1)
                    cmacros += pmacros[it.substring(0, i)]!!.find { it.name == name }!!
                }
            }
        }

        processor.stageManager.pushTask(ProcessingStage.TYPES_IMPORT) {
            node.data["types"]?.run {
                val aliases = gctx.aliases
                val imports = gctx.imports
                forEach {
                    it as String
                    if (it.endsWith('*'))
                        imports += it.substring(0, it.length - 2)
                    else aliases[it.substring(it.lastIndexOf('.') + 1)] = it
                }
            }
        }

        processor.stageManager.pushTask(ProcessingStage.EXTENSIONS_IMPORT) {
            node.data["extensions"]?.forEach { it ->
                it as String
                gctx.getType(it, processor.tp).methods
                    .stream()
                    .filter { it.modifiers.extension }
                    .forEach { gctx.getExtensions(it.extension!!) += it }
            }
            node.data["methods"]?.forEach { it ->
                it as String
                val i = it.lastIndexOf('.')
                val name = it.substring(i + 1)
                val methods = gctx.methods.getOrPut(name) { ArrayList() }
                gctx.getType(it.substring(0, i), processor.tp).methods
                    .stream()
                    .filter { it.name == name }
                    .forEach { methods.add(it) }
            }
        }

        return when (ctx.platform) {
            Platform.JAVA -> null
            else -> node
        }
    }
}