package ru.DmN.pht.std.collections

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.collections.compiler.java.compilers.*

object StdCollections : Module("std/collections") {
    init {
        //
        add("aset", NPDefault,  NUDefault,  NRDefault,  NCArraySet)
        add("aget", NPDefault,  NUDefault,  NRDefault,  NCArrayGet)
        //
        add("doubles-of",   NPDefault,  NUDefault,  NRDefault,  NCIntsOf(VirtualType.DOUBLE,    Opcodes.T_DOUBLE,   Opcodes.DASTORE))
        add("floats-of",    NPDefault,  NUDefault,  NRDefault,  NCIntsOf(VirtualType.FLOAT,     Opcodes.T_FLOAT,    Opcodes.FASTORE))
        add("longs-of",     NPDefault,  NUDefault,  NRDefault,  NCIntsOf(VirtualType.LONG,      Opcodes.T_LONG,     Opcodes.LASTORE))
        add("ints-of",      NPDefault,  NUDefault,  NRDefault,  NCIntsOf(VirtualType.INT,       Opcodes.T_INT,      Opcodes.IASTORE))
        add("chars-of",     NPDefault,  NUDefault,  NRDefault,  NCIntsOf(VirtualType.CHAR,      Opcodes.T_CHAR,     Opcodes.CASTORE))
        add("shorts-of",    NPDefault,  NUDefault,  NRDefault,  NCIntsOf(VirtualType.SHORT,     Opcodes.T_SHORT,    Opcodes.SASTORE))
        add("bytes-of",     NPDefault,  NUDefault,  NRDefault,  NCIntsOf(VirtualType.BYTE,      Opcodes.T_BYTE,     Opcodes.BASTORE))
        add("booleans-of",  NPDefault,  NUDefault,  NRDefault,  NCIntsOf(VirtualType.BOOLEAN,   Opcodes.T_BOOLEAN,  Opcodes.BASTORE))
        add("array-of",     NPDefault,  NUDefault,  NRDefault,  NCArrayOf)
        add("new-doubles",  NPDefault,  NUDefault,  NRDefault,  NCNewInts(VirtualType.DOUBLE,   Opcodes.T_DOUBLE))
        add("new-floats",   NPDefault,  NUDefault,  NRDefault,  NCNewInts(VirtualType.FLOAT,    Opcodes.T_FLOAT))
        add("new-longs",    NPDefault,  NUDefault,  NRDefault,  NCNewInts(VirtualType.LONG,     Opcodes.T_LONG))
        add("new-ints",     NPDefault,  NUDefault,  NRDefault,  NCNewInts(VirtualType.INT,      Opcodes.T_INT))
        add("new-chars",    NPDefault,  NUDefault,  NRDefault,  NCNewInts(VirtualType.CHAR,     Opcodes.T_CHAR))
        add("new-shorts",   NPDefault,  NUDefault,  NRDefault,  NCNewInts(VirtualType.SHORT,    Opcodes.T_SHORT))
        add("new-bytes",    NPDefault,  NUDefault,  NRDefault,  NCNewInts(VirtualType.BYTE,     Opcodes.T_BYTE))
        add("new-booleans", NPDefault,  NUDefault,  NRDefault,  NCNewInts(VirtualType.BOOLEAN,  Opcodes.T_BOOLEAN))
        add("new-array",    NPDefault,  NUDefault,  NRDefault,  NCNewArray)
        //
        add("list", NPDefault,  NUDefault,  NRDefault,  NCList)
        add("map",  NPDefault,  NUDefault,  NRDefault)
    }
}