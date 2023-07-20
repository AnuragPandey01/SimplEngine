package simpleEngine.utils

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer


/**
 * This class represents a texture.
 */
class Texture {
    /**
     * Stores the handle of the texture.
     */
    val id: Int
    /**
     * Gets the texture width.
     *
     * @return Texture width
     */
    /**
     * Width of the texture.
     */
    var width = 0
        /**
         * Sets the texture width.
         *
         * @param width The width to set
         */
        set(width) {
            if (width > 0) {
                field = width
            }
        }
    /**
     * Gets the texture height.
     *
     * @return Texture height
     */
    /**
     * Height of the texture.
     */
    var height = 0
        /**
         * Sets the texture height.
         *
         * @param height The height to set
         */
        set(height) {
            if (height > 0) {
                field = height
            }
        }

    /** Creates a texture.  */
    init {
        id = GL11.glGenTextures()
    }

    /**
     * Binds the texture.
     */
    fun bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
    }

    /**
     * Sets a parameter of the texture.
     *
     * @param name  Name of the parameter
     * @param value Value to set
     */
    fun setParameter(name: Int, value: Int) {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, name, value)
    }

    /**
     * Uploads image data with specified width and height.
     *
     * @param width  Width of the image
     * @param height Height of the image
     * @param data   Pixel data of the image
     */
    fun uploadData(width: Int, height: Int, data: ByteBuffer?) {
        uploadData(GL11.GL_RGBA8, width, height, GL11.GL_RGBA, data)
    }

    /**
     * Uploads image data with specified internal format, width, height and
     * image format.
     *
     * @param internalFormat Internal format of the image data
     * @param width          Width of the image
     * @param height         Height of the image
     * @param format         Format of the image data
     * @param data           Pixel data of the image
     */
    fun uploadData(internalFormat: Int, width: Int, height: Int, format: Int, data: ByteBuffer?) {
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, data)
    }

    /**
     * Delete the texture.
     */
    fun delete() {
        GL11.glDeleteTextures(id)
    }

    companion object {
        /**
         * Creates a texture with specified width, height and data.
         *
         * @param width  Width of the texture
         * @param height Height of the texture
         * @param data   Picture Data in RGBA format
         *
         * @return Texture from the specified data
         */
        fun createTexture(width: Int, height: Int, data: ByteBuffer?): Texture {
            val texture = Texture()
            texture.width = width
            texture.height = height
            texture.bind()
            texture.setParameter(GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER)
            texture.setParameter(GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER)
            texture.setParameter(GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
            texture.setParameter(GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)
            texture.uploadData(GL11.GL_RGBA8, width, height, GL11.GL_RGBA, data)
            return texture
        }

        /**
         * Load texture from file.
         *
         * @param path File path of the texture
         *
         * @return Texture from specified file
         */
        fun loadTexture(path: String?): Texture {
            var image: ByteBuffer?
            var width: Int
            var height: Int
            MemoryStack.stackPush().use { stack ->
                /* Prepare image buffers */
                val w = stack.mallocInt(1)
                val h = stack.mallocInt(1)
                val comp = stack.mallocInt(1)

                /* Load image */STBImage.stbi_set_flip_vertically_on_load(true)
                image = STBImage.stbi_load(path, w, h, comp, 4)
                if (image == null) {
                    throw RuntimeException(
                        "Failed to load a texture file!"
                                + System.lineSeparator() + STBImage.stbi_failure_reason()
                    )
                }

                /* Get width and height of image */width = w.get()
                height = h.get()
            }
            return createTexture(width, height, image)
        }
    }
}