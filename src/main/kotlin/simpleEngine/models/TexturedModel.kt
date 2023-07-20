package simpleEngine.models

import simpleEngine.texture.ModelTexture

data class TexturedModel(
    val rawModel: RawModel,
    val texture: ModelTexture
)