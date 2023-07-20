package simpleEngine.shader

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.GL_FALSE
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import simpleEngine.utils.FileUtils

abstract class ShaderProgram(
    vertexFile:String,
    fragmentFile:String
){

    private val vertexShaderID :Int
    private val fragmentShaderID :Int
    private val programID : Int
    private val matrixBuffer = BufferUtils.createFloatBuffer(16)

    init {

        programID = GL30.glCreateProgram()
        vertexShaderID = loadShader(vertexFile,GL30.GL_VERTEX_SHADER)
        fragmentShaderID = loadShader(fragmentFile,GL30.GL_FRAGMENT_SHADER)
        GL30.glAttachShader(programID,vertexShaderID)
        GL30.glAttachShader(programID,fragmentShaderID)
        bindAttributes()
        GL30.glLinkProgram(programID)
        GL30.glValidateProgram(programID)
    }

    abstract fun bindAttributes()

    fun bindAttribute(attribute:Int,variableName:String){
        GL30.glBindAttribLocation(programID,attribute,variableName)
    }

    fun start(){
        GL30.glUseProgram(programID)
    }

    fun stop(){
        GL30.glUseProgram(0)
    }

    fun cleanUp(){
        stop()
        GL30.glDetachShader(programID,vertexShaderID)
        GL30.glDetachShader(programID,fragmentShaderID)
        GL30.glDeleteShader(vertexShaderID)
        GL30.glDeleteShader(fragmentShaderID)
        GL30.glDeleteProgram(programID)
    }

    companion object{
        fun loadShader(filePath:String,type:Int):Int{

            val shaderSource = FileUtils.readFile(filePath)

            //create a shader object
            val shaderId = GL30.glCreateShader(type)

            if (shaderId == 0) throw RuntimeException("Error creating shader type: $type")

            //assign source code to shader created shader object
            GL30.glShaderSource(shaderId,shaderSource)

            //compile the shader source code assigned to a shader object.
            GL30.glCompileShader(shaderId)

            if(GL30.glGetShaderi(shaderId,GL30.GL_COMPILE_STATUS) == GL_FALSE)
                throw RuntimeException("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderId, 1024))

            return shaderId
        }
    }


}