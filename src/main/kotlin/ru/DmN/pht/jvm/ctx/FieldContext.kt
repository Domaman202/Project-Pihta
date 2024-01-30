package ru.DmN.pht.compiler.java.ctx

import org.objectweb.asm.tree.FieldNode
import ru.DmN.siberia.utils.VirtualField

class FieldContext(val node: FieldNode, val field: VirtualField)