package simpleEngine.shader

class StaticShader: ShaderProgram(
    vertexFile = "E:\\lwjgl\\simpleEngine\\SimpleEngine\\src\\main\\resources\\vertShader.vert",
    fragmentFile = "E:\\lwjgl\\simpleEngine\\SimpleEngine\\src\\main\\resources\\fargShader.frag"
) {


    override fun bindAttributes() {
        super.bindAttribute(0,"position")
    }


}