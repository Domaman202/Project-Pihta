package ru.DmN.siberia.utils

import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler

abstract class ModuleCompilers(val module: Module) {
    var init = false

    fun init() {
        if (!init) {
            init = true
            onInitialize()
        }
    }

    protected open fun onInitialize() = Unit

    fun add(name: String, compiler: INodeCompiler<*>) =
        module.add(name, compiler)

    fun add(name: Regex, compiler: INodeCompiler<*>) =
        module.add(name, compiler)
}