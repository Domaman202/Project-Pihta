package ru.DmN.test.phtx.ppl

import ru.DmN.phtx.ppl.element.EImage
import ru.DmN.phtx.ppl.element.EPair
import ru.DmN.phtx.ppl.element.ETitle
import ru.DmN.phtx.ppl.page.PageList
import ru.DmN.phtx.ppl.utils.Presentation
import javax.imageio.ImageIO
import javax.swing.SwingUtilities

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        SwingUtilities.invokeLater {
            Presentation.setDarkTheme()
            Presentation("Тестовая Презентация", 1000).apply {
                this += PageList(
                    mutableListOf(
                        EPair(
                            EPair(
                                EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img0.jpg"))),
                                EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg")))
                            ),
                            EPair(
                                EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg"))),
                                EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img3.jpg")))
                            )
                        ),
                        EPair(
                            EPair(
                                EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img3.jpg"))),
                                EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg")))
                            ),
                            EPair(
                                EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg"))),
                                EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img0.jpg")))
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