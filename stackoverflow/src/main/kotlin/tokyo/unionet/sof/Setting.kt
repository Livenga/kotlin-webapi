package tokyo.unionet.sof

import java.io.BufferedReader
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Path

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.*


data class Setting(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("key") val key: String
) {
  companion object {
    fun get(path: Path): Setting {
      val sb = StringBuilder()

      Files.newBufferedReader(path).use({

        while(true) {
          val line: String? = it.readLine()
          if(line == null) { break }

          sb.append(line)
        }
      })

      return jacksonObjectMapper().readValue(sb.toString())
    }
  }
}
