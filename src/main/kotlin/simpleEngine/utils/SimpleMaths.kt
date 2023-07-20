package simpleEngine.utils

import org.joml.Matrix4f
import org.joml.Vector3f

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
}