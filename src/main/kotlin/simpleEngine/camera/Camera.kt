package simpleEngine.camera

import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import simpleEngine.core.Window

class Camera(
    private val window: Window
){

    private var _position = Vector3f(0f,0f,0f)
    val position get() = _position

    private var _pitch = 0f
    val pitch get() = _pitch

    private var _yaw = 0f
    val yaw get() = _yaw

    private var _roll = 0f
    val roll get() = _roll


    fun move(){
        if(window.isKeyPressed(GLFW.GLFW_KEY_W)){
            _position.z -= 0.02f
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_S)){
            _position.z += 0.02f
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_A)){
            _position.x -= 0.02f
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_D)){
            _position.x += 0.02f
        }
    }




}