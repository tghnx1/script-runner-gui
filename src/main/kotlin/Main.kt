import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.Color
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.swing.*
import javax.swing.text.StyleConstants
import javax.swing.event.DocumentListener
import javax.swing.text.SimpleAttributeSet
import kotlin.concurrent.thread
import kotlin.io.path.createTempDirectory
import kotlin.io.path.writeText

data class ClickableError(
    val start: Int,
    val end: Int,
    val line: Int,
    val col: Int
)

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("Script Runner (Swing)")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(1100, 700)

        // ---------- Editor ----------
        val editor = JTextPane().apply {
            text = """
                // Kotlin script
                println("Hello from script")
            """.trimIndent()
        }

        // Simple Kotlin keyword highlighter (10 keywords)
        val kotlinKeywords = listOf("fun", "val", "var", "if", "else", "for", "while", "return", "class", "object")
        val keywordRegex = Regex("""\b(${kotlinKeywords.joinToString("|")})\b""")

        fun highlightKeywords() {
            SwingUtilities.invokeLater {
                try {
                    val doc = editor.styledDocument
                    val full = doc.getText(0, doc.length)

                    // Reset all to default style first
                    val defaultAttr = SimpleAttributeSet()
                    StyleConstants.setForeground(defaultAttr, Color.BLACK)
                    StyleConstants.setBold(defaultAttr, false)
                    doc.setCharacterAttributes(0, full.length, defaultAttr, true)

                    // Apply keyword style
                    val kwAttr = SimpleAttributeSet()
                    StyleConstants.setForeground(kwAttr, Color.BLUE)
                    StyleConstants.setBold(kwAttr, true)

                    keywordRegex.findAll(full).forEach { match ->
                        doc.setCharacterAttributes(match.range.first, match.value.length, kwAttr, false)
                    }
                } catch (_: Exception) {
                    // ignore highlighting errors
                }
            }
        }

        var highlightScheduled = false
        editor.document.addDocumentListener(object : DocumentListener {
            private fun scheduleHighlight() {
                if (!highlightScheduled) {
                    highlightScheduled = true
                    SwingUtilities.invokeLater {
                        highlightKeywords()
                        highlightScheduled = false
                    }
                }
            }
            override fun insertUpdate(e: javax.swing.event.DocumentEvent) { scheduleHighlight() }
            override fun removeUpdate(e: javax.swing.event.DocumentEvent) { scheduleHighlight() }
            override fun changedUpdate(e: javax.swing.event.DocumentEvent) { scheduleHighlight() }
        })

        // initial highlight
        SwingUtilities.invokeLater { highlightKeywords() }

        // ---------- Output ----------
        val output = JTextPane().apply {
            isEditable = false
        }

        val splitPane = JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            JScrollPane(editor),
            JScrollPane(output)
        ).apply { resizeWeight = 0.6 }

        // ---------- Controls ----------
        val runBtn = JButton("Run")
        val stopBtn = JButton("Stop").apply { isEnabled = false }
        val languageCombo = JComboBox(arrayOf("Kotlin", "Swift")).apply { selectedIndex = 0 }
        val statusLabel = JLabel("Idle")
        val exitLabel = JLabel("Exit: —")

        val controls = JPanel(FlowLayout(FlowLayout.LEFT)).apply {
            add(runBtn)
            add(stopBtn)
            add(JLabel("Language:"))
            add(languageCombo)
            add(JSeparator(SwingConstants.VERTICAL))
            add(statusLabel)
            add(JSeparator(SwingConstants.VERTICAL))
            add(exitLabel)
        }

        frame.add(controls, BorderLayout.NORTH)
        frame.add(splitPane, BorderLayout.CENTER)
        frame.isVisible = true

        // ---------- State ----------
        var process: Process? = null
        val clickableErrors = mutableListOf<ClickableError>()

        // ---------- Helpers ----------
        fun setRunning(running: Boolean) {
            SwingUtilities.invokeLater {
                runBtn.isEnabled = !running
                stopBtn.isEnabled = running
                statusLabel.text = if (running) "Running…" else "Idle"
            }
        }

        fun jumpToEditor(line: Int, col: Int) {
            try {
                // For JTextPane, compute offset from document root elements
                val l = (line - 1).coerceAtLeast(0)
                val c = (col - 1).coerceAtLeast(0)
                val root = editor.document.getDefaultRootElement()
                val lineIndex = l.coerceAtMost(root.elementCount - 1)
                val elem = root.getElement(lineIndex)
                val offset = elem.startOffset + c.coerceAtMost(elem.endOffset - elem.startOffset - 1)
                editor.requestFocus()
                editor.caretPosition = offset
            } catch (_: Exception) {}
        }

        // match either .kts or .swift error location formats like "script.kts:2:1:"
        val errorRegex = Regex(""".*?(\.kts|\.swift):(\d+):(\d+):""")

        fun appendOutput(line: String) {
            SwingUtilities.invokeLater {
                val doc = output.styledDocument
                val start = doc.length

                doc.insertString(start, line + "\n", null)

                val match = errorRegex.find(line)
                if (match != null) {
                    val lineNo = match.groupValues[2].toInt()
                    val colNo = match.groupValues[3].toInt()

                    val style = output.addStyle("err-$start", null)
                    StyleConstants.setForeground(style, Color(0x1a5fd0))
                    StyleConstants.setUnderline(style, true)

                    doc.setCharacterAttributes(start, line.length, style, false)

                    clickableErrors += ClickableError(
                        start = start,
                        end = start + line.length,
                        line = lineNo,
                        col = colNo
                    )
                }

                output.caretPosition = doc.length
            }
        }

        fun findKotlinc(): String {
            val home = System.getProperty("user.home")
            val cache = File(home, ".gradle/caches")

            if (cache.exists()) {
                cache.walkTopDown().forEach { f ->
                    if (f.isDirectory && f.name == "kotlinc") {
                        listOf(
                            File(f, "bin/kotlinc"),
                            File(f, "bin/kotlinc-jvm")
                        ).forEach { exe ->
                            if (exe.exists() && exe.isFile) {
                                if (!exe.canExecute()) exe.setExecutable(true)
                                if (exe.canExecute()) return exe.absolutePath
                            }
                        }
                    }
                }
            }

            val path = System.getenv("PATH") ?: ""
            path.split(File.pathSeparator).forEach { dir ->
                val exe = File(dir, "kotlinc")
                if (exe.exists() && exe.isFile) {
                    if (!exe.canExecute()) exe.setExecutable(true)
                    if (exe.canExecute()) return exe.absolutePath
                }
            }

            throw IllegalStateException("kotlinc executable not found")
        }

        // ---------- Single MouseListener ----------
        output.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val pos = output.viewToModel2D(e.point)
                clickableErrors.firstOrNull { pos in it.start..it.end }?.let {
                    jumpToEditor(it.line, it.col)
                }
            }
        })

        // ---------- Actions ----------
        runBtn.addActionListener {
            output.text = ""
            clickableErrors.clear()
            exitLabel.text = "Exit: —"
            exitLabel.foreground = Color.BLACK
            setRunning(true)

            thread(name = "script-runner") {
                try {
                    val selectedLang = languageCombo.selectedItem as String

                    val tmpDir = createTempDirectory("script-runner-")
                    val ext = if (selectedLang == "Swift") "swift" else "kts"
                    val scriptFile = tmpDir.resolve("script.$ext")
                    scriptFile.writeText(editor.text)

                    val pb = when (selectedLang) {
                        "Swift" -> ProcessBuilder("/usr/bin/env", "swift", scriptFile.toAbsolutePath().toString())
                        else -> {
                            // Kotlin: prefer discovered kotlinc, but we'll fallback to /usr/bin/env kotlinc if needed
                            val kotlinc = try { findKotlinc() } catch (_: Exception) { null }
                            if (kotlinc != null) ProcessBuilder(kotlinc, "-script", scriptFile.toAbsolutePath().toString())
                            else ProcessBuilder("/usr/bin/env", "kotlinc", "-script", scriptFile.toAbsolutePath().toString())
                        }
                    }

                    pb.directory(tmpDir.toFile())
                    var p: Process
                    try {
                        p = pb.start()
                    } catch (io: Exception) {
                        // If permission denied on direct kotlinc, try env fallback
                        if (io.message?.contains("Permission denied") == true && (languageCombo.selectedItem as String) == "Kotlin") {
                            val fallback = ProcessBuilder("/usr/bin/env", "kotlinc", "-script", scriptFile.toAbsolutePath().toString())
                            fallback.directory(tmpDir.toFile())
                            p = fallback.start()
                        } else throw io
                    }
                    process = p

                    thread {
                        BufferedReader(InputStreamReader(p.inputStream)).useLines {
                            it.forEach(::appendOutput)
                        }
                    }

                    thread {
                        BufferedReader(InputStreamReader(p.errorStream)).useLines {
                            it.forEach(::appendOutput)
                        }
                    }

                    val code = p.waitFor()
                    SwingUtilities.invokeLater {
                        exitLabel.text = "Exit: $code"
                        exitLabel.foreground = if (code != 0) Color.RED else Color.BLACK
                    }
                } catch (e: Exception) {
                    appendOutput("Failed to run: ${e.message}")
                } finally {
                    process = null
                    setRunning(false)
                }
            }
        }

        stopBtn.addActionListener {
            process?.let {
                it.destroy()
                if (it.isAlive) it.destroyForcibly()
                appendOutput("Process stopped")
            }
        }

        frame.addWindowListener(object : java.awt.event.WindowAdapter() {
            override fun windowClosing(e: java.awt.event.WindowEvent) {
                process?.destroyForcibly()
            }
        })
    }
}
