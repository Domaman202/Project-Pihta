package ru.DmN.test.phtx.ppl

import ru.DmN.phtx.ppl.Presentation
import ru.DmN.phtx.ppl.element.*
import ru.DmN.phtx.ppl.page.PageList
import ru.DmN.phtx.ppl.page.PagePair
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
            Presentation("Тестовая Презентация").apply {
                this += PagePair(
                    EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg"))),
                    EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg")))
                )
                this += PageList(
                    listOf(
                        EPair(
                            ETitle("Слава России!", 36),
                            ETitle("Слава Гагаузии!", 36),
                        ),
                        EPair(
                            EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg"))),
                            EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img3.jpg"))),
                            Element.SizeType.DYNAMIC
                        )
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