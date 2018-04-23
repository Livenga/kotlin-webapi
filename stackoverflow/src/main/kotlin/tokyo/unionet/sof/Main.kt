package tokyo.unionet.sof

import tokyo.unionet.sof.data.*
import tokyo.unionet.sof.net.*

import java.io.BufferedWriter
import java.nio.file.Files as nioFiles
import java.nio.file.Path as nioPath
import java.nio.file.Paths as nioPaths
import java.nio.file.StandardOpenOption

import com.fasterxml.jackson.module.kotlin.*


fun main(args: Array<String>) {
  val mapper = jacksonObjectMapper()
  val br = nioFiles.newBufferedWriter(
      nioPaths.get(".", "list.csv"),
      StandardOpenOption.CREATE, StandardOpenOption.WRITE
  )


  try {
    val setting: Setting = Setting.get(nioPaths.get(".", "setting.json"))

    for( i in 1..10) {
      val query: StackOverflowQuestion? = StackOverflowQuestion(
          page        = i.toLong(),
          pageSize    = 100,
          sort        = Sort.POPULAR,
          order       = Order.ASC,
          accessToken = setting.accessToken,
          key         = setting.key
      )

      val client = StackOverflowClient(
          path    = "${StackOverflow.STACKEXCHANGE}/2.2/tags",
          query   = query,
          verbose = false
      )

      client.connect()


      if(client.statusCode < 400) {
        val resp: StackOverflowResponseTag = mapper.readValue(String(client.response!!))

        resp.items.forEach({
          val line = "${it.name}, ${it.count}\n"
          //println("Tag Name: ${it.name}, ${it.count}")
          
          br.write(line, 0, line.length)
        })
        if(!resp.hasMore) {
          break
        }
      } else {
        println(String(client.response!!))
        break
      }
    }
  } catch(e: Exception) {
    println(e.message)
  } finally {
    br.close()
  }
}
