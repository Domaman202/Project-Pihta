package ru.DmN.siberia

import org.objectweb.asm.ClassWriter
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.utils.*
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.TypesProvider
import ru.DmN.siberia.utils.getJavaClassVersion
import ru.DmN.pht.std.module.StdModule
import ru.DmN.pht.std.module.ast.NodeModule
import ru.DmN.siberia.Compiler
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
            Имя:           ${module.name}
            Версия:        ${module.version}
            Автор:         ${module.author}
            Зависимости:   ${module.dependencies}
            Использование: ${module.uses}
            Файлы:         ${module.files}
            Класс:         ${module.javaClass}
            """.trimIndent()
        )
    }

    private val Module.dependencies: String
        get() {
            val list = ArrayList<Pair<Int, String>>()
            printDeps(this, list, 0)
            return list.map { "${"'".repeat(it.first)}${it.second}" }.toString()
        }

    private fun printDeps(module: Module, list: MutableList<Pair<Int, String>>, i: Int) {
        list.addAll(module.deps.map { Pair(i, it) })
        module.deps.forEach { printDeps(Module.getOrThrow(it), list, i + 1) }
    }

    private fun compileModule(dir: String) {
        val module = (Parser(Module.getModuleFile(dir)).parseNode(ParsingContext.of(StdModule)) as NodeModule).module
        printModuleInfo(module)
        val processed = ArrayList<Node>()
        val processor = Processor(TypesProvider.JAVA)
        val pctx = ProcessingContext.base().with(Platform.JAVA)
        processed += module.load(processor, pctx, ValType.NO_VALUE)!!
        processor.tasks.forEach {
            pctx.stage.set(it.key)
            it.value.forEach { it() }
        }
        val compiler = Compiler(TypesProvider.JAVA)
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