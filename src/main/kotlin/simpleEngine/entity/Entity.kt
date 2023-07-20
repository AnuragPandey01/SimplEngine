package simpleEngine.entity

import org.joml.Vector3f
import simpleEngine.models.TexturedModel

data class Entity(
    val model: TexturedModel,
    val position: Vector3f,
    val rotation: Vector3f,
    val scale: Float
) {

    fun changePosition(dx: Float, dy: Float, dz: Float) {
        position.add(dx, dy, dz)
    }

    fun changeRotation(dx: Float, dy: Float, dz: Float) {
        rotation.add(dx, dy, dz)
    }

}