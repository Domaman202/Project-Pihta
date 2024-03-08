package ru.DmN.pht.jvm

import org.objectweb.asm.ClassWriter
import ru.DmN.pht.ast.ISyncNode
import ru.DmN.pht.compiler.java.compilers.*
import ru.DmN.pht.jvm.compiler.ctx.classes
import ru.DmN.pht.jvm.compilers.*
import ru.DmN.pht.jvm.node.NodeParsedTypes.*
import ru.DmN.pht.jvm.node.NodeTypes.*
import ru.DmN.pht.jvm.processors.NRAnnotation
import ru.DmN.pht.jvm.processors.NRClassOf
import ru.DmN.pht.jvm.processors.NRList
import ru.DmN.pht.jvm.processors.NRSync
import ru.DmN.pht.jvm.unparsers.NUAnnotation
import ru.DmN.pht.processors.NRSA
import ru.DmN.pht.unparsers.NUClassOf
import ru.DmN.pht.unparsers.NUSync
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.addSANP
import ru.DmN.pht.utils.addSNP
import ru.DmN.pht.utils.addSNU
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.utils.ModuleCompilers
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.processors.NRProgn
import java.io.File
import java.io.FileOutputStream

object PhtJvm : ModuleCompilers("pht/jvm", JVM) {
    private fun initParsers() {
        // c
        addSNP(CLASS_OF)
        // s
        addSNP(SYNC)

        // @
        addSANP(ANN_ANN)
        addSANP(ANN_LIST)
        addSANP(ANN_SYNC)
    }

    private fun initUnparsers() {
        // c
        addSNU(CLASS_OF)
        add(CLASS_OF_, NUClassOf)
        // s
        addSNU(SYNC)
        add(SYNC_,     NUSync)

        // @
        addSNU(ANN_ANN)
        add(ANN_ANN_,  NUAnnotation)
        addSNU(ANN_LIST)
        addSNU(ANN_LIST_)
        addSNU(ANN_SYNC)
        addSNU(ANN_SYNC_)
    }

    private fun initProcessors() {
        // c
        add(CLASS_OF,  NRClassOf)
        add(CLASS_OF_, NRClassOf)
        // s
        add(SYNC,      NRSync)
        add(SYNC_,     NRProgn)

        // @
        add(ANN_ANN,   NRAnnotation)
        add(ANN_ANN_,  NRAnnotation)
        add(ANN_LIST,  NRList)
        add(ANN_LIST_, NRList)
        add(ANN_SYNC, NRSA { it, _, _ -> if (it is ISyncNode) it.sync = true })
    }

    private fun initCompilers() {
        // a
        add(ADD_,         NCMath)
        add(AGET_,        NCAGet)
        add(ALIAS_TYPE_,  NCSkip)
        add(AND_,         NCMath)
        add(ARRAY_SIZE_,  NCArraySize)
        add(AS_,          NCAs)
        add(ASET_,        NCASet)
        // b
        add(BODY_,        NCBody)
        add(BREAK_,       NCBreak)
        // c
        add(CATCH_,       NCCatch)
        add(CLASS_OF_,    NCClassOf)
        add(CLS_,         NCClass)
        add(CONTINUE_,    NCContinue)
        add(CTOR_,        NCDefn)
        add(CYCLE_,       NCCycle)
        // d
        add(DEC_PRE_,     NCIncDec)
        add(DEC_POST_,    NCIncDec)
        add(DEF_,         NCDef)
        add(DEFN_,        NCDefn)
        add(DIV_,         NCMath)
        // e
        add(ECTOR_,       NCECtor)
        add(EFLD_,        NCEField)
        add(EFN_,         NCDefn)
        add(ENUM_,        NCClass)
        add(EQ_,          NCCompare)
        // f
        add(FGET_,        NCFGet)
        add(FLD_,         NCField)
        add(FN_,          NCDefault)
        add(FSET_,        NCFSet)
        // g
        add(GET_,         NCGetA)
        add(GREAT_,       NCCompare)
        add(GREAT_OR_EQ_, NCCompare)
        // i
        add(IF_,          NCIf)
        add(INC_PRE_,     NCIncDec)
        add(INC_POST_,    NCIncDec)
        add(INL_BODY_,    NCInlBody)
        add(INNER_,       NCInner)
        add(IS_,          NCIs)
        add(ITF_,         NCClass)
        // l
        add(LESS_,        NCCompare)
        add(LESS_OR_EQ_,  NCCompare)
        // m
        add(MCALL_,       NCMCall)
        add(MUL_,         NCMath)
        // n
        add(NAMED_BLOCK_, NCNamedBlock)
        add(NEG_,         NCMath)
        add(NEW_,         NCNew)
        add(NEW_ARRAY_,   NCNewArray)
        add(NOT_,         NCCompare)
        add(NOT_EQ_,      NCCompare)
        add(NS_,          NCDefault)
        // o
        add(OBJ_,         NCClass)
        add(OR_,          NCMath)
        // r
        add(REM_,         NCMath)
        add(RET_,         NCRet)
        add(RFN_,         NCRFn)
        // s
        add(SET_,         NCSet)
        add(SHIFT_LEFT_,  NCMath)
        add(SHIFT_RIGHT_, NCMath)
        add(SUB_,         NCMath)
        add(SYNC_,        NCSync)
        // t
        add(THROW_,       NCThrow)
        add(TRCALL_,      NCTRCall)
        add(TYPED_GET_,   NCTypedGet)
        // u
        add(UNIT,         NCUnit)
        // v
        add(VALUE,        NCValue)
        // x
        add(XOR_,         NCMath)

        // @
        add(ANN_ANN_,      NCAnnotation)
        add(ANN_LIST_,     NCList)
    }

    override fun load(compiler: Compiler, ctx: CompilationContext) {
        if (!ctx.loadedModules.contains(this)) {
            // Контексты
            compiler.contexts.classes = HashMap()
            // Финализация
            compiler.finalizers.add { dir ->
                compiler.contexts.classes.values.forEach {
                    if (it.name.contains('/'))
                        File("$dir/${it.name.substring(0, it.name.lastIndexOf('/'))}").mkdirs()
                    FileOutputStream("$dir/${it.name}.class").use { stream ->
                        val writer =
                            try {
                                val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)
                                it.accept(writer)
                                writer
                            } catch (_: ArrayIndexOutOfBoundsException) {
                                println("Внимание: класс '${it.name}' скомпилирован без просчёта фреймов.")
                                val writer = ClassWriter(ClassWriter.COMPUTE_MAXS)
                                it.accept(writer)
                                writer
                            }
                        val b = writer.toByteArray()
                        stream.write(b)
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