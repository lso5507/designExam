package structure

/**
 * 객체들을 트리 구조들로 구성한 후, 이러한 구조들과 개별 객체들처럼 작업할 수 있도록 하는 구조 패턴입니다.
 */
interface Graphic{
    fun move(x:Int,y:Int)
    fun draw()
}
open class Dot(
    var x:Int,
    var y:Int):Graphic{
    override fun move(x: Int, y: Int) {
        println("dot move to ($x,$y)")
    }
    override fun draw() {
        println("dot draw")
    }
}
data class Circle(val radius:Int):Dot(0,0){
    constructor(radius: Int, x: Int, y: Int) : this(radius) {
        super.x = x
        super.y = y
    }
    override fun move(x: Int, y: Int) {
        println("Circle move to ($x,$y)")
    }
    override fun draw() {
        println("Circle draw")
    }
}
class CompoundGraphic(val children:MutableList<Graphic>):Graphic{
    fun add(graphic: Graphic){
        children.add(graphic)
    }
    fun remove(graphic: Graphic){
        children.remove(graphic)
    }
    override fun move(x: Int, y: Int) {
        children.forEach { it.move(x,y) }
    }
    override fun draw() {
        children.forEach { it.draw() }
    }
}


class ImageEditor(val graphics: MutableList<Graphic>){
    fun allRemove(){ graphics.clear() }
    fun allDraw(){ graphics.forEach { it.draw() } }
}
fun main(){
    val circle = Circle(10,1,2)
    val dot = Dot(10,20)
    val compoundGraphic = CompoundGraphic(mutableListOf(Circle(30),Dot(30,30)))
    val imageEditor = ImageEditor(mutableListOf(circle,dot,compoundGraphic))
//    imageEditor.allRemove()
    imageEditor.allDraw()
}