package simpleEngine.scene

import org.lwjgl.opengl.GL30
import simpleEngine.models.RawModel
import simpleEngine.models.TexturedModel

class Renderer {

    fun prepare() {
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT)
        GL30.glClearColor(1f, 0f, 0f, 1f)
    }

    fun render(texturedModel: TexturedModel){

        val model = texturedModel.rawModel

        //activate VAO
        GL30.glBindVertexArray(model.vaoId)

        //enable all VAO attributes
        GL30.glEnableVertexAttribArray(0)
        GL30.glEnableVertexAttribArray(1)

        //draw the vertices
        /*GL30.glDrawArrays(GL30.GL_TRIANGLES,0,model.vertexCount)*/
        GL30.glDrawElements(GL30.GL_TRIANGLES,model.vertexCount,GL30.GL_UNSIGNED_INT,0)

        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D,texturedModel.texture.textureID)

        //disable all VAO attributes
        GL30.glDisableVertexAttribArray(0)
        GL30.glDisableVertexAttribArray(1)

        //deactivate VAO
        GL30.glBindVertexArray(0)
    }
}