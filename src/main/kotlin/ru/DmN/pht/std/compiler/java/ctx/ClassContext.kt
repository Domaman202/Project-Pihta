package ru.DmN.pht.std.compiler.java.ctx

import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.base.utils.TypesProvider
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.ctx.GlobalContext

class ClassContext(val node: ClassNode, val clazz: VirtualType)