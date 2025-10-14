package create

/**
 * 팩토리 메서드 패턴과 비슷하지만 추상 팩토리 메서드는 팩토리메서드 패턴을 한단계 더 추상화 한 것이다.
 * 예를 들어 Mac OS, Windows OS에는 버튼이라는 공통된 기능이 있으며 그 버튼에는 공통된 클릭이라는 버튼이 있다.
 * 하지만 두가지의 OS 종류는 엄연히 다르기 떄문에 팩토리 단계를 추상화 하여 두가지의 팩토리 종류를 추상화 한것임
 *  팩토리 메서드로 예를 들자면 팩토리 메서드로 해당 버튼을 생성하려면 Windows OS 또는 Mac OS의 버튼을 생성하는 여러개의 팩토리 메서드들이 생기는 것임
 */
class MacOsButton: Button{
    override fun render() {
        println("MacOSButton")
    }
    override fun onClick() {
    }
}
class WindowsButton: Button{
    override fun render() {
        println("WindowsButton")
    }
    override fun onClick() {
    }
}
interface OSFactory{
    fun createButton():Button
}
class MacOsFactory:OSFactory{
    override fun createButton(): Button {
        return MacOsButton()
    }
}
class WindowsFactory:OSFactory{
    override fun createButton(): Button {
        return WindowsButton()
    }
}
fun main(){
    val factory = MacOsFactory()
    factory.createButton().render()
}