package create

/**
 * 프로토타입은 코드를 그들의 클래스들에 의존시키지 않고 기존 객체들을 복사할 수 있도록 하는 생성 디자인 패턴입니다.
 *  - 복사하려는 객체의 일부 필드가 private이라면 복사불가
 */
abstract class Shape{
    var x:Int = 0
    var y:Int = 0
    var color:String?=null
    constructor(){}
    constructor(source:Shape){
        this.x = source.x
        this.y = source.y
        this.color = source.color
    }
    abstract fun clone():Shape
}

/**
 * 해당 레지스트리를 이용해 미리 생성해둔 Shape 클래스들을 복사할 수 있다. 또는 클론
 */
class ProtoTypeRegistry{
    val items:MutableMap<String,Shape> = mutableMapOf()
    init{
        val circle = Circle();
        circle.modifyRadius()
        circle.x=1
        circle.y=2
        circle.color="black"
        items.put("blackCircle",circle)
        val rectangle = Rectangle()
        rectangle.x=3
        rectangle.y=4
        rectangle.width=5
        rectangle.height=6
        rectangle.color="red"
        items.put("redRectangle",rectangle)
    }
    fun put(key:String,item:Shape){
        items[key] = item
    }
    fun get(key:String):Shape{
        return items[key]?.clone()?:throw IllegalArgumentException("No item for key $key")
    }
}
/**
 * radius는 비공개 필드이므로 복사하기 어려움
 */
class Circle:Shape{
    private var radius:Int = 0
    constructor(){}
    constructor(source:Circle):super(source){
        this.radius = source.radius
    }
    override fun clone(): Shape {
        return Circle(this)
    }
    fun modifyRadius(){
        //외부에서 radius를 받아왔다는 전제
        this.radius=10;
    }
    override fun toString(): String {
        return  "circle.x ${this.x} circle.y ${this.y} circle.color ${this.color} circle.radius ${this.radius}"
    }
}
class Rectangle:Shape{
    var width:Int = 0
    var height:Int = 0
    constructor(){}
    constructor(source:Rectangle):super(source){
        this.width=source.width
        this.height=source.height
    }
    override fun clone(): Shape {
        return Rectangle(this)
    }
    override fun toString(): String {
        return  "Rectangle.x ${this.x} Rectangle.y ${this.y} Rectangle.color ${this.color} Rectangle.width ${this.width}, Rectangle.height ${this.height}"
    }
}
fun main(){
    val circle = Circle()
    circle.x=1
    circle.y=10
    circle.modifyRadius()
    val anotherCircle = circle.clone()
    println("circle == anotherCircle::${circle==anotherCircle}")
    println("circle:$circle")
    println("anotherCircle:$anotherCircle")

    val rectangle = Rectangle()
    rectangle.x=1
    rectangle.y=10
    rectangle.width=20
    rectangle.height=30
    val anotherRectangle = rectangle.clone()
    println("rectangle == anotherRectangle::${rectangle==anotherRectangle}")

    //레지스트릐 이용
    val registry = ProtoTypeRegistry()
    println(registry.get("blackCircle"))
    //clone 되므로 다름
    println(registry.get("blackCircle") == registry.get("blackCircle"))
    registry.put("redCircle",Circle().apply{
        this.x=2
        this.y=3
        this.modifyRadius()
    })
    println(registry.get("redCircle"))

}