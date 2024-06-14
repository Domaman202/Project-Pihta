package ru.DmN.pht.cpp.utils.vtype

import ru.DmN.pht.utils.vtype.PhtVirtualMethod
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.utils.vtype.MethodModifiers

object VTString : PhtVirtualType.Impl("dmn.pht.string") {
    init {
        methods += PhtVirtualMethod.Impl(
            this,
            "<init>",
            VOID,
            null,
            listOf(VTNativeString),
            listOf("value"),
            listOf(null),
            MethodModifiers(),
            null,
            null,
            emptyMap()
        )
        methods += PhtVirtualMethod.Impl(
            this,
            "toString",
            VTNativeString,
            null,
            emptyList(),
            emptyList(),
            emptyList(),
            MethodModifiers(),
            null,
            null,
            emptyMap()
        )
    }
}