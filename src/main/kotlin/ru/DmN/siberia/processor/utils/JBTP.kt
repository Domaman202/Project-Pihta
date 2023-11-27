package ru.DmN.siberia.processor.utils

import org.objectweb.asm.ClassReader
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.base.utils.MethodModifiers
import ru.DmN.pht.base.utils.TypesProvider
import ru.DmN.pht.base.utils.VirtualField.VirtualFieldImpl
import ru.DmN.pht.base.utils.VirtualMethod.VirtualMethodImpl
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.VirtualType.VirtualTypeImpl
import ru.DmN.pht.base.utils.readAllBytes
import ru.DmN.pht.std.utils.VTDynamic

/**
 * Java Bytecode Types Provider
 */
class JBTP : TypesProvider() {
    init {
        types.add(VirtualType.VOID)
        types.add(VirtualType.BOOLEAN)
        types.add(VirtualType.BYTE)
        types.add(VirtualType.SHORT)
        types.add(VirtualType.CHAR)
        types.add(VirtualType.INT)
        types.add(VirtualType.LONG)
        types.add(VirtualType.FLOAT)
        types.add(VirtualType.DOUBLE)
        types.add(VTDynamic)
    }

    override fun typeOf(name: String): VirtualType =
        typeOfOrNull(name)!!

    override fun typeOfOrNull(name: String): VirtualType? {
        if (name.startsWith('['))
            return typeOfOrNull(name.substring(1))?.arrayType
        types.find { it.name == name || it.desc.replace('/', '.') == name }?.let { return it }
        val stream = Thread.currentThread().contextClassLoader.getResourceAsStream("${name.replace('.', '/')}.class")
            ?: return null
        val node = ClassNode()
        ClassReader(stream.readAllBytes()).accept(node, 0)
        return VirtualTypeImpl(name, ArrayList()).let { type ->
            types += type
            type.parents.apply {
                node.superName?.let { this += typeOf(it.replace('/', '.')) }
                node.interfaces.forEach { this += typeOf(it.replace('/', '.')) }
            }
            type.fields.apply {
                node.fields.forEach {
                    this += VirtualFieldImpl(
                        type,
                        it.name,
                        typeOfDesc(it.desc),
                        it.access and Opcodes.ACC_STATIC != 0,
                        it.access and Opcodes.ACC_ENUM != 0,
                    )
                }
            }
            type.methods.apply {
                node.methods.forEach { it ->
                    val types = typesOfDesc(it.desc)
                    this += VirtualMethodImpl(
                        type,
                        it.name,
                        types.second,
                        types.first,
                        List(types.first.size) { "arg$it" },
                        MethodModifiers(
                            it.access and Opcodes.ACC_VARARGS != 0,
                            it.name == "<init>",
                            it.access and Opcodes.ACC_STATIC != 0,
                            it.access and Opcodes.ACC_ABSTRACT != 0,
                            false,
                        )
                    )
                }
            }
            type
        }
    }

    private fun typesOfDesc(desc: String): Pair<List<VirtualType>, VirtualType> {
        val args = ArrayList<VirtualType>()
        val str = desc.substring(1, desc.indexOf(')'))
        var i = 0
        while (i < str.length) {
            val result = typeOfDesc(str, i)
            i = result.second
            args += result.first
        }
        return Pair(args, typeOfDesc(desc.substring(desc.indexOf(')') + 1)))
    }

    private fun typeOfDesc(str: String, i: Int): Pair<VirtualType, Int> =
        when (str[i]) {
            '[' -> {
                val result = typeOfDesc(str, i + 1)
                Pair(result.first.arrayType, result.second)
            }

            'L' -> {
                var j = i
                while (str[j] != ';')
                    j++
                Pair(typeOfDesc(str.substring(i, ++j)), j)
            }

            else -> Pair(typeOfDesc(str[i].toString()), i + 1)
        }

    private fun typeOfDesc(desc: String): VirtualType =
        if (desc.startsWith('['))
            typeOfDesc(desc.substring(1)).arrayType
        else
            when (desc) {
                "V" -> VirtualType.VOID
                "Z" -> VirtualType.BOOLEAN
                "B" -> VirtualType.BYTE
                "S" -> VirtualType.SHORT
                "C" -> VirtualType.CHAR
                "I" -> VirtualType.INT
                "J" -> VirtualType.LONG
                "F" -> VirtualType.FLOAT
                "D" -> VirtualType.DOUBLE
                else -> typeOf(desc.substring(1, desc.length - 1).replace('/', '.'))
            }
}