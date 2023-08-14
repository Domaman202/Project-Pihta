package ru.DmN.pht.std

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NCNodesList
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.std.compiler.java.compilers.*
import ru.DmN.pht.std.parsers.*
import ru.DmN.pht.std.unparsers.*
import ru.DmN.pht.std.utils.Module

object Std : Module("std") {
    init {
        // Атрибуты
        add(name = "@static",   parser = NPStatic,              unparser = NUDefault,   compiler = NCNodesList)
        add(name = "@generic",  parser = NPGeneric,             unparser = NUGeneric,   compiler = NCNodesList)
        // Импорт
        add(name = "eimport",   parser = NPDefault,             unparser = NUDefault,   compiler = NCExtendsImport)
        add(name = "import",    parser = NPImport,              unparser = NUImport,    compiler = NodeCompiler.INSTANCE)
        // Пространство Имён
        add(name = "ns",        parser = NPNamespace,           unparser = NUNamespace, compiler = NCNamespace)
        // Перечисление / Объект / Класс / Интерфейс
        add(name = "enum",      parser = NPClass,               unparser = NUClass,     compiler = NCEnum)
        add(name = "object",    parser = NPClass,               unparser = NUClass,     compiler = NCClass)
        add(name = "class",     parser = NPClass,               unparser = NUClass,     compiler = NCClass)
        add(name = "interface", parser = NPClass,               unparser = NUClass,     compiler = NCClass)
        // Конструктор / Расширение / Функция / Лямбда
        add(name = "ector",     parser = NPCtor,                unparser = NUFunction,  compiler = NCEnumCtor)
        add(name = "ctor",      parser = NPCtor,                unparser = NUFunction,  compiler = NCFunction)
        add(name = "efn",       parser = NPExFunction,          unparser = NUExFunction,compiler = NCExFunction)
        add(name = "sfn",       parser = NPFunction,            unparser = NUFunction,  compiler = NCFunction)
        add(name = "ofn",       parser = NPFunction,            unparser = NUFunction,  compiler = NCFunction)
        add(name = "afn",       parser = NPFunction,            unparser = NUFunction,  compiler = NCFunction)
        add(name = "fn",        parser = NPFunction,            unparser = NUFunction,  compiler = NCFunction)
        add(name = "lambda",    parser = NPFunction,            unparser = NUFunction)
        // Циклы
        add(name = "for",       parser = NPFor,                 unparser = NUFor,       compiler = NCFor)
        add(name = "repeat",    parser = NPDefault,             unparser = NUDefault,   compiler = NCRepeat)
        add(name = "while",     parser = NPDefault,             unparser = NUDefault,   compiler = NCWhile)
        // Условия
        add(name = "if",        parser = NPDefault,             unparser = NUDefault,   compiler = NCIf)
        // Выход
        add(name = "return",    parser = NPDefault,             unparser = NUDefault,   compiler = NCReturn)
        add(name = "yield",     parser = NPDefault,             unparser = NUDefault,   compiler = NCYield)
        // Тело
        add(name = "body",      parser = NPDefault,             unparser = NUDefault,   compiler = NCBody)
        // Создать / Выполнить / Вызвать Супер-Конструктор / Вызвать
        add(name = "new",       parser = NPDefault,             unparser = NUDefault,   compiler = NCNew)
        add(name = "ccall",     parser = NPCtorCall,            unparser = NUCtorCall,  compiler = NCCtorCall)
        add(name = "tcall",     parser = NPDefault,             unparser = NUDefault,   compiler = NCThisCall)
        add(name = "mcall_",    parser = NPMethodCallB)
        add(name = "mcall",     parser = NPMethodCallA,         unparser = NUMethodCall,compiler = NCMethodCall)
        add(name = "call",      parser = NPCall)
        // Сеттеры
        add(name = "fset",      parser = NPFieldSet,            unparser = NUFieldSet)
        add(name = "set",       parser = NPSet,                 unparser = NUSet,       compiler = NCSet)
        // Геттеры
        add(name = "mget",      parser = NPGet,                 unparser = NUMGet)
        add(name = "sfget",     parser = NPStaticFieldGet,      unparser = NUFieldSet,  compiler = NCStaticFieldGet)
        add(name = "fget",      parser = NPGet,                 unparser = NUFieldGet)
        add(name = "get",       parser = NPGet,                 unparser = NUGet,       compiler = NCGet)
        // Поля / Переменные
        add(name=  "efield",    parser = NPDefault,             unparser = NUDefault,   compiler = NCEnumField)
        add(name = "field",     parser = NPField,               unparser = NUField,     compiler = NCField)
        add(name = "def",       parser = NPDef,                 unparser = NUDef,       compiler = NCDef)
        // Преобразование типов
        add(name = "cast",      parser = NPDefault,             unparser = NUDefault,   compiler = NCCast)
        // Итераторы
        add(name = "until",     parser = NPDefault,             unparser = NUDefault)
        add(name = "range",     parser = NPDefault,             unparser = NUDefault,   compiler = NCRange)
        // Значения
        add(name = "value_",    parser = NPValueB)
        add(name = "value",     parser = NPValueA,              unparser = NUValue,     compiler = NCValue)
        // Пустой блок
        add(name = "empty",     parser = NPDefault,             unparser = NUDefault,   compiler = NodeCompiler.INSTANCE)
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        super.inject(compiler, ctx, ret)
        return if (ctx.type.method) {
            val variable = ctx.bctx!!.addVariable("std", "ru.DmN.pht.std.StdFunctions", tmp = ret)
            val label = Label()
            ctx.mctx!!.node.run {
                visitLabel(label)
                visitFieldInsn(
                    Opcodes.GETSTATIC,
                    "ru/DmN/pht/std/StdFunctions",
                    "INSTANCE",
                    "Lru/DmN/pht/std/StdFunctions;"
                )
                if (ret)
                    variable
                else {
                    visitVarInsn(Opcodes.ASTORE, variable.id)
                    ctx.mctx.variableStarts[variable.id] = label
                    null
                }
            }
        } else null
    }
}