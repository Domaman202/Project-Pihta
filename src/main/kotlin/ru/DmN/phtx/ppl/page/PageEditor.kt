//package ru.DmN.phtx.ppl.page
//
//import ru.DmN.phtx.ppl.utils.Presentation
//import java.awt.*
//import java.awt.event.ComponentAdapter
//import java.awt.event.ComponentEvent
//import java.awt.event.KeyAdapter
//import java.awt.event.KeyEvent
//import java.io.File
//import java.io.FileInputStream
//import java.io.FileOutputStream
//import javax.swing.JComponent
//import javax.swing.JList
//import javax.swing.JPanel
//import javax.swing.JScrollPane
//import javax.swing.JTextArea
//import javax.swing.event.DocumentEvent
//import javax.swing.event.DocumentListener
//
//class PageEditor(val module: String) : Page() {
//    private var file = "$module/module.pht"
//    override lateinit var component: Instance
//    lateinit var presentation: Presentation
//
//    override fun onShow(presentation: Presentation) {
//        this.presentation = presentation
//        component = Instance()
//        component.load()
//        super.onShow(presentation)
//    }
//
//    override fun onHide(presentation: Presentation) {
//        component.save()
//        super.onHide(presentation)
//    }
//
//    inner class Instance : JPanel() {
//        private val textArea = JTextArea(27, 40).apply {
//            this.font = Font("TimesRoman", Font.BOLD + Font.ITALIC, 26)
//            this.tabSize = 1
//            this.lineWrap = true
//            this.wrapStyleWord = true
//            this.addKeyListener(object : KeyAdapter() {
//                override fun keyPressed(e: KeyEvent?) {
//                    when (e!!.keyCode) {
//                        KeyEvent.VK_LEFT -> presentation.prevPage()
//                        KeyEvent.VK_RIGHT -> presentation.nextPage()
//                    }
//                }
//            })
//        }
//
//        init {
//            val list = filesList(module)
//            add(JList(list).apply {
//                this.font = Font("TimesRoman", Font.BOLD + Font.ITALIC, 26)
//                this.addListSelectionListener {
//                    save()
//                    file = "$module/${list[this.selectedIndex]}"
//                    load()
//                }
//            })
//            textArea.document.addDocumentListener(object : DocumentListener {
//                override fun insertUpdate(e: DocumentEvent?) = save()
//                override fun removeUpdate(e: DocumentEvent?) = save()
//                override fun changedUpdate(e: DocumentEvent?) = save()
//            })
//            add(JScrollPane(textArea), BorderLayout.CENTER)
//        }
//
//        override fun paint(g: Graphics?) {
//            g as Graphics2D
//            g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blackout)
//            super.paint(g)
//        }
//
//        fun load() {
//            FileInputStream(file).use { textArea.text = String(it.readBytes()) }
//        }
//
//        fun save() {
//            FileOutputStream(file).use { it.write(textArea.text.toByteArray()) }
//        }
//
//        private fun filesList(file: String): Array<String> {
//            val list = ArrayList<String>()
//            File(file).list()?.filter { it.endsWith("pht") || it.endsWith("txt") }?.forEach { filesList(it, list) }
//            return list.toTypedArray()
//        }
//
//        private fun filesList(file: String, list: MutableList<String>) {
//            list += file
//            File(file).list()?.filter { it.endsWith("pht") || it.endsWith("txt") }?.forEach { filesList("$file/$it", list) }
//        }
//    }
//}