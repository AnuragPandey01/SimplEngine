package simpleEngine.utils

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object FileUtils {

    fun readFile(
        filePath: String
    ): String = try {
        String(Files.readAllBytes(Paths.get(filePath)))
    } catch (excp: IOException) {
        throw RuntimeException("Error reading file [$filePath]", excp)
    }

}

