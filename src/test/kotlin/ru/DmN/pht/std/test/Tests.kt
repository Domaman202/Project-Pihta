package ru.DmN.pht.std.test

import ru.DmN.siberia.test.Module
import java.lang.reflect.Modifier
import kotlin.test.*

class Tests {
    @Test
    fun testAbstractClassImpl() {
        Module("test/pht/std/abstract-class-impl").run {
            compile()
            (test(0) as Class<*>).let { it ->
                assertTrue(Modifier.isAbstract(it.modifiers))
                it.methods.find { it.name == "foo" }.let {
                    assertNotNull(it)
                    assertTrue(Modifier.isAbstract(it.modifiers))
                }
            }
            (test(1) as Class<*>).let { it ->
                assertTrue(it.interfaces.isEmpty())
                it.methods.find { it.name == "foo" }.let {
                    assertNotNull(it)
                    assertFalse(Modifier.isAbstract(it.modifiers))
                }
            }
            assertNotNull(test(2))
            assertEquals(test(3), "Foo!")
        }
    }

    @Test
    fun testBaseCompare() {
        Module("test/pht/std/base-compare").run {
            compile()
            assertEquals(test(0), false)
            assertEquals(test(1), true)
            assertEquals(test(2), false)
            assertEquals(test(3), true)
            assertEquals(test(4), false)
            assertEquals(test(5), true)
        }
    }

    @Test
    fun testBaseMath() {
        Module("test/pht/std/base-math").run {
            compile()
            for (i in 0..1) {
                assertEquals(test(0 + i * 8), 33)
                assertEquals(test(1 + i * 8), 21)
                assertEquals(test(2 + i * 8), 0)
                assertEquals(test(3 + i * 8), 12)
                assertEquals(test(4 + i * 8), 4)
                assertEquals(test(5 + i * 8), 1)
                assertEquals(test(6 + i * 8), 1)
                assertEquals(test(7 + i * 8), 1024)
            }
        }
    }

    @Test
    fun testCatch() {
        Module("test/pht/std/catch").run {
            compile()
            assertEquals(test(0), 21)
            assertEquals(test(1), 12)
            assertFails { test(2) }
        }
    }

    @Test
    fun testClass() {
        Module("test/pht/std/class").run {
            compile()
            (test(0) as Class<*>).let {
                assertEquals(it.name, "Test")
                assertEquals(it.superclass, Any::class.java)
                assertTrue(it.interfaces.isEmpty())
            }
        }
    }

    @Test
    fun testCollectionOf() {
        Module("test/pht/std/collection-of").run {
            compile()
            assertTrue((test(0) as IntArray).contentEquals(intArrayOf(12, 21, 33)))
            assertTrue((test(1) as LongArray).contentEquals(longArrayOf(202L, 203L, 213L)))
        }
    }

    @Test
    fun testCond() {
        Module("test/pht/std/cond").run {
            compile()
            assertEquals(test(0), "МРБ")
        }
    }

    @Test
    fun testCycle() {
        Module("test/pht/std/cycle").run {
            compile()
            assertEquals(test(0), 45)
        }
    }

    @Test
    fun testDefField() {
        Module("test/pht/std/def-field").run {
            compile()
            assertEquals(test(0), 202)
        }
    }

    @Test
    fun testDefVar() {
        Module("test/pht/std/def-var").run {
            compile()
            assertEquals(test(0), 12)
            assertEquals(test(1), 21L)
            assertEquals(test(2), 0f)
            assertEquals(test(3), 33.44)
        }
    }

    @Test
    fun testExtensionCompare() {
        Module("test/pht/std/extension-compare").run {
            compile()
            assertEquals(test(0), "great")
            assertEquals(test(1), "great-or-eq")
            assertEquals(test(2), "less")
            assertEquals(test(3), "less-or-eq")
            assertEquals(test(4), "eq")
            assertEquals(test(5), "not-eq")
        }
    }

    @Test
    fun testExtensionMath() {
        Module("test/pht/std/extension-math").run {
            compile()
            assertEquals(test(0), "Слава России")
            assertEquals(test(1), "ЛолКек")
            assertEquals(test(2), "mul")
            assertEquals(test(3), "div")
            assertEquals(test(4), "rem")
        }
    }

    @Test
    fun testField() {
        Module("test/pht/std/field").run {
            compile()
            assertEquals(test(0), 213)
        }
    }

    @Test
    fun testGenerics() {
        Module("test/pht/std/generics").run {
            compile()
            assertEquals(test(0), String::class.java)
            assertEquals(test(1), String::class.java)
        }
    }

    @Test
    fun testInterface() {
        Module("test/pht/std/interface").run {
            compile()
            (test(0) as Class<*>).let {
                assertEquals(it.name, "ITest")
                assertNull(it.superclass)
                assertTrue(Modifier.isInterface(it.modifiers))
            }
        }
    }

    @Test
    fun testInterfaceImpl() {
        Module("test/pht/std/interface-impl").run {
            compile()
            (test(0) as Class<*>).let { it ->
                it.methods.find { it.name == "foo" }.let {
                    assertNotNull(it)
                    assertTrue(Modifier.isAbstract(it.modifiers))
                }
            }
            (test(1) as Class<*>).let { it ->
                assertTrue(it.interfaces.isNotEmpty())
                it.methods.find { it.name == "foo" }.let {
                    assertNotNull(it)
                    assertFalse(Modifier.isAbstract(it.modifiers))
                }
            }
            assertNotNull(test(2))
            assertEquals(test(3), "Foo!")
        }
    }

    @Test
    fun testTestFn() {
        Module("test/pht/std/test-fn").run {
            compile()
            assertEquals(test(0), "Текст из Сибири.")
            assertEquals(test(1), "Текст из Кавказа.")
            assertEquals(test(2), "Текст из Донбасса.")
        }
    }

    @Test
    fun testUnit() {
        Module("test/pht/std/unit").run {
            compile()
        }
    }

    @Test
    fun testUnroll() {
        Module("test/pht/std/unroll").run {
            compile()
            assertEquals(test(0), 5)
            assertEquals(test(1), 8)
        }
    }

    @Test
    fun testValue() {
        Module("test/pht/std/value").run {
            compile()
            assertEquals(test(0), null)
            assertEquals(test(1), 12)
            assertEquals(test(2), 21.33)
            assertEquals(test(3), 44f)
            assertEquals(test(4), 202L)
            assertEquals(test(5), "Текст")
            assertEquals(test(6), Any::class.java)
        }
    }
}