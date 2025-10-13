package create

interface Button{
    fun render()
    fun onCLick()
}
class WindowButton:Button{
    override fun render() {
        println("WindowButton")
    }
    override fun onCLick() {

    }
}
class HTMLButton:Button{
    override fun render() {
        println("HTMLButton")
    }
    override fun onCLick() {
    }
}
abstract class Dialog{
    fun render(){
        val button = this.createButton()
        button.render()
    }
    abstract fun createButton():Button
}
class WindowsDialog:Dialog(){
    override fun createButton(): Button { // return type = 슈퍼타입 Button
        return WindowButton()
    }
}
class WebDialog:Dialog(){
    override fun createButton(): Button {
        return HTMLButton()
    }

}
fun main(){
    val windowsDialog = WindowsDialog()
    windowsDialog.render()
    val webDialog = WebDialog()
    webDialog.render()
}