package ru.DmN.pht.std.collections

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.ups.UPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.collections.compiler.java.compilers.*

object StdCollections : StdModule("std/collections") {
    init {
        //
        add("aset",  UPDefault,  NCArraySet)
        add("aget",  UPDefault,  NCArrayGet)
        //
        add("doubles-of",   UPDefault,  NCIntsOf(VirtualType.DOUBLE,    Opcodes.T_DOUBLE,   Opcodes.DASTORE))
        add("floats-of",    UPDefault,  NCIntsOf(VirtualType.FLOAT,     Opcodes.T_FLOAT,    Opcodes.FASTORE))
        add("longs-of",     UPDefault,  NCIntsOf(VirtualType.LONG,      Opcodes.T_LONG,     Opcodes.LASTORE))
        add("ints-of",      UPDefault,  NCIntsOf(VirtualType.INT,       Opcodes.T_INT,      Opcodes.IASTORE))
        add("chars-of",     UPDefault,  NCIntsOf(VirtualType.CHAR,      Opcodes.T_CHAR,     Opcodes.CASTORE))
        add("shorts-of",    UPDefault,  NCIntsOf(VirtualType.SHORT,     Opcodes.T_SHORT,    Opcodes.SASTORE))
        add("bytes-of",     UPDefault,  NCIntsOf(VirtualType.BYTE,      Opcodes.T_BYTE,     Opcodes.BASTORE))
        add("booleans-of",  UPDefault,  NCIntsOf(VirtualType.BOOLEAN,   Opcodes.T_BOOLEAN,  Opcodes.BASTORE))
        add("array-of",     UPDefault,  NCArrayOf)
        add("new-doubles",  UPDefault,  NCNewInts(VirtualType.DOUBLE,   Opcodes.T_DOUBLE))
        add("new-floats",   UPDefault,  NCNewInts(VirtualType.FLOAT,    Opcodes.T_FLOAT))
        add("new-longs",    UPDefault,  NCNewInts(VirtualType.LONG,     Opcodes.T_LONG))
        add("new-ints",     UPDefault,  NCNewInts(VirtualType.INT,      Opcodes.T_INT))
        add("new-chars",    UPDefault,  NCNewInts(VirtualType.CHAR,     Opcodes.T_CHAR))
        add("new-shorts",   UPDefault,  NCNewInts(VirtualType.SHORT,    Opcodes.T_SHORT))
        add("new-bytes",    UPDefault,  NCNewInts(VirtualType.BYTE,     Opcodes.T_BYTE))
        add("new-booleans", UPDefault,  NCNewInts(VirtualType.BOOLEAN,  Opcodes.T_BOOLEAN))
        add("new-array",    UPDefault,  NCNewArray)
        //
        add("list", UPDefault,  NCList)
        add("map",  UPDefault)
    }
}