package structure

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.*
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream
import java.util.zip.InflaterInputStream

/**
 * 데코레이터는 객체들을 새로운 행동들을 포함한 특수 래퍼 객체들 내에 넣어서 위 행동들을 해당 객체들에 연결시키는 구조적 디자인 패턴입니다.
 */

interface DataSource{
    fun writeData(data:String)
    fun readData():String
}
class FileDataSource(val name:String): DataSource {
    override fun writeData(data: String) {
        val file = File(name)
        file.writeText(data)
    }

    override fun readData(): String {
        return File(name).readText()
    }

}
abstract class DataSourceDecorator(private val wrappee: DataSource): DataSource{
    override fun writeData(data: String) {
        wrappee.writeData(data)
    }
    override fun readData(): String {
        return wrappee.readData()
    }
}
class EncryptDecorator(wrappee: DataSource): DataSourceDecorator(wrappee){
    override fun writeData(data: String) {
        super.writeData(encode(data))
    }
    override fun readData(): String {
        return decode(super.readData())
    }
    private fun encode(data: String): String {
        val result = data.toByteArray()
        for (i in result.indices) {
            result[i] = (result[i] + 1.toByte()).toByte()
        }
        return Base64.getEncoder().encodeToString(result)
    }

    private fun decode(data: String?): String {
        val result = Base64.getDecoder().decode(data)
        for (i in result.indices) {
            result[i] = (result[i] - 1.toByte()).toByte()
        }
        return String(result)
    }
}
class CompressDecorator(wrappee: DataSource,val compLevel:Int = 6): DataSourceDecorator(wrappee){
    override fun writeData(data: String) {
        super.writeData(compress(data) ?: data)
    }
    override fun readData(): String {
        return decompress(super.readData())
    }
    private fun compress(stringData:String): String? {
        val data = stringData.toByteArray()
        val byteArrayOutputStream = ByteArrayOutputStream(512)
        val dos = DeflaterOutputStream(byteArrayOutputStream, Deflater(compLevel))
        dos.write(data)
        dos.close()
        byteArrayOutputStream.close()
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
    }
    fun decompress(stringData:String): String {
        val decode = Base64.getDecoder().decode(stringData)
        val `in`: InputStream = ByteArrayInputStream(decode)
        val iin = InflaterInputStream(`in`)
        val bout = ByteArrayOutputStream(512)
        var b: Int
        while ((iin.read().also { b = it }) != -1) {
            bout.write(b)
        }
        `in`.close()
        iin.close()
        bout.close()
        return String(bout.toByteArray())
    }
}

fun main(){
    val salaryRecords = "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"

    /**
     * 데코레이터 패턴
     * 파일을 읽고, 암호화 한다음, 압축
     *
     */
    val encoded: DataSourceDecorator = CompressDecorator(
        EncryptDecorator(
            FileDataSource("out/OutputDemo.txt")
        )
    )

    /**
     * 데코레이터 패턴 장점으로 원하는대로 객체를 넣어서 래핑할수 있음
     */
    val encoded2: DataSourceDecorator = CompressDecorator(
        CompressDecorator(
            EncryptDecorator(
                FileDataSource("out/OutputDemo.txt")
            )
        )
    )
    encoded.writeData(salaryRecords)
    val plain: DataSource = FileDataSource("out/OutputDemo.txt")

    println("- Input ----------------")
    println(salaryRecords)
    println("- Encoded --------------")
    println(plain.readData())
    println("- Decoded --------------")
    println(encoded.readData())
}