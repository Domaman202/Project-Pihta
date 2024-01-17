package ru.DmN.test.phtx.ppl

import ru.DmN.phtx.ppl.Presentation
import ru.DmN.phtx.ppl.element.*
import ru.DmN.phtx.ppl.page.PageList
import java.awt.Color
import javax.imageio.ImageIO
import javax.swing.SwingUtilities
import javax.swing.UIManager
import javax.swing.plaf.nimbus.NimbusLookAndFeel

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        SwingUtilities.invokeLater {
            darkTheme()
            Presentation("Тестовая Презентация", 1000).apply {
                this += PageList(
                    listOf(
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
                this += PageList(
                    listOf(
                        ETitle("Флаги Росссийской Империи", 36),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg"))),
                        ETitle("Флаги Росссийской Империи", 36),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg"))),
                    )
                )
                //
                this.start()
            }
        }
    }

    private fun darkTheme() {
        UIManager.setLookAndFeel(NimbusLookAndFeel())
        UIManager.put("control", Color(64, 64, 64))
        UIManager.put("info", Color(64, 64, 64))
        UIManager.put("nimbusBase", Color(18, 30, 49))
        UIManager.put("nimbusAlertYellow", Color(248, 187, 0))
        UIManager.put("nimbusDisabledText", Color(128, 128, 128))
        UIManager.put("nimbusFocus", Color(115, 164, 209))
        UIManager.put("nimbusGreen", Color(176, 179, 50))
        UIManager.put("nimbusInfoBlue", Color(66, 139, 221))
        UIManager.put("nimbusLightBackground", Color(18, 30, 49))
        UIManager.put("nimbusOrange", Color(191, 98, 4))
        UIManager.put("nimbusRed", Color(169, 46, 34))
        UIManager.put("nimbusSelectedText", Color(255, 255, 255))
        UIManager.put("nimbusSelectionBackground", Color(104, 93, 156))
        UIManager.put("text", Color(230, 230, 230))
    }
}