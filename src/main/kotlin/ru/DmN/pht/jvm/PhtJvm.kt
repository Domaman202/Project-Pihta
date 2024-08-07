package ru.DmN.pht.jvm

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.ast.ISyncNode
import ru.DmN.pht.jvm.compiler.ctx.compiledClasses
import ru.DmN.pht.jvm.compiler.ctx.compiledClassesOrNull
import ru.DmN.pht.jvm.compilers.*
import ru.DmN.pht.jvm.processors.*
import ru.DmN.pht.jvm.unparsers.NUAnnotation
import ru.DmN.pht.jvm.unparsers.NUClassOf
import ru.DmN.pht.jvm.unparsers.NUSync
import ru.DmN.pht.jvm.utils.node.NodeParsedTypes.*
import ru.DmN.pht.jvm.utils.node.NodeTypes.*
import ru.DmN.pht.module.utils.Module
import ru.DmN.pht.parsers.NPNodeAlias
import ru.DmN.pht.processors.NRSA
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.addSMNP
import ru.DmN.pht.utils.addSNP
import ru.DmN.pht.utils.addSNU
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.ctx.splitModuleBuild
import ru.DmN.siberia.compiler.utils.ModuleCompilers
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processors.NRProgn
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream

object PhtJvm : ModuleCompilers("pht/jvm", JVM) {
    private fun initParsers() {
        // c
        addSNP(CLS_OF)
        // s
        addSNP(SBLOCK)
        addSNP(SYMBOL_CLS)

        // @
        addSMNP(ANN_ANN)
        addSMNP(ANN_LIST)
        addSMNP(ANN_SYNC)

        // Длинные аналоги

        // c
        "class-of"   to "cls-of"
        // s
        "sync-block" to "sblock"
    }

    private infix fun String.to(alias: String) {
        add(this.toRegularExpr(), NPNodeAlias(alias))
    }

    private fun initUnparsers() {
        // c
        addSNU(CLS_OF)
        add(CLS_OF_,  NUClassOf)
        // s
        addSNU(SBLOCK)
        add(SBLOCK_,  NUSync)
        addSNU(SYMBOL_CLS)

        // @
        addSNU(ANN_ANN)
        add(ANN_ANN_, NUAnnotation)
        addSNU(ANN_LIST)
        addSNU(ANN_LIST_)
        addSNU(ANN_SYNC)
        addSNU(ANN_SYNC_)
    }

    private fun initProcessors() {
        // c
        add(CCALL,       NRCCall)
        add(CLS_OF,      NRClassOf)
        add(CLS_OF_,     NRClassOf)
        // s
        add(SBLOCK,      NRSync)
        add(SBLOCK_,     NRProgn)
        add(SYMBOL_CLS,  NRSymbolCls)

        // @
        add(ANN_ANN,   NRAnnotation)
        add(ANN_ANN_,  NRAnnotation)
        add(ANN_LIST,  NRList)
        add(ANN_LIST_, NRList)
        add(ANN_SYNC,  NRSA { it, _, _ -> if (it is ISyncNode) it.sync = true })
    }

    private fun initCompilers() {
        // a
        add(ADD_,         NCMath)
        add(AGET_,        NCAGet)
        add(ALIAS_TYPE_,  NCSkip)
        add(AND_,         NCMath)
        add(ARR_SIZE_,    NCArraySize)
        add(AS_,          NCAs)
        add(ASET_,        NCASet)
        // b
        add(BGET_,        NCBGet)
        add(BSET_,        NCBSet)
        add(BLOCK_,       NCBody)
        add(BREAK_,       NCBreak)
        // c
        add(CATCH_,       NCCatch)
        add(CLS_OF_,      NCClassOf)
        add(CLS_,         NCClass)
        add(CONTINUE_,    NCContinue)
        add(CTOR_,        NCDefn)
        add(CYCLE_,       NCCycle)
        // d
        add(DEF_,         NCDef)
        add(DEF_FLD_,     NCDefFld)
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
        add(GET_,         NCGet)
        add(GREAT_,       NCCompare)
        add(GREAT_OR_EQ_, NCCompare)
        // i
        add(IF_,          NCIf)
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
        add(NB_,          NCNamedBlock)
        add(NEG_,         NCMath)
        add(NEW_,         NCNew)
        add(NEW_ARR_,     NCNewArray)
        add(NOT_,         NCNot)
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
        add(SBLOCK_,      NCSync)
        // t
        add(THROW_,       NCThrow)
        add(TRCALL_,      NCTRCall)
        add(TGET_,        NCTGet)
        // u
        add(UNIT,         NCUnit)
        // v
        add(VALUE,        NCValue)
        // x
        add(XOR_,         NCMath)

        // @
        add(ANN_ANN_,      NCAnnotation)
        add(ANN_LIST_,     NCList)
        add(ANN_SYNC_,     NCDefault)
        add(ANN_TEST_,     NCDefault)
    }

    override fun load(compiler: Compiler, ctx: CompilationContext) {
        if (!ctx.loadedModules.contains(this)) {
            // Контексты
            (compiler.contexts.compiledClassesOrNull ?: HashMap<Module, MutableMap<String, ClassNode>>().apply { compiler.contexts.compiledClassesOrNull = this })[ctx.module] = HashMap()
            // Финализация
            compiler.finalizers.add { dir ->
                val module0 = ctx.module
                val module = module0.name
                File("$dir/$module").mkdirs()
                JarOutputStream(FileOutputStream("$dir/${module.replace('/', '.')}.jar")).use { jar ->
                    iterateCompiledClasses(compiler, module0).forEach {
                        val b =
                            try {
                                val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)
                                it.accept(writer)
                                writer
                            } catch (_: Exception) {
                                println("Внимание: класс '${it.name}' скомпилирован без просчёта фреймов.")
                                val writer = ClassWriter(ClassWriter.COMPUTE_MAXS)
                                it.accept(writer)
                                writer
                            }.toByteArray()
                        //
                        if (it.name.contains('/'))
                            File("$dir/$module/${it.name.substring(0, it.name.lastIndexOf('/'))}").mkdirs()
                        FileOutputStream("$dir/$module/${it.name}.class").write(b)
                        //
                        jar.putNextEntry(JarEntry("${it.name}.class"))
                        jar.write(b)
                        jar.closeEntry()
                    }
                }
            }
            //
            super.load(compiler, ctx)
        }
    }

    private fun iterateCompiledClasses(compiler: Compiler, module: Module): Iterator<ClassNode> {
        if (compiler.contexts.splitModuleBuild)
            return compiler.contexts.compiledClasses[module]!!.values.iterator()
        val modules = compiler.contexts.compiledClasses.values.iterator()
        var classes: Iterator<ClassNode>? = null
        return object : Iterator<ClassNode> {
            override fun hasNext(): Boolean =
                if (classes == null) {
                    if (modules.hasNext()) {
                        classes = modules.next().values.iterator()
                        hasNext()
                    } else {
                        false
                    }
                } else if (classes!!.hasNext()) {
                    true
                } else {
                    classes = null
                    hasNext()
                }

            override fun next(): ClassNode {
                hasNext()
                return classes!!.next()
            }
        }
    }

    init {
        initParsers()
        initUnparsers()
        initProcessors()
        initCompilers()
    }
}