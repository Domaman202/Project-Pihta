package ru.DmN.phtx.spt.test

import ru.DmN.phtx.spt.Window
import ru.DmN.phtx.spt.components.Image
import ru.DmN.phtx.spt.components.Text
import ru.DmN.phtx.spt.components.Title
import javax.imageio.ImageIO

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        test()
    }

    fun test() {
        val window = Window("Крещение Матушки Руси", 640, 480)
        window.newPage().run {
            jpanel.layout = null
            add(Title("Крещение Руси"))
            add(Image(ImageIO.read(Main::class.java.getResourceAsStream("/phtx/spt/img0.jpg")), 20, 50, 280, 340))
            add(Image(ImageIO.read(Main::class.java.getResourceAsStream("/phtx/spt/img3.jpg")), 340, 50, 270, 340))
            add(Text("Рис. 1", 150, 420, 14))
            add(Text("Рис. 2", 450, 420, 14))
        }
        window.showNext()
    }
}