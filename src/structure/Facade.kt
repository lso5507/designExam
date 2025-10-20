package structure

import java.io.File

/**
 * 라이브러리, 프레임워크, 다른 클래스들의 복잡한 집합에 대한 단순화 된 인터페이스를 제공하는 패턴
 */
class VideoFile(
    private val naem:String,
    private val codecType:String
){
    constructor(name:String):this(name,name.substringAfterLast("."+1))
}
interface Codec{
    val type:String
}
class Mpeg4:Codec{
    override val type: String
        get() = "mpeg4"
}
class Avi:Codec{
    override val type: String
        get() = "avi"
}
class CodecFactory{
    companion object{
        fun extract(type:String):Codec{
            return when(type){
                "mpeg4"->Mpeg4()
                "avi"->Avi()
                else->throw IllegalArgumentException("No such codec")
            }
        }
    }
}
class BitrateReader{
    companion object{
        fun read(file: VideoFile,codec: Codec): VideoFile{
            println("BitrateReader: reading file...")
            return file
        }
        fun convert(buffer: VideoFile,codec: Codec): VideoFile{
            println("BitrateReader: converting file...")
            return buffer
        }
    }
}
class AudioMixer {
    fun fix(result: VideoFile): File {
        println("AudioMixer: fixing audio...")
        return File("tmp")
    }
}
class VideoConversionFacade{
    /**
     * 퍼샤드패턴 쓰면 해당 메소드바디에 모든 기능 구현부가 모임
     *  이게 단점같음
     */
    fun convertVideo(fileName:String,format:String){
        val videoFile = VideoFile(fileName)
        val codec = CodecFactory.extract(format)
        val destCodec = when (format) {
            "mpeg4" -> {
                Mpeg4()
            }

            "avi" -> {
                Avi()
            }

            else -> {
                throw IllegalArgumentException("No such format")
            }
        }
        BitrateReader.read(videoFile,codec)
        BitrateReader.convert(videoFile,destCodec)
        val result = AudioMixer().fix(videoFile)
        println("VideoConversionFacade: video saved to $result")

    }
}
fun main(){
    VideoConversionFacade().convertVideo("test.mp4","avi")
}