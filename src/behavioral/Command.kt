package behavioral

import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.*


/**
 * 요청 또는 간단한 작업을 객체로 변환하는 행동 디자인 패턴입니다.
 */
abstract class Command internal constructor(editor: Editor) {
    var editor: Editor
    private var backup: String? = null

    init {
        this.editor = editor
    }

    fun backup() {
        backup = editor.textField?.getText()
    }

    fun undo() {
        editor.textField?.text = backup
    }

    abstract fun execute(): Boolean
}
class CopyCommand(editor: Editor) : Command(editor) {
    public override fun execute(): Boolean {
        editor.clipboard = editor.textField!!.getSelectedText()
        return false
    }
}
class PasteCommand(editor: Editor) : Command(editor) {
    public override fun execute(): Boolean {
        if (editor.clipboard == null || editor.clipboard!!.isEmpty()) return false

        backup()
        editor?.textField?.caretPosition?.let { editor.textField?.insert(editor.clipboard, it) }
        return true
    }
}

class CutCommand(editor: Editor) : Command(editor) {
    public override fun execute(): Boolean {
        if (editor.textField!!.getSelectedText().isEmpty()) return false

        backup()
        val source: String = editor.textField!!.getText()
        editor.clipboard = editor.textField!!.getSelectedText()
        editor.textField!!.setText(cutString(source))
        return true
    }

    private fun cutString(source: String): String {
        val start = editor?.textField?.let { source.take(it.selectionStart) }
        val end: String = source.substring(editor?.textField?.getSelectionEnd() ?: throw RuntimeException())
        return start + end
    }
}
class CommandHistory {
    private val history = Stack<Command?>()

    fun push(c: Command?) {
        history.push(c)
    }

    fun pop(): Command? {
        return history.pop()
    }

    val isEmpty: Boolean
        get() = history.isEmpty()
}
class Editor{
    var textField: JTextArea? = null
    var clipboard: String? = null
    private val history = CommandHistory()
    fun init() {
        val frame = JFrame("Text editor (type & use buttons, Luke!)")
        val content = JPanel()
        frame.setContentPane(content)
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        content.setLayout(BoxLayout(content, BoxLayout.Y_AXIS))
        textField = JTextArea()
        textField?.setLineWrap(true)
        content.add(textField)
        val buttons = JPanel(FlowLayout(FlowLayout.CENTER))
        val ctrlC = JButton("Ctrl+C")
        val ctrlX = JButton("Ctrl+X")
        val ctrlV = JButton("Ctrl+V")
        val ctrlZ = JButton("Ctrl+Z")
        val editor = this
        ctrlC.addActionListener { executeCommand(CopyCommand(editor)) }
        ctrlX.addActionListener { executeCommand(CutCommand(editor)) }
        ctrlV.addActionListener { executeCommand(PasteCommand(editor)) }
        ctrlZ.addActionListener { undo() }
        buttons.add(ctrlC)
        buttons.add(ctrlX)
        buttons.add(ctrlV)
        buttons.add(ctrlZ)
        content.add(buttons)
        frame.setSize(450, 200)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }
    /*private fun executeCommand(command: Command) {
        if (command.execute()) {
            history.push(command)
        }
    }

    private fun undo() {
        if (history.isEmpty) return

        val command = history.pop()
        if (command != null) {
            command.undo()
        }
    }*/
    private fun executeCommand(command: Command) {
        if (command.execute()) {
            history.push(command)
        }
    }

    private fun undo() {
        if (history.isEmpty) return

        val command = history.pop()
        if (command != null) {
            command.undo()
        }
    }
}

fun main(){
    Editor().init()
}