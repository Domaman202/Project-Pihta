package ru.DmN.test.phtx.ppl

import ru.DmN.phtx.ppl.attribute.AOffset
import ru.DmN.phtx.ppl.attribute.ASized
import ru.DmN.phtx.ppl.container.CPair
import ru.DmN.phtx.ppl.element.EImage
import ru.DmN.phtx.ppl.page.PageList
import ru.DmN.phtx.ppl.utils.Presentation
import javax.imageio.ImageIO
import javax.swing.SwingUtilities

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        SwingUtilities.invokeLater {
            Presentation("Тестовая Презентация", 1000).apply {
                this += PageList(
                    mutableListOf(
                        CPair(
                            ASized(
                                640,
                                480,
                                EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg")))
                            ),
                            AOffset(
                                0,
                                0,
                                320,
                                0,
                                ASized(
                                    640,
                                    480,
                                    EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg")))
                                )
                            )
                        ),
                        AOffset(
                            0,
                            0,
                            540,
                            540,
                            ASized(
                                -1,
                                480,
                                EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img3.jpg")))
                            )
                        )
                    )
                )
                //
                this.show()
            }
        }
    }
}