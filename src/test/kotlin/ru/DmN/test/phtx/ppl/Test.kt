package ru.DmN.test.phtx.ppl

import ru.DmN.phtx.ppl.Presentation
import ru.DmN.phtx.ppl.page.*
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
                this += TitlePage("Слава России!", 127)
                this += TitleTextPage(
                    "Крещение Руси",
                    """
                        1. В 988 году князь Владимир Святославич крестил Русь.
                        2. Событие произошло по приглашению византийского императора Василия II.
                        3. Крещение стало ключевым в истории Древнерусского государства.
                        4. Этот шаг укрепил связи с Византией и положил начало христианской традиции.
                        5. Христианство оказало влияние на культуру, образование и политику Руси.
                    """.trimIndent(),
                    64,
                    32,
                    true
                )
                this += TitleTextPage(
                    "Крещение Руси",
                    """
                        1. В 988 году князь Владимир Святославич крестил Русь.
                        2. Событие произошло по приглашению византийского императора Василия II.
                        3. Крещение стало ключевым в истории Древнерусского государства.
                        4. Этот шаг укрепил связи с Византией и положил начало христианской традиции.
                        5. Христианство оказало влияние на культуру, образование и политику Руси.
                    """.trimIndent(),
                    64,
                    32,
                    false
                )
                this += TitleImagePage("Крещение Руси", 36, ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img0.jpeg")))
                this += TitleImageTextPage(
                    "Крещение Руси",
                    ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img0.jpeg")),
                    """
                        1. В 988 году князь Владимир Святославич крестил Русь.
                        2. Событие произошло по приглашению византийского императора Василия II.
                        3. Крещение стало ключевым в истории Древнерусского государства.
                        4. Этот шаг укрепил связи с Византией и положил начало христианской традиции.
                        5. Христианство оказало влияние на культуру, образование и политику Руси.
                    """.trimIndent(),
                    36,
                    32,
                    true
                )
                this += TitleImageTextPage(
                    "Крещение Руси",
                    ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img0.jpeg")),
                    """
                        1. В 988 году князь Владимир Святославич крестил Русь.
                        2. Событие произошло по приглашению византийского императора Василия II.
                        3. Крещение стало ключевым в истории Древнерусского государства.
                        4. Этот шаг укрепил связи с Византией и положил начало христианской традиции.
                        5. Христианство оказало влияние на культуру, образование и политику Руси.
                    """.trimIndent(),
                    36,
                    32,
                    false
                )
                this += TitleImageImagePage(
                    "Флаги Российской Империи",
                    36,
                    ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg")),
                    ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg"))
                )
                this += ImagePage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg")))
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