package simpleEngine.scene

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import simpleEngine.camera.Camera
import simpleEngine.entity.Entity
import simpleEngine.models.RawModel
import simpleEngine.models.TexturedModel
import simpleEngine.shader.StaticShader
import simpleEngine.utils.SimpleMaths

class Renderer(
    private val shader: StaticShader,
    private val camera: Camera
) {

    init {
        shader.start()
        shader.createUniform("pMatrix")
        shader.setUniform("pMatrix",SimpleMaths.createProjectionMatrix())
        shader.stop()
    }

    fun render(entity: Entity) {

        val texturedModel = entity.model
        val model = texturedModel.rawModel

        //activate VAO
        GL30.glBindVertexArray(model.vaoId)

        //enable all VAO attributes
        GL30.glEnableVertexAttribArray(0)
        GL30.glEnableVertexAttribArray(1)

        shader.createUniform("tMatrix")
        val matrix =  SimpleMaths.createTransformationMatrix(
            entity.position,
            entity.rotation.x,
            entity.rotation.y,
            entity.rotation.z,
            entity.scale
        )
        shader.setUniform("tMatrix",matrix)
        shader.createUniform("vMatrix")
        shader.setUniform("vMatrix",SimpleMaths.createViewMatrix(camera))

        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D,texturedModel.texture.textureID)

        //draw the vertices
        /*GL30.glDrawArrays(GL30.GL_TRIANGLES,0,model.vertexCount)*/
        GL30.glDrawElements(GL30.GL_TRIANGLES,model.vertexCount,GL30.GL_UNSIGNED_INT,0)

        //disable all VAO attributes
        GL30.glDisableVertexAttribArray(0)
        GL30.glDisableVertexAttribArray(1)

        //deactivate VAO
        GL30.glBindVertexArray(0)
    }
}