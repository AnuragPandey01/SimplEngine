package simpleEngine.core

/**
 * The main class of the Ngine engine
 * creates and manages the window, the scene and the render
 * it is responsible for the game loop
 *
 * @author droidev
 * @param windowTitle The title of the window
 * @param windowOptions The options of the window
 * @param gameLogic The game logic
 */
class SimpleEngine(
    private val windowTitle:String,
    private val windowOptions: Window.WindowOptions,
    private val gameLogic : GameLogic
) : Runnable{

    companion object{
        const val TARGET_UPS = 30
    }

    private var targetFps = windowOptions.fps
    private var targetUps = TARGET_UPS
    private var running = false
    /*private val scene: Scene
    private val renderer: Renderer*/
    private val window: Window

    init {

        window = Window(windowTitle, windowOptions)
        targetFps = windowOptions.fps
        targetUps = windowOptions.ups

        /*scene = Scene(window.width,window.height)
        renderer = Renderer()*/

        gameLogic.init(window)
        running = true

        window.setResizeCallback {
            gameLogic.onResize(window)
            /*scene.resize(window.width, window.height)*/
        }
    }

    /**
     * The main game loop
     * responsible for  the input, update and the rendering
     */
    override  fun run(){

        var initialTime = System.currentTimeMillis()
        val timeU = 1000.0f / targetUps // time per update
        val timeR: Float = if (targetFps > 0) 1000.0f / targetFps else 0F // time per render
        var deltaUpdate = 0f
        var deltaFps = 0f

        var updateTime = initialTime
        while (running && !window.shouldClose()) {

            //TODO: window.pollEvents()

            val now = System.currentTimeMillis()

            deltaUpdate += (now - initialTime) / timeU
            deltaFps += (now - initialTime) / timeR

            if (targetFps <= 0 || deltaFps >= 1) {
                gameLogic.input(window, now - initialTime)
            }
            if (deltaUpdate >= 1) {
                val diffTimeMillis = now - updateTime
                gameLogic.update(window,  diffTimeMillis)
                updateTime = now
                deltaUpdate--
            }
            if (targetFps <= 0 || deltaFps >= 1) {
                /*renderer.render(window, scene)*/
                deltaFps--
                window.update()
            }
            initialTime = now
        }

        cleanup()
    }

    fun start(){
        running = true
        run();
    }

    fun stop(){
        running = false
    }


    private fun cleanup() {
        gameLogic.cleanup()
        window.cleanup()
    }
}