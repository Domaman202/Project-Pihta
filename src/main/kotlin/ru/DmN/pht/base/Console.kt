package ru.DmN.pht.base

import org.objectweb.asm.ClassWriter
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.processor.JavaTypesProvider
import ru.DmN.pht.base.processor.Platform
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.with
import ru.DmN.pht.std.module.StdModule
import ru.DmN.pht.std.module.ast.NodeModule
import java.io.File
import java.io.FileOutputStream
import java.net.URLClassLoader

object Console {
    @JvmStatic
    fun main(args: Array<String>) {
        while (true) {
            print("""
                Список действий
                0. Вывод информации о программе
                1. Вывод информации о модуле
                2. Компиляция модуля
                3. Компиляция и запуск модуля
                
                Выберите действие
                > 
                """.trimIndent()
            )
            val action = readln().toInt()
            println()
            when (action) {
                0 -> {
                    println("""
                        Проект: Пихта
                        Версия: 1.0.0
                        Автор:  DomamaN202
                    """.trimIndent())
                }

                1 -> {
                    print("Введите название модуля: ")
                    printModuleInfo(readln().let {
                        val module = Module[it]
                        if (module?.init != true)
                            Parser(Module.getModuleFile(it)).parseNode(ParsingContext.of(StdModule))
                        (module ?: Module.getOrThrow(it))
                    })
                }

                2 -> {
                    print("Введите расположение модуля: ")
                    compileModule(readln())
                }

                3 -> {
                    print("Введите расположение модуля: ")
                    compileModule(readln())
                    println()
                    println(Class.forName("App", true, URLClassLoader(arrayOf(File("dump").toURL()))).getMethod("main").invoke(null))
                }

                else -> println("Неизвестное действие №$action.")
            }
            println()
        }
    }

    private fun printModuleInfo(module: Module) {
        println("""
            Имя:         ${module.name}
            Версия:      ${module.version}
            Автор:       ${module.author}
            Зависимости: ${module.deps}
            Файлы:       ${module.files}
            Класс:       ${module.javaClass}
            """.trimIndent()
        )
    }

    private fun compileModule(dir: String) {
        val module = (Parser(Module.getModuleFile(dir)).parseNode(ParsingContext.of(StdModule)) as NodeModule).module
        printModuleInfo(module)
        val processed = ArrayList<Node>()
        val processor = Processor(JavaTypesProvider())
        val pctx = ProcessingContext.base().with(Platform.JAVA)
        module.files.forEach {
            processed += processor.process(Parser(module.getModuleFile(it)).parseNode(ParsingContext.base())!!, pctx, ValType.NO_VALUE)!!
        }
        processor.tasks.forEach {
            pctx.stage.set(it.key)
            it.value.forEach { it() }
        }
        val compiler = Compiler()
        val cctx = CompilationContext.base()
        processed.forEach { compiler.compile(it, cctx) }
        compiler.tasks.forEach {
            cctx.stage.set(it.key)
            it.value.forEach { it() }
        }
        File("dump").mkdir()
        compiler.classes.values.forEach {
            if (it.name.contains('/'))
                File("dump/${it.name.substring(0, it.name.lastIndexOf('/'))}").mkdirs()
            FileOutputStream("dump/${it.name}.class").use { stream ->
                val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)
                it.accept(writer)
                val b = writer.toByteArray()
                stream.write(b)
            }
        }
    }
}