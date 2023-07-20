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
){

    private var targetFps = windowOptions.fps
    private var running = false
    private val window: Window

    init {

        window = Window(windowTitle, windowOptions)
        targetFps = windowOptions.fps

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
    fun run(){

        while (running && !window.shouldClose()) {

            if(windowOptions.fps > 0){
                val timePerFrame = 1.0 / targetFps * 1000000000
                val now = System.nanoTime()
                while (System.nanoTime() - now < timePerFrame) {
                    //wait
                }
                window.update()
                gameLogic.update(window, 1)
            }
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