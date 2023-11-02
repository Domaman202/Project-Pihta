package ru.DmN.pht.std.compiler.java

import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.utils.ModuleCompilers
import ru.DmN.pht.std.Pihta
import ru.DmN.pht.std.compilers.*

object PihtaJava : ModuleCompilers(Pihta) {
    override fun onInitialize() {
        // a
        add("!add",         NCMath)
        add("!aget",        NCAGet)
        add("array-size",   NCArraySize)
        add("!as",          NCAs)
        add("!aset",        NCASet)
        // b
        add("body",         NCBody)
        // c
        add("!cls",         NCClass)
        add("ctor",         NCDefn)
        add("!cycle",       NCCycle)
        // d
        add("!dec",         NCIncDec)
        add("!def",         NCDef)
        add("defn",         NCDefn)
        // e
        add("efield",       NCEField)
        add("efn",          NCDefn)
        add("!eq",          NCCompare)
        // f
        add("!fget",        NCFGet)
        add("field",        NCField)
        add("!fn",          NCFn)
        add("fset",         NCFSet)
        // g
        add("get",          NCGetA)
        add("get-or-name!", NCGetB)
        add("!great",       NCCompare)
        add("!great-or-eq", NCCompare)
        // i
        add("if",           NCIf)
        add("!inc",         NCIncDec)
        add("!itf",         NCClass)
        // l
        add("!less",        NCCompare)
        add("!less-or-eq",  NCCompare)
        // m
        add("!mcall",       NCMCall)
        // n
        add("new",          NCNew)
        add("!new-array",   NCNewArray)
        add("!not",         NCNot)
        add("!not-eq",      NCCompare)
        add("ns",           NCDefault)
        // o
        add("!obj",         NCClass)
        // r
        add("ret",          NCRet)
        add("!rfn",         NCRFn)
        // s
        add("set!",         NCSet)
        add("sns",          NCDefault)
        // u
        add("unit",         NCUnit)
        // v
        add("value",        NCValue)

        // Аннотации
        add("@abstract",    NCDefault)
        add("@final",       NCDefault)
        add("@open",        NCDefault)
        add("@static",      NCDefault)
    }
}