package structure

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * 호환되지 않는 객체들을 연결해주는 역할
 * 어댑터는 한 객체의 인터페이스를 다른 객체가 이해할 수 있도록 변환하는 특별한 객체입니다.
 */
//둥근 구멍
class RoundHole(val radius:Double){
    fun fits(peg: RoundPeg) = radius >=peg.radius

}
//둥근 못
open class RoundPeg(open val radius:Double){}
//네모난 못
open class SquarePeg(val width:Double){
}

//네모난 못을 둥근 구멍에 넣기 위한 어댑터 (상속)
class SquarePegAdapter(val squarePeg: SquarePeg): RoundPeg(0.0) {
    //네모난 못을 둥근 홀에 들어 갈 수 있게 변환
    override val radius: Double
        get() = sqrt((squarePeg.width / 2).pow(2) * 2)

}
//네모난 못을 둥근 구멍에 넣기 위한 어댑터 (합성)
class SquarePegAdapter2(val squarePeg: SquarePeg,val roundPeg: RoundPeg) {
    //네모난 못을 둥근 홀에 들어 갈 수 있게 변환
    val radius: Double
        get() = sqrt((squarePeg.width / 2).pow(2) * 2)
}
fun main(){

    // Round fits round, no surprise.
    val hole = RoundHole(5.0)
    val rpeg = RoundPeg(5.0)
    if (hole.fits(rpeg)) {
        println("Round peg r5 fits round hole r5.")
    }

    val smallSqPeg = SquarePeg(2.0)
    val largeSqPeg = SquarePeg(20.0)
    // Adapter solves the problem.
    val smallSqPegAdapter = SquarePegAdapter(smallSqPeg)
    val largeSqPegAdapter = SquarePegAdapter(largeSqPeg)
    if (hole.fits(smallSqPegAdapter)) {
        println("Square peg w2 fits round hole r5.")
    }
    if (!hole.fits(largeSqPegAdapter)!!) {
        println("Square peg w20 does not fit into round hole r5.")
    }
}