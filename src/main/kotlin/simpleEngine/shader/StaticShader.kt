package simpleEngine.shader

import org.joml.Matrix4f

class StaticShader: ShaderProgram(
    vertexFile = "src/main/resources/vertShader.vert",
    fragmentFile = "src/main/resources/fragShader.frag"
) {

    private val uniformLocations: MutableMap<String, Int> = HashMap()

    override fun bindAttributes() {
        super.bindAttribute(0, "position")
        super.bindAttribute(1, "textureCoords")
    }

    fun createUniform(name:String){
        val location = super.getUniformLocation(name)
        if(location < 0)
            throw RuntimeException("Could not find uniform [$name] in shader program")
        uniformLocations[name] = location
    }

   fun setUniform(name:String,value:Matrix4f){
       super.loadMatrix(uniformLocations[name]!!,value)
   }

}