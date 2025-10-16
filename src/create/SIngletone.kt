package create

/**
 * 인스턴스가 클래스당 하나만 생성되는 것을 보장하는 것
 * 하지만 SRP (단일책임원칙) 에 어긋남
 *  -  객체를 생성하는 책임과 관리하는 책임을 가지고 있기 떄문에 어긋남\
 *  -  또한 다른 클래스와의 결합도가 높아질 수 있어 OCP 원칙에도 어긋날수 있음
 */
class Database{
    private constructor(){}
    companion object{
        //동시 접근 문제 해결
        @Volatile
        private var instance:Database?=null
        fun getInstance():Database{
            return instance ?: synchronized(this){
                instance ?: Database().also { instance = it }
            }
        }

    }
}
fun main(){
    //생성자로 생성 불가
//    val database = Database()
    val database = Database.getInstance();

}