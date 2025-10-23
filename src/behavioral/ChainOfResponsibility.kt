package behavioral

abstract class Handler{
    protected var nextHandler:Handler? = null
    init{

    }
    fun setNext(handler:Handler):Handler {
        this.nextHandler = handler;
        return handler
    }
    protected abstract fun process(url:String)
    fun run(url:String){
        process(url)
        nextHandler?.run(url)
    }
}
class ProtocolHandler:Handler(){
    override fun process(url: String) {
        val index = url.indexOf("://")
        if(index != -1){
            println("PROTOCOL : ${url.substring(0,index)}")
        }else{
            println("NO PROTOCOL")
        }
    }

}
class DomainHandler:Handler() {
    override fun process(url: String) {
        val startIndex = url.indexOf("://")
        val lastIndex  = url.lastIndexOf(":")
        println("DOMAIN")
        if(startIndex == -1){
            if(lastIndex == -1){
                println(url)
            }else{
                println(url.substring(0,lastIndex))
            }
        }else if(startIndex!=lastIndex){
            println(url.substring(startIndex+3,lastIndex))
        }else{
            println(url.substring(startIndex+3))
        }

    }
}
class PortHandler:Handler(){
    override fun process(url: String) {
        val index = url.indexOf(":")
        if (index != -1) {
            println("PORT : ${url.substring(index + 1)}")
        }
    }
}
fun main(){
    val handler1 = ProtocolHandler()
    val handler2 = DomainHandler()
    val handler3 = PortHandler()

    handler1.setNext(handler2).setNext(handler3)
    handler1.run("https://naver.com:8080/index.html")

}