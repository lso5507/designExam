package create
/**
 * 복잡한 객체들을 단계별로 생성할 수 있도록 하는 디자인 생성 패턴
 */
interface Builder{
    fun buildStepA():Builder
    fun buildStepB():Builder
    fun buildStepZ():Builder
    fun build():ProductComponent
}

/**
 * 빌더 단계에 대한 일련의 호출을 디렉터를 통해 구현할수 있음
 */
interface ProductComponent{
    var stepAVal:Int?
    var stepBVal:Int?
    var stepZVal:Int?
}

class Director(var builder: Builder) {
    fun changeBuilder(builder:Builder){
        this.builder=builder
    }
    // 해당 함수를 이용해 A~Z의 step 순으로 Product를 생성
    fun make():ProductComponent{
        return builder.buildStepA()
            .buildStepB()
            .buildStepZ()
            .build()
    }
}
data class ProductA(
    override var stepAVal: Int?=null,
    override var stepBVal: Int?=null,
    override var stepZVal: Int?=null
): ProductComponent

data class ProductB(
    override var stepAVal: Int?=null,
    override var stepBVal: Int?=null,
    override var stepZVal: Int?=null
): ProductComponent



class ConcreteBuilderA():Builder,ProductComponent{
    override var stepAVal: Int?=null
    override var stepBVal: Int?=null
    override var stepZVal: Int?=null
    override fun buildStepA(): Builder {
        stepAVal=1
        return this
    }

    override fun buildStepB(): Builder {
        stepBVal=2
        return this
    }

    override fun buildStepZ():Builder {
        stepZVal=3
        return this
    }
    override fun build(): ProductComponent {
        /**
         * 근데 사실 Kotlin은 빌더패턴은 잘 안쓰고 apply 또는 파라미터에 파라미터명을 작성하는 방식으로 진행한다 ㅋ
         */
        return ProductA().apply {
            this.stepAVal=this@ConcreteBuilderA.stepAVal
            this.stepBVal=this@ConcreteBuilderA.stepBVal
            this.stepZVal=this@ConcreteBuilderA.stepZVal
        }
    }

}
class ConcreteBuilderB():Builder,ProductComponent{
    override var stepAVal: Int?=null
    override var stepBVal: Int?=null
    override var stepZVal: Int?=null
    override fun buildStepA(): Builder {
        stepAVal=1
        return this
    }

    override fun buildStepB(): Builder {
        stepBVal=2
        return this
    }

    override fun buildStepZ():Builder {
        stepZVal=3
        return this
    }
    override fun build(): ProductComponent {
        return ProductA().apply {
            this.stepAVal=this@ConcreteBuilderB.stepAVal
            this.stepBVal=this@ConcreteBuilderB.stepBVal
            this.stepZVal=this@ConcreteBuilderB.stepZVal
        }
    }
}
fun main(){
    val builderA = ConcreteBuilderA()
    val productA = builderA.buildStepA()
        .buildStepB()
        .buildStepZ()
        .build()
    println(productA.toString())
    val builderB = ConcreteBuilderB()
    val productB = builderB.buildStepA()
        .buildStepB()
        .buildStepZ()
        .build()
    println(productB)
    //디렉터 이용
    val directBuilderA = ConcreteBuilderA()
    println(Director(directBuilderA).make())
    val directBuilderB = ConcreteBuilderB()
    println(Director(directBuilderB).make())

}