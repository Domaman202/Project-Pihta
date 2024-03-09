package ru.DmN.pht.cpp

import ru.DmN.pht.compiler.cpp.compilers.NCCls
import ru.DmN.pht.cpp.compiler.ctx.out
import ru.DmN.pht.cpp.compiler.ctx.tests
import ru.DmN.pht.cpp.compilers.*
import ru.DmN.pht.cpp.utils.VTString
import ru.DmN.pht.utils.Platforms.CPP
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.utils.ModuleCompilers
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.MethodModifiers
import ru.DmN.siberia.utils.vtype.VTDynamic
import ru.DmN.siberia.utils.vtype.VirtualMethod.VirtualMethodImpl
import ru.DmN.siberia.utils.vtype.VirtualType
import ru.DmN.siberia.utils.vtype.VirtualType.VirtualTypeImpl
import java.io.FileOutputStream

object PhtCpp : ModuleCompilers("pht/cpp", CPP) {
    private fun initParsers() {

    }

    private fun initUnparsers() {

    }

    private fun initProcessors() {

    }

    private fun initCompilers() {
        // a
        add(ADD_,         NCMath)
        add(AS_,          NCAs)
        // b
        // c
        add(CCALL_,       NCCCall)
        add(CLS_,         NCCls)
        add(CTOR_,        NCCtor)
        add(CYCLE_,       NCCycle)
        // d
        add(DEC_PRE_,     NCIncDec)
        add(DEC_POST_,    NCIncDec)
        add(DEF_,         NCDef)
        add(DEFN_,        NCDefn)
        // e
        add(EQ_,          NCMath)
        // f
        add(FGET_,        NCFGet)
        // g
        add(GET_,         NCGet)
        add(GREAT_,       NCMath)
        add(GREAT_OR_EQ_, NCMath)
        // i
        add(IF_,          NCIf)
        add(INC_PRE_,     NCIncDec)
        add(INC_POST_,    NCIncDec)
        add(ITF_,         NCItf)
        // l
        add(LESS_,        NCMath)
        add(LESS_OR_EQ_,  NCMath)
        // m
        add(MCALL_,       NCMCall)
        add(MUL_,         NCMath)
        // n
        add(NEG_,         NCMath)
        add(NEW_,         NCNew)
        add(NOT_,         NCMath)
        add(NOT_EQ_,      NCMath)
        // o
        add(OBJ_,         NCObj)
        add(OR_,          NCMath)
        // p
        add(PRINT_,       NCPrint)
        add(PRINTLN_,     NCPrint)
        // r
        add(REM_,         NCMath)
        // s
        add(SUB_,         NCMath)
        add(SHIFT_LEFT_,  NCMath)
        add(SHIFT_RIGHT_, NCMath)
        // t
        // u
        // v
        add(VALUE,        NCValue)
        // x
        add(XOR_,         NCMath)

        // @
        add(ANN_TEST_,    NCTest)
    }

    override fun load(processor: Processor, ctx: ProcessingContext, uses: MutableList<String>): Boolean {
        if (!ctx.loadedModules.contains(this)) {
            processor.tp += VTDynamic
            processor.tp += VTString
            val tObject = VirtualTypeImpl("dmn.pht.object").apply {
                methods += VirtualMethodImpl(
                    this,
                    "<init>",
                    VirtualType.VOID,
                    null,
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    MethodModifiers(),
                    null,
                    null,
                    emptyMap()
                )
                methods += VirtualMethodImpl(
                    this,
                    "toString",
                    VTString,
                    null,
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    MethodModifiers(),
                    null,
                    null,
                    emptyMap()
                )
            }
            processor.tp += tObject
            processor.tp += VirtualTypeImpl("dmn.pht.integer").apply {
                parents += tObject
                methods += VirtualMethodImpl(
                    this,
                    "<init>",
                    VirtualType.VOID,
                    null,
                    listOf(VirtualType.INT),
                    listOf("value"),
                    listOf(null),
                    MethodModifiers(),
                    null,
                    null,
                    emptyMap()
                )
                methods += VirtualMethodImpl(
                    this,
                    "toPrimitive",
                    VirtualType.INT,
                    null,
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    MethodModifiers(),
                    null,
                    null,
                    emptyMap()
                )
            }
            processor.tp += VirtualTypeImpl("Class") // Костыль
            processor.tp += VirtualType.VOID
            processor.tp += VirtualType.BOOLEAN
            processor.tp += VirtualType.BYTE
            processor.tp += VirtualType.SHORT
            processor.tp += VirtualType.CHAR
            processor.tp += VirtualType.INT
            processor.tp += VirtualType.LONG
            processor.tp += VirtualType.FLOAT
            processor.tp += VirtualType.DOUBLE
            //
            super.load(processor, ctx, uses)
        }
        return false
    }

    override fun load(compiler: Compiler, ctx: CompilationContext) {
        if (!ctx.loadedModules.contains(this)) {
            // Контексты
            compiler.contexts.out = StringBuilder()
            compiler.contexts.tests = 0
            // Финализация
            compiler.finalizers.add { dir ->
                FileOutputStream("$dir/main.cpp").use { stream ->
                    stream.write("#include \"main.hpp\"\n\n".toByteArray())
                    stream.write(compiler.contexts.out.toString().toByteArray())
                    stream.write("int main(int argc, char *argv[]) {\nif (argc == 1)\nApp::main();\nelse {\nint i = std::stoi(argv[1]);\n".toByteArray())
                    for (i in 0 until compiler.contexts.tests)
                        stream.write("if (i == $i) Test$i::test();\n".toByteArray())
                    stream.write("}\n}".toByteArray())
                }
                FileOutputStream("$dir/main.hpp").use { it.write(PhtCpp.getModuleFile("res/main.hpp").toByteArray()) }
                FileOutputStream("$dir/pht.hpp").use { it.write(PhtCpp.getModuleFile("res/pht.hpp").toByteArray()) }
                //
                Runtime.getRuntime().exec("c++ dump/main.cpp -o dump/main").run {
                    waitFor()
                    val err = String(errorStream.readBytes())
                    if (err.isNotEmpty()) {
                        throw RuntimeException("\n$err")
                    }
                }
            }
            //
            super.load(compiler, ctx)
        }
    }

    init {
        initParsers()
        initUnparsers()
        initProcessors()
        initCompilers()
    }
}