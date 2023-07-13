package simpleEngine.core

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import org.lwjgl.system.MemoryUtil


/**
 * Encapsulates all the invocations to GLFW library
 * to create and manage a window.
 *
 *
 *  @param title  title of the window.
 *  @param windowOptions options for the window
 *       width - width of the window
 *       height - height of the window
 *       fps - frames per second(-ve or 0 to enable v-sync)
 *       ups - updates per second
 * */
class Window (
    private val title:String,
    private val windowOptions: WindowOptions = WindowOptions()
){

    private var resizeFunc: () -> Unit = {}
    private var _window:Long? = null
    val window:Long
        get() = _window!!
    private var _width:Int? = null
    val width:Int
        get() = _width!!
    private var _height:Int? = null
    val height:Int
        get() = _height!!

    init{

        //set error callback
        GLFWErrorCallback.createPrint(System.err).set()

        //initialize GLFW
        if(!glfwInit()){
            throw IllegalStateException("Unable to initialize GLFW")
        }

        //set window hints
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        //set window size
        if (windowOptions.width > 0 && windowOptions.height > 0) {
            this._width = windowOptions.width
            this._height = windowOptions.height
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE)
            glfwGetVideoMode(glfwGetPrimaryMonitor())?.let {
                this._width = it.width()
                this._height = it.height()
            }
        }

        //create window
        _window = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        //check if window was created
        if(_window == MemoryUtil.NULL){
            glfwTerminate()
            throw RuntimeException("Failed to create the GLFW window")
        }

        //called when the framebuffer of the specified window is resized.
        glfwSetFramebufferSizeCallback(window){ window, width, height ->
            resized(width, height)
        }

        glfwSetKeyCallback(window){ window, key, scancode, action, mods ->

            //if escape key is pressed then after the key is released, close window
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE){
                glfwSetWindowShouldClose(window, true)
            }
        }

        //make the OpenGL context current
        glfwMakeContextCurrent(window)

        //enable v-sync
        if (windowOptions.fps > 0) {
            glfwSwapInterval(0)
        } else {
            glfwSwapInterval(1)
        }

        //make the OpenGL bindings available for use
        GL.createCapabilities()

        //sets initial screen color
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        //make the window visible
        glfwShowWindow(window)

        val arrWidth = IntArray(1)
        val arrHeight = IntArray(1)
        glfwGetFramebufferSize(window, arrWidth, arrHeight)
        _width = arrWidth[0]
        _height = arrHeight[0]
    }

    private fun resized(width: Int, height: Int) {
        this._width = width
        this._height = height
        try {
            resizeFunc()
        } catch (excp: Exception) {
            println("Error in resizeFunc: ${excp.message}")
        }
    }

    fun update(){
        GL11.glClear(GL_COLOR_BUFFER_BIT and GL_DEPTH_BUFFER_BIT) // clear the framebuffer
        glfwSwapBuffers(window)

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents()
    }

    fun cleanup(){
        glfwDestroyWindow(window)
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

    fun setClearColor(r:Float, g:Float, b:Float, alpha:Float){
        GL11.glClearColor(r, g, b, alpha)
    }

    fun isKeyPressed(keyCode:Int):Boolean{
        return glfwGetKey(window, keyCode) == GLFW_PRESS
    }

    fun shouldClose():Boolean{
        return glfwWindowShouldClose(window)
    }

    fun setResizeCallback(func: () -> Unit){
        resizeFunc = func
    }

    data class WindowOptions(
        var width:Int = 800,
        var height:Int = 600,
        var fps: Int = 0,
        var ups: Int = SimpleEngine.TARGET_UPS
    )



}