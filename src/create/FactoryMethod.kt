package create

/**
 *
 * 팩토리 메서드(Factory Method) 패턴은 객체 생성 방식을 하위 클래스에 위임
 * 그러므로 Dialog.render() 에서 createButton을 호출하여 하위클래스에서 해당 createButton 함수를
 * 작성할수있도록 함
 */
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
        val button = createButton()
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