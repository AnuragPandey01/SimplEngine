package simpleEngine.scene

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryUtil
import simpleEngine.models.RawModel
import simpleEngine.utils.Texture
import java.nio.FloatBuffer
import java.nio.IntBuffer

class Loader {

    private val vaos = ArrayList<Int>()
    private val vbos = ArrayList<Int>()
    private val textures = ArrayList<Texture>()

    fun loadToVAO(positions: FloatArray,indices: IntArray,textureCoords:FloatArray): RawModel {
        val vaoID = createVAO()
        bindIndicesBuffer(indices)
        storeDataInAttributeList(0,positions,3)
        storeDataInAttributeList(1,textureCoords,2)
        unbindVAO()
        return RawModel(vaoID,indices.size)
    }

    fun loadTexture(fileName:String):Int{
        val texture = Texture.loadTexture("src/main/resources/${fileName}.png")
        textures.add(texture)
        return texture.id
    }

    private fun createVAO():Int {
        val vaoID = GL30.glGenVertexArrays()
        vaos.add(vaoID)
        GL30.glBindVertexArray(vaoID)
        return vaoID
    }

    private fun storeDataInAttributeList(attributeNumber: Int, positions: FloatArray,coordinateSize:Int ) {
        val vboID = GL30.glGenBuffers()
        vbos.add(vboID)
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID)
        val buffer = storeDataInFloatBuffer(positions)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(attributeNumber,coordinateSize, GL30.GL_FLOAT, false, 0, 0)
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0)
    }



    private fun bindIndicesBuffer(indices: IntArray) { val vboID = GL30.glGenBuffers()
        val vboId = GL30.glGenBuffers()
        vbos.add(vboId)
        //GL_ELEMENT_ARRAY_BUFFER tells OpenGL that this is the index buffer
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, vboId)
        val buffer = storeDataInIntBuffer(indices)
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW)
    }

    private fun storeDataInIntBuffer(data:IntArray): IntBuffer {
        val buffer = MemoryUtil.memAllocInt(data.size)
        buffer.put(data).flip()
        return buffer
    }

    private fun storeDataInFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data).flip()
        return buffer
    }


    private fun unbindVAO() {
        GL30.glBindVertexArray(0)
    }

    fun cleanUp(){
        for(vao in vaos){
            GL30.glDeleteVertexArrays(vao)
        }
        for(vbo in vbos){
            GL30.glDeleteBuffers(vbo)
        }
        for(texture in textures){
            texture.delete()
        }
    }

}