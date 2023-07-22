package simpleEngine.utils

import org.joml.Matrix4f
import org.joml.Vector3f
import simpleEngine.camera.Camera

object SimpleMaths {

    fun createTransformationMatrix(
        translation:Vector3f,
        rx:Float,
        ry:Float,
        rz:Float,
        scale:Float
    ):Matrix4f{
        val matrix = Matrix4f()
        matrix.identity()
        matrix.translate(translation,matrix)
        matrix.rotate(Math.toRadians(rx.toDouble()).toFloat(),Vector3f(1f,0f,0f),matrix)
        matrix.rotate(Math.toRadians(ry.toDouble()).toFloat(),Vector3f(0f,1f,0f),matrix)
        matrix.rotate(Math.toRadians(rz.toDouble()).toFloat(),Vector3f(0f,0f,1f),matrix)
        matrix.scale(scale,matrix)

        return matrix
    }

    fun createProjectionMatrix():Matrix4f{
        val matrix = Matrix4f()
        matrix.setPerspective(Math.toRadians(70.0).toFloat(), 1.0f, 0.1f, 1000f)
        return matrix
    }

    fun createViewMatrix(camera: Camera):Matrix4f{
        val matrix = Matrix4f()
        matrix.rotate(Math.toRadians(camera.pitch.toDouble()).toFloat(),Vector3f(1f,0f,0f),matrix)
        matrix.rotate(Math.toRadians(camera.yaw.toDouble()).toFloat(),Vector3f(0f,1f,0f),matrix)
        matrix.rotate(Math.toRadians(camera.roll.toDouble()).toFloat(),Vector3f(0f,0f,1f),matrix)
        val negativeCameraPos = Vector3f(-camera.position.x,-camera.position.y,-camera.position.z)
        matrix.translate(negativeCameraPos,matrix)
        return matrix
    }
}