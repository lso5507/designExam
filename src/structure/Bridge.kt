package structure
/**
 *  큰 클래스 또는 밀접하게 관련된 클래스들의 집합을 두 개의 개별 계층구조
 *  (추상화 및 구현)로 나눈 후 각각 독립적으로 개발할 수 있도록 하는 구조 디자인 패턴입니다.
 *  추상화 객체는 앱의 드러나는 모습을 제어하고 연결된 구현 객체(Device)에 실제 작업들을 위임합니다
 */
class Remote(val device:Device){
    fun togglePower(){
        if(device.isEnabled()) device.disable() else device.enable(true)
    }

}
interface Device{
    fun isEnabled():Boolean
    fun enable(enabled:Boolean)
    fun disable()
    fun getVolume():Int
    fun setVolume(volume:Int)
    fun getChannel()
    fun setChannel(channel:Int)
}
class TVDevice:Device{
    override fun isEnabled(): Boolean {
        return false
    }
    override fun enable(enabled: Boolean) {
    }
    override fun disable() {
        println(this.javaClass.simpleName+" is disabled")
    }
    override fun getVolume(): Int {
        return 0
    }
    override fun setVolume(volume: Int) {
    }
    override fun getChannel() {}
    override fun setChannel(channel: Int) {}
}
class RadioDevice:Device{
    override fun isEnabled(): Boolean {
        return false
    }
    override fun enable(enabled: Boolean) {
    }
    override fun disable() {
        println(this.javaClass.simpleName+" is disabled")

    }
    override fun getVolume(): Int {
        return 0
    }
    override fun setVolume(volume: Int) {
    }
    override fun getChannel() {}
    override fun setChannel(channel: Int) {}
}

fun main(){
    val tvDevice = TVDevice()
    Remote(tvDevice).togglePower()
    val radioDevice = RadioDevice()
    Remote(radioDevice).togglePower()
}