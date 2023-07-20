package simpleEngine.shader

class StaticShader: ShaderProgram(
    vertexFile = "src/main/resources/vertShader.vert",
    fragmentFile = "src/main/resources/fragShader.frag"
) {
    override fun bindAttributes() {
        super.bindAttribute(0, "position")
        super.bindAttribute(1, "textureCoords")
    }

}