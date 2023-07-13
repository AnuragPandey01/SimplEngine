package simpleEngine.core

import simpleEngine.core.Window

/**
 * Every game must extend to this class
 * @see Window
 * @see SimpleEngine
 * @author droidev
 */
abstract class GameLogic {

    abstract fun cleanup()

    /**
     * This method is called once when the game starts
     * attach all the meshes to the scene here
     * @param window The window object
     */
    abstract fun init(window: Window/*, scene: Scene, renderer: Renderer*/)

    abstract fun input(window: Window, /*scene: Scene*/ diffTimeMillis: Long)

    /**
     * This method is called every frame
     * @param window The window object
     * @param scene The scene object
     * @param diffTimeMillis The time difference between the current frame and the previous frame
     */
    abstract fun update(window: Window, /*scene: Scene,*/ diffTimeMillis: Long)

    /**
     * This method is called when the window is resized
     * @param window The window object
     * @param scene The scene object
     * @param renderer The render object
     */
    open fun onResize(window: Window, /*scene: Scene, renderer: Renderer*/) {}

}