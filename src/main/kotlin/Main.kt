import org.joml.Vector3f
import simpleEngine.camera.Camera
import simpleEngine.core.GameLogic
import simpleEngine.core.Window
import simpleEngine.scene.Loader
import simpleEngine.core.SimpleEngine
import simpleEngine.entity.Entity
import simpleEngine.models.RawModel
import simpleEngine.models.TexturedModel
import simpleEngine.scene.Renderer
import simpleEngine.shader.StaticShader
import simpleEngine.texture.ModelTexture

fun main() {
    SimpleEngine("Simple Engine", Window.WindowOptions(800, 800, 60), MyGame()).run()
}

class MyGame: GameLogic(){

    private lateinit var renderer: Renderer
    private lateinit var loader: Loader
    private lateinit var staticShader: StaticShader
    private lateinit var model: RawModel
    private lateinit var modelTexture:ModelTexture
    private lateinit var texturedModel: TexturedModel
    private lateinit var entity: Entity
    private lateinit var camera: Camera


    val vertices = floatArrayOf(
        -0.5f,0.5f,-0.5f,
        -0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,-0.5f,
        0.5f,0.5f,-0.5f,

        -0.5f,0.5f,0.5f,
        -0.5f,-0.5f,0.5f,
        0.5f,-0.5f,0.5f,
        0.5f,0.5f,0.5f,

        0.5f,0.5f,-0.5f,
        0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,0.5f,
        0.5f,0.5f,0.5f,

        -0.5f,0.5f,-0.5f,
        -0.5f,-0.5f,-0.5f,
        -0.5f,-0.5f,0.5f,
        -0.5f,0.5f,0.5f,

        -0.5f,0.5f,0.5f,
        -0.5f,0.5f,-0.5f,
        0.5f,0.5f,-0.5f,
        0.5f,0.5f,0.5f,

        -0.5f,-0.5f,0.5f,
        -0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,0.5f
    )

    val indices = intArrayOf(
        0,1,3,
        3,1,2,
        4,5,7,
        7,5,6,
        8,9,11,
        11,9,10,
        12,13,15,
        15,13,14,
        16,17,19,
        19,17,18,
        20,21,23,
        23,21,22
    )

    val textureCoords = floatArrayOf(
        0f,0f,
        0f,1f,
        1f,1f,
        1f,0f,
        0f,0f,
        0f,1f,
        1f,1f,
        1f,0f,
        0f,0f,
        0f,1f,
        1f,1f,
        1f,0f,
        0f,0f,
        0f,1f,
        1f,1f,
        1f,0f,
        0f,0f,
        0f,1f,
        1f,1f,
        1f,0f,
        0f,0f,
        0f,1f,
        1f,1f,
        1f,0f
    )



    override fun cleanup() {
        loader.cleanUp()
        staticShader.cleanUp()
    }

    override fun init(window: Window) {

        //please initialise components at init
        //can cause jdk exception otherwise
        staticShader = StaticShader()
        camera = Camera(window)
        renderer = Renderer(staticShader,camera)
        loader = Loader()
        model = loader.loadToVAO(vertices,indices,textureCoords)
        modelTexture = ModelTexture(loader.loadTexture("brick"))
        texturedModel = TexturedModel(model,modelTexture)
        entity = Entity(texturedModel, Vector3f(0f,0f,-5f), Vector3f(0f,0f,0f), 1f)

    }

    override fun input(window: Window, diffTimeMillis: Long) {
    }

    override fun update(window: Window, diffTimeMillis: Long) {
        camera.move()
        entity.changeRotation(1f,1f,0f)
        staticShader.start()
        renderer.render(entity)
        staticShader.stop()
    }

}