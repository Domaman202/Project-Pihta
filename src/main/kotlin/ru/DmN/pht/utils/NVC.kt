package ru.DmN.pht.utils

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualField
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.compiler.java.utils.load

abstract class NVC {
    abstract val name: String
    abstract val type: VirtualType

    abstract fun load(node: MethodNode, i: Int = -1)

    companion object {
        fun of(variable: Variable) =
            VarImpl(variable)
        fun of(field: VirtualField) =
            FieldImpl(field)
    }


    class VarImpl(private val variable: Variable) : NVC() {
        override val name: String
            get() = variable.name
        override val type: VirtualType
            get() = variable.type()

        override fun load(node: MethodNode, i: Int) {
            load(if (i == -1) variable else Variable(variable.name, variable.type, i, false), node)
        }
    }

    class FieldImpl(private val field: VirtualField) : NVC() {
        override val name: String
            get() = this.field.name
        override val type: VirtualType
            get() = this.field.type

        override fun load(node: MethodNode, i: Int) {
            node.visitFieldInsn(if (field.modifiers.isStatic) Opcodes.GETSTATIC else Opcodes.GETFIELD, field.declaringClass.className, field.name, field.desc)
        }
    }
}