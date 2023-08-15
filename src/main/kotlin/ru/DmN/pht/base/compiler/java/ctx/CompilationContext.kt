package ru.DmN.pht.base.compiler.java.ctx

class CompilationContext(val type: Type, val global: GlobalContext, val clazz: ClassContext?, val method: MethodContext?, val body: BodyContext?, val macro: MacroContext?) {
    fun with(type: Type) =
        CompilationContext(type, global, clazz, method, body, macro)
    fun with(global: GlobalContext) =
        CompilationContext(type, global, clazz, method, body, macro)
    fun with(clazz: ClassContext?) =
        CompilationContext(type, global, clazz, method, body, macro)
    fun with(method: MethodContext?) =
        CompilationContext(type, global, clazz, method, body, macro)
    fun with(body: BodyContext?) =
        CompilationContext(type, global, clazz, method, body, macro)
    fun with(macro: MacroContext?) =
        CompilationContext(type, global, clazz, method, body, macro)

    data class Type(val clazz: Boolean = false, val enum: Boolean = false, val method: Boolean = false, val body: Boolean = false, val macro: Boolean = false) {
        fun with(type: Type) =
            Type(clazz || type.clazz, enum || type.enum, method || type.method, body || type.body, macro || type.macro)

        companion object {
            val GLOBAL          = Type()
            val CLASS           = Type(clazz = true)
            val ENUM            = Type(clazz = true, enum = true)
            val METHOD          = Type(method = true)
            val BODY            = Type(body = true)
            val MACRO           = Type(macro = true)
        }
    }
}