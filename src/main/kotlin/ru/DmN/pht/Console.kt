package ru.DmN.pht.std

import ru.DmN.pht.std.module.StdModule
import ru.DmN.pht.std.module.ast.NodeModule
import ru.DmN.siberia.*
import ru.DmN.siberia.Console
import ru.DmN.siberia.ConsoleOld.initCompileAndRunModule
import ru.DmN.siberia.ConsoleOld.initCompileModule
import ru.DmN.siberia.ConsoleOld.initHelp
import ru.DmN.siberia.ConsoleOld.initModuleInfo
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platform
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processor.utils.with
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.TypesProvider
import java.io.File
import java.io.FileOutputStream

object Console : Console() {
    @JvmStatic
    fun Console.initTestUnparse() {
        this.actions.add(Triple("Тест де-парса", "Парсит и обрабатывает => де-парсит модуль.", Runnable {
            print("Введите расположение модуля: ")
            val dir = readln()
            if (!ConsoleOld.validateModule(dir))
                return@Runnable
            try {
                val tp = TypesProvider.java()
                val module = (Parser(Module.getModuleFile(dir)).parseNode(ParsingContext.of(StdModule)) as NodeModule).module
                ConsoleOld.printModuleInfo(module)
                module.init()
                val processed = ArrayList<Node>()
                val processor = Processor(tp)
                val pctx = ProcessingContext.base().with(Platform.JAVA).apply { this.module = module }
                module.load(processor, pctx, ValType.NO_VALUE)
                File("dump").mkdir()
                FileOutputStream("dump/parsed.unparse.pht").use { out ->
                    val unparser = Unparser()
                    val uctx = UnparsingContext.base()
                    module.nodes.forEach { it ->
                        unparser.unparse(it, uctx, 0)
                        processor.process(it.copy(), pctx, ValType.NO_VALUE)?.let {
                            processed += it
                        }
                    }
                    out.write(unparser.out.toString().toByteArray())
                }
                processor.stageManager.runAll()
                FileOutputStream("dump/processed.unparse.pht").use { out ->
                    val unparser = Unparser()
                    val uctx = UnparsingContext.base()
                    processed.forEach { unparser.unparse(it, uctx, 0) }
                    out.write(unparser.out.toString().toByteArray())
                }
            } catch (error: Throwable) {
                error.printStackTrace()
            }
        }))
    }

    @JvmStatic
    fun Console.initProgramInfo() {
        this.actions.add(Triple("О программе", "Выводит информацию о программе.", Runnable {
            println("""
                Проект: Пихта
                Версия: 1.7.5
                Авторы: DomamaN202, Wannebetheshy
            """.trimIndent())
        }))
    }

    @JvmStatic
    fun main(args: Array<String>) {
        initHelp()
        initProgramInfo()
        initModuleInfo()
        initCompileModule()
        initCompileAndRunModule()
        initTestUnparse()
        run()
    }
}