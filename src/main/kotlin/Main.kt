import simpleEngine.core.GameLogic
import simpleEngine.core.Window
import simpleEngine.scene.Loader
import simpleEngine.core.SimpleEngine
import simpleEngine.scene.Renderer
import simpleEngine.shader.StaticShader

fun main() {
    SimpleEngine("Simple Engine", Window.WindowOptions(800, 600, 60, 30), MyGame()).run()
}

class MyGame: GameLogic(){

    private lateinit var renderer: Renderer
    private lateinit var loader: Loader
    private lateinit var staticShader: StaticShader

    /*val vertices = floatArrayOf(
        // Left bottom triangle
        -0.5f, 0.5f, 0f, // V0
        -0.5f, -0.5f, 0f, // V1
        0.5f, -0.5f, 0f, // V2
        // Right top triangle
        0.5f, -0.5f, 0f, // V2
        0.5f, 0.5f, 0f, // V3
        -0.5f, 0.5f, 0f // V0
    )*/

    val vertices = floatArrayOf(
        -0.5f, 0.5f, 0f, // V0
        -0.5f, -0.5f, 0f, // V1
        0.5f, -0.5f, 0f, // V2
        0.5f, 0.5f, 0f // V3
    )

    val indices = intArrayOf(
        0, 1, 3, // Top left triangle (V0, V1, V3)
        3, 1, 2 // Bottom right triangle (V3, V1, V2)
    )



    override fun cleanup() {
        loader.cleanUp()
        staticShader.cleanUp()
    }

    override fun init(window: Window) {

        //please initialise components at init
        //can cause jdk exception otherwise
        staticShader = StaticShader()
        renderer = Renderer()
        loader = Loader()
    }

    override fun input(window: Window, diffTimeMillis: Long) {
    }

    override fun update(window: Window, diffTimeMillis: Long) {
        val model = loader.loadToVAO(vertices,indices)
        renderer.prepare()
        staticShader.start()
        renderer.render(model)
        staticShader.stop()
    }

}