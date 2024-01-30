package ru.DmN.pht.compiler.java.ctx

import org.objectweb.asm.tree.ClassNode
import ru.DmN.siberia.utils.TypesProvider
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.processor.ctx.GlobalContext

class ClassContext(val node: ClassNode, val clazz: VirtualType)