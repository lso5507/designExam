package create

/**
 *
 * 팩토리 메서드(Factory Method) 패턴은 객체 생성 방식을 하위 클래스에 위임
 * 그러므로 Dialog.render() 에서 createButton을 호출하여 하위클래스에서 해당 createButton 함수를
 * 작성할수있도록 함
 */
interface Button{
    fun render()
    fun onClick()
}
class WindowButtonA:Button{
    override fun render() {
        println("WindowButtonA")
    }
    override fun onClick() {

    }
}
class WindowButtonB:Button{
    override fun render() {
        println("WindowButtonB")
    }
    override fun onClick() {
    }
}
abstract class WindowFactory{
    fun render(){
        val button = createButton()
        button.render()
    }
    abstract fun createButton():Button
}
class ConcreteFactoryA:WindowFactory(){
    override fun createButton(): Button { // return type = 슈퍼타입 Button
        return WindowButtonA()
    }
}
class ConcreteFactoryB:WindowFactory(){
    override fun createButton(): Button { // return type = 슈퍼타입 Button
        return WindowButtonB()
    }
}
fun main(){
    val factoryA = ConcreteFactoryA()
    factoryA.render()
    val factoryB = ConcreteFactoryB()
    factoryB.render()
}