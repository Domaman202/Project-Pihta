package ru.DmN.test.phtx.ppl

import ru.DmN.phtx.ppl.element.EImage
import ru.DmN.phtx.ppl.element.EText
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
                        ETitle("Крещение Руси", 36),
                        EText("""
                            1. Крещение Руси произошло в IX веке благодаря князю Владимиру.
                            2. По легенде, князь Владимир сам принял крещение в Херсонесе.
                            3. Крещение Руси стало важным событием для формирования русской культуры.
                            4. После крещения, христианство было принято всеми слоями населения.
                            5. Появление православной церкви привнесло новые ценности и обряды.
                        """.trimIndent(), 24),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img0.jpg"))),
                        EText("""
                            6. В результате крещения Русь стала частью европейской христианской цивилизации.
                            7. Крещение Руси способствовало развитию письменности и образования.
                            8. Церковь стала центром культуры и искусства на Руси.
                            9. Важное значение крещения Руси в истории России неоспоримо.
                            10. Праздник Крещения до сих пор является одним из важнейших в православной традиции.
                        """.trimIndent(), 24)
                    )
                )
                this += PageList().apply {
                    this += ETitle("Флаги Росссийской Империи", 36)
                    this += EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg")))
                    this += ETitle("Флаги Росссийской Империи", 36)
                    this += EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg")))
                }
                //
                this.show()
            }
        }
    }
}