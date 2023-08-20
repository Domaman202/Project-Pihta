package ru.DmN.pht.std.collections

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.std.unparsers.NUDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.collections.compiler.java.compilers.*

object StdCollections : Module("std/collections") {
    init {
        add("doubles-of",   NPDefault,  NUDefault,  NCIntsOf(VirtualType.DOUBLE,    Opcodes.T_DOUBLE,   Opcodes.DASTORE))
        add("floats-of",    NPDefault,  NUDefault,  NCIntsOf(VirtualType.FLOAT,     Opcodes.T_FLOAT,    Opcodes.FASTORE))
        add("longs-of",     NPDefault,  NUDefault,  NCIntsOf(VirtualType.LONG,      Opcodes.T_LONG,     Opcodes.LASTORE))
        add("ints-of",      NPDefault,  NUDefault,  NCIntsOf(VirtualType.INT,       Opcodes.T_INT,      Opcodes.IASTORE))
        add("chars-of",     NPDefault,  NUDefault,  NCIntsOf(VirtualType.CHAR,      Opcodes.T_CHAR,     Opcodes.CASTORE))
        add("shorts-of",    NPDefault,  NUDefault,  NCIntsOf(VirtualType.SHORT,     Opcodes.T_SHORT,    Opcodes.SASTORE))
        add("bytes-of",     NPDefault,  NUDefault,  NCIntsOf(VirtualType.BYTE,      Opcodes.T_BYTE,     Opcodes.BASTORE))
        add("booleans-of",  NPDefault,  NUDefault,  NCIntsOf(VirtualType.BOOLEAN,   Opcodes.T_BOOLEAN,  Opcodes.BASTORE))
        add("array-of",     NPDefault,  NUDefault,  NCArrayOf)
        add("new-doubles",  NPDefault,  NUDefault,  NCNewInts(VirtualType.DOUBLE,   Opcodes.T_DOUBLE))
        add("new-floats",   NPDefault,  NUDefault,  NCNewInts(VirtualType.FLOAT,    Opcodes.T_FLOAT))
        add("new-longs",    NPDefault,  NUDefault,  NCNewInts(VirtualType.LONG,     Opcodes.T_LONG))
        add("new-int",      NPDefault,  NUDefault,  NCNewInts(VirtualType.INT,      Opcodes.T_INT))
        add("new-chars",    NPDefault,  NUDefault,  NCNewInts(VirtualType.CHAR,     Opcodes.T_CHAR))
        add("new-shorts",   NPDefault,  NUDefault,  NCNewInts(VirtualType.SHORT,    Opcodes.T_SHORT))
        add("new-bytes",    NPDefault,  NUDefault,  NCNewInts(VirtualType.BYTE,     Opcodes.T_BYTE))
        add("new-booleans", NPDefault,  NUDefault,  NCNewInts(VirtualType.BOOLEAN,  Opcodes.T_BOOLEAN))
        add("new-array",    NPDefault,  NUDefault,  NCNewArray)
        add("list",         NPDefault,  NUDefault,  NCList)
        add("map",          NPDefault,  NUDefault)
    }
}