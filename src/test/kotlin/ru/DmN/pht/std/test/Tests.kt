package ru.DmN.pht.std.test

import java.lang.reflect.Modifier
import kotlin.test.*

class Tests {
    @Test
    fun testBaseModuleUses() {
        Module("test/base/module-uses").run {
            compileModule()
        }
    }


    @Test
    fun testBaseUseCtx() {
        Module("test/base/use-ctx").run {
            compileModule()
        }
    }

    @Test
    fun testAbstractClassImpl() {
        Module("test/pht/abstract-class-impl").run {
            compileModule()
            (runModule(0) as Class<*>).let { it ->
                assertTrue(Modifier.isAbstract(it.modifiers))
                it.methods.find { it.name == "foo" }.let {
                    assertNotNull(it)
                    assertTrue(Modifier.isAbstract(it.modifiers))
                }
            }
            (runModule(1) as Class<*>).let { it ->
                assertTrue(it.interfaces.isEmpty())
                it.methods.find { it.name == "foo" }.let {
                    assertNotNull(it)
                    assertFalse(Modifier.isAbstract(it.modifiers))
                }
            }
            assertNotNull(runModule(2))
            assertEquals(runModule(3), "Foo!")
        }
    }

    @Test
    fun testBaseCompare() {
        Module("test/pht/base-compare").run {
            compileModule()
            assertEquals(runModule(0), false)
            assertEquals(runModule(1), true)
            assertEquals(runModule(2), false)
            assertEquals(runModule(3), true)
            assertEquals(runModule(4), false)
            assertEquals(runModule(5), true)
        }
    }

    @Test
    fun testBaseMath() {
        Module("test/pht/base-math").run {
            compileModule()
            assertEquals(runModule(0), 33)
            assertEquals(runModule(1), 21)
            assertEquals(runModule(2), 0)
            assertEquals(runModule(3), 12)
            assertEquals(runModule(4), 4)
            assertEquals(runModule(5), 1)
            assertEquals(runModule(6), 33)
            assertEquals(runModule(7), 21)
            assertEquals(runModule(8), 0)
            assertEquals(runModule(9), 12)
            assertEquals(runModule(10), 4)
            assertEquals(runModule(11), 1)
        }
    }

    @Test
    fun testClass() {
        Module("test/pht/class").run {
            compileModule()
            (runModule(0) as Class<*>).let {
                assertEquals(it.name, "Test")
                assertEquals(it.superclass, Any::class.java)
                assertTrue(it.interfaces.isEmpty())
            }
        }
    }

    @Test
    fun testDefVar() {
        Module("test/pht/def-var").run {
            compileModule()
            assertEquals(runModule(0), 12)
            assertEquals(runModule(1), 21L)
            assertEquals(runModule(2), 0f)
            assertEquals(runModule(3), 33.44)
        }
    }

    @Test
    fun testExtensionCompare() {
        Module("test/pht/extension-compare").run {
            compileModule()
            assertEquals(runModule(0), "great")
            assertEquals(runModule(1), "great-or-eq")
            assertEquals(runModule(2), "less")
            assertEquals(runModule(3), "less-or-eq")
            assertEquals(runModule(4), "eq")
            assertEquals(runModule(5), "not-eq")
        }
    }

    @Test
    fun testExtensionMath() {
        Module("test/pht/extension-math").run {
            compileModule()
            assertEquals(runModule(0), "Слава России")
            assertEquals(runModule(1), "ЛолКек")
            assertEquals(runModule(2), "mul")
            assertEquals(runModule(3), "div")
            assertEquals(runModule(4), "rem")
        }
    }

    @Test
    fun testInterface() {
        Module("test/pht/interface").run {
            compileModule()
            (runModule(0) as Class<*>).let {
                assertEquals(it.name, "ITest")
                assertNull(it.superclass)
                assertTrue(Modifier.isInterface(it.modifiers))
            }
        }
    }

    @Test
    fun testInterfaceImpl() {
        Module("test/pht/interface-impl").run {
            compileModule()
            (runModule(0) as Class<*>).let { it ->
                it.methods.find { it.name == "foo" }.let {
                    assertNotNull(it)
                    assertTrue(Modifier.isAbstract(it.modifiers))
                }
            }
            (runModule(1) as Class<*>).let { it ->
                assertTrue(it.interfaces.isNotEmpty())
                it.methods.find { it.name == "foo" }.let {
                    assertNotNull(it)
                    assertFalse(Modifier.isAbstract(it.modifiers))
                }
            }
            assertNotNull(runModule(2))
            assertEquals(runModule(3), "Foo!")
        }
    }

    @Test
    fun testTestFn() {
        Module("test/pht/test-fn").run {
            compileModule()
            assertEquals(runModule(0), "Текст из Сибири.")
            assertEquals(runModule(1), "Текст из Кавказа.")
            assertEquals(runModule(2), "Текст из Донбасса.")
        }
    }

    @Test
    fun testUnit() {
        Module("test/pht/unit").run {
            compileModule()
        }
    }

    @Test
    fun testValue() {
        Module("test/pht/value").run {
            compileModule()
            assertEquals(runModule(0), null)
            assertEquals(runModule(1), 12)
            assertEquals(runModule(2), 21.33)
            assertEquals(runModule(3), 44f)
            assertEquals(runModule(4), 202L)
            assertEquals(runModule(5), "Текст")
            assertEquals(runModule(6), Any::class.java)
        }
    }
}