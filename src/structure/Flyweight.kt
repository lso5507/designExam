package structure

import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE

/**
 * 가변성 데이터들
 */
class Tree(
    private val x:Int,
    private val y:Int,
    private val type:TreeType
){
    fun draw(graphic: Graphics){
        type.draw(graphic,x,y)
    }
}

/**
 * 불변성 데이터들
 */
class TreeType(
    private val name:String,
    private val color: Color,
    private val otherTreeData:String
){
    fun draw(graphic: Graphics,x:Int,y:Int){
        graphic.color=Color.BLACK
        graphic.fillRect(x - 1, y,3,5)
        graphic.color=color
        graphic.fillOval(x-5,y-10,10,10);
    }
}
class TreeFactory{
    companion object{
        private val treeTypes = mutableMapOf<String,TreeType>()
        fun getTreeType(name:String,color: Color,otherTreeData:String):TreeType{
            if(treeTypes[name]==null){
                treeTypes[name] = TreeType(name, color, otherTreeData)
            }
            return treeTypes[name]!!
        }
    }
}

class Forest(
    private var trees: ArrayList<Tree>
): JFrame(){
    fun plantTree(x:Int,y:Int,color:Color,otherTreeData:String){
        Tree(x,y, TreeFactory.getTreeType(name,color,otherTreeData))
            .also{
                trees.add(it)
            }
    }
    override fun paint(g: Graphics) {
        for(tree in trees){
            tree.draw(g)
        }
    }
}

private fun random(min: Int, max: Int): Int {
    return min + (Math.random() * ((max - min) + 1)).toInt()
}
fun main(){
    val runtime = Runtime.getRuntime()
    println("Before forest creation: ${(runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)}MB")
    val forest = Forest(ArrayList())
        .also{
            for (i in 0..1000){
                it.plantTree(random(0,1000),random(0,1000),Color.RED,"RED")
                it.plantTree(random(0,1000),random(0,1000),Color.GREEN,"GREEN")
            }
        }
    forest.setSize(1000,1000)
    forest.isVisible=true
    println("After forest creation: ${(runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)}MB")

    
}