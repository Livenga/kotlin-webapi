package tokyo.unionet.sof.net

import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL;
import java.util.zip.GZIPInputStream


class StackOverflow {
  companion object static {
    const val URL = "https://stackoverflow.com"
    const val OAUTH_PATH = "oauth"
    const val ACCESS_TOKEN = "oauth/access_token"
    const val ACCESS_TOKEN_JSON = "oauth/access_token/json"
    const val STACKEXCHANGE = "https://api.stackexchange.com"
  }
}

class StackOverflowClient(
    val path: String,
    val query: StackOverflowQuery? = null,
    val verbose: Boolean = false
) {
  var statusCode: Int = 0
  var responseText: String? = null
  var response: ByteArray? = null
  var contentEncoding: String? = null


  fun connect(method: String = "GET", requestBody: String? = null) {
    val queries = this.query?.toQuery() ?: ""
    val url     = URL("${path}${queries}")
    val conn    = url.openConnection() as HttpURLConnection

    
    conn.requestMethod = method
    conn.setRequestProperty("Connection", "close")
    conn.setRequestProperty("User-Agent", UserAgent.CHROME)
    if(requestBody != null) {
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
      conn.setRequestProperty("Content-Length", "${requestBody.length}")
      conn.doOutput = true;

      conn.outputStream.use({
        val bytes = requestBody.toByteArray()
        it.write(bytes, 0, bytes.size)
      })
    }

    conn.doInput = true;
    conn.connect()

    this.statusCode      = conn.responseCode
    this.contentEncoding = conn.getHeaderField("Content-Encoding")
    
    if(verbose) {
      println("* Access URL: ${url.toString()}")
      println("* Header Fields:")
      conn.headerFields.forEach({
        println("\t** ${it.key}: ${it.value}")
      })
    }

    if(this.statusCode < 400) {
      // 応答データの取得
      this.response = tokyo.unionet.sof.io.readInputStream(conn.inputStream)

    } else {
      this.response = tokyo.unionet.sof.io.readInputStream(conn.errorStream)
    }
    // gzip 圧縮の解凍
    if(contentEncoding.equals(other = "gzip", ignoreCase = true)) {
      this.response = tokyo.unionet.sof.util.GZIP.deflate(this.response)
    }

    conn.disconnect()
  }
}
