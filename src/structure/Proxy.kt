package structure

interface  IImage{
    fun showImage()
}
class HighResolutionImage(var img:String): IImage{
    init{
        loadImage(img)
    }
    fun loadImage(path:String){
        Thread.sleep(1000)
        img=path
        println("Image loaded from $path")
    }

    override fun showImage() {
        println("Show $img")
    }
}

/**
 * 가상프록시 패턴 구현
 */
class VProxyImage(var path:String):IImage{
    var realImage:IImage?=null
    override fun showImage() {
        // 이때 realImage 변수에 HighResolutionImage를 생성함
        realImage=HighResolutionImage(path)
        realImage?.showImage()
    }
}
fun main(){
    val img1=VProxyImage("img1.jpg")
    val img2=VProxyImage("img2.jpg")
    val img3=VProxyImage("img3.jpg")

    // 가상 프록시 패턴을 사용하지 않고 HighResolutionImage
    img2.showImage()
}