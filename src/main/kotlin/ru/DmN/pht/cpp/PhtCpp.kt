package ru.DmN.pht.cpp

import ru.DmN.pht.compiler.cpp.compilers.NCCls
import ru.DmN.pht.cpp.compiler.ctx.out
import ru.DmN.pht.cpp.compiler.ctx.tests
import ru.DmN.pht.cpp.compilers.*
import ru.DmN.pht.cpp.processors.NRValue
import ru.DmN.pht.cpp.utils.node.NodeParsedTypes.ANN_NATIVE
import ru.DmN.pht.cpp.utils.node.NodeTypes.ANN_NATIVE_
import ru.DmN.pht.cpp.utils.vtype.VTNativeClass
import ru.DmN.pht.cpp.utils.vtype.VTNativeString
import ru.DmN.pht.cpp.utils.vtype.VTObject
import ru.DmN.pht.cpp.utils.vtype.VTString
import ru.DmN.pht.processor.ctx.getType
import ru.DmN.pht.processors.NRSA
import ru.DmN.pht.utils.Platforms.CPP
import ru.DmN.pht.utils.addSMNP
import ru.DmN.pht.utils.addSNU
import ru.DmN.pht.utils.meta.MetadataKeys
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.utils.ModuleCompilers
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType
import java.io.FileOutputStream

object PhtCpp : ModuleCompilers("pht/cpp", CPP) {
    private fun initParsers() {
        // @
        addSMNP(ANN_NATIVE)
    }

    private fun initUnparsers() {
        // @
        addSNU(ANN_NATIVE)
        addSNU(ANN_NATIVE_)
    }

    private fun initProcessors() {
        // v
        add(VALUE, NRValue)

        // @
        add(ANN_NATIVE, NRSA { it, _, _ -> it.setMetadata(MetadataKeys.NATIVE, true) })
    }

    private fun initCompilers() {
        // a
        add(ADD_,         NCMath)
        add(AS_,          NCAs)
        // c
        add(CCALL_,       NCCCall)
        add(CLS_,         NCCls)
        add(CTOR_,        NCCtor)
        add(CYCLE_,       NCCycle)
        // d
        add(DEBUG,        NCDebug)
        add(DEC_PRE_,     NCIncDec)
        add(DEC_POST_,    NCIncDec)
        add(DEF_,         NCDef)
        add(DEFN_,        NCDefn)
        // e
        add(EQ_,          NCMath)
        // f
        add(FGET_,        NCFGet)
        add(FSET_,        NCFSet)
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
        // v
        add(VALUE,        NCValue)
        // x
        add(XOR_,         NCMath)

        // @
        add(ANN_NATIVE_,  NCDefault)
        add(ANN_TEST_,    NCTest)
    }

    override fun load(processor: Processor, ctx: ProcessingContext, uses: MutableList<String>): Boolean {
        if (!ctx.loadedModules.contains(this)) {
            processor.tp += VTNativeClass
            processor.tp += VTNativeString
            processor.tp += VTObject
            processor.tp += VTString
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
            ctx.getType = NRValue::getType
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
                val sb = StringBuilder()
                sb.append("#include \"main.hpp\"\n\n")
                sb.append(compiler.contexts.out)
                sb.append("int main(int argc, char *argv[]) {\nif (argc == 1) {\n")
                if (compiler.tp.types.contains("App".hashCode()))
                    sb.append("App::main();")
                sb.append("}\nelse {\nint i = std::stoi(argv[1]);\n")
                for (i in 0 until compiler.contexts.tests)
                    sb.append("if (i == $i) Test$i::test();\n")
                sb.append("}\n}")
                FileOutputStream("$dir/main.cpp").use { it.write(sb.toString().toByteArray()) }
                FileOutputStream("$dir/main.hpp").use { it.write(PhtCpp.getModuleFile("res/main.hpp").readBytes()) }
                FileOutputStream("$dir/pht.hpp").use { it.write(PhtCpp.getModuleFile("res/pht.hpp").readBytes()) }
                //
                Runtime.getRuntime().exec("c++ $dir/main.cpp -o $dir/main").run {
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