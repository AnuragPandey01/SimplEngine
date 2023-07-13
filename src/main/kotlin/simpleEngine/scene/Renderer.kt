package simpleEngine.scene

import org.lwjgl.opengl.GL30
import simpleEngine.scene.RawModel

class Renderer {

    fun prepare() {
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT)
        GL30.glClearColor(1f, 0f, 0f, 1f)
    }

    fun render(model: RawModel){
        //activate VAO
        GL30.glBindVertexArray(model.vaoId)
        //enable VAO of index 0
        GL30.glEnableVertexAttribArray(0)
        //draw the vertices
        /*GL30.glDrawArrays(GL30.GL_TRIANGLES,0,model.vertexCount)*/
        GL30.glDrawElements(GL30.GL_TRIANGLES,model.vertexCount,GL30.GL_UNSIGNED_INT,0)
        GL30.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }
}