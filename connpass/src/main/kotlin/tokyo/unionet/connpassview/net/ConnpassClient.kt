package tokyo.unionet.connpassview.net

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

import kotlin.collections.List
import kotlin.collections.Map

import tokyo.unionet.connpassview.data.*


class ConnpassClient {
  companion object {
    fun get(path: String, searchQuery: ConnpassSearchQuery? = null): ConnpassEventResponse {
      val url    = URL("https://connpass.com/" + path +
      if(searchQuery != null) searchQuery.toString() else "")

      val client = url.openConnection() as HttpURLConnection 

      client.requestMethod = "GET"
      client.setRequestProperty("Connection", "close")
      client.setRequestProperty("User-Agent", UserAgent.CHROME)
      client.connect()

      val sb = StringBuilder()

      client.inputStream.use({
        InputStreamReader(it).use({
          BufferedReader(it).use({
            sb.append(it.readLine())
          })
        })
      })

      client.disconnect()
      return ConnpassEventResponse.parse(sb.toString())
    }
  }
}
