package simpleEngine.scene

import org.lwjgl.opengl.GL30
import simpleEngine.entity.Entity
import simpleEngine.models.RawModel
import simpleEngine.models.TexturedModel
import simpleEngine.shader.StaticShader
import simpleEngine.utils.SimpleMaths

class Renderer {

    fun prepare() {
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT)
        GL30.glClearColor(1f, 0f, 0f, 1f)
    }

    fun render(entity: Entity, shader: StaticShader) {

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