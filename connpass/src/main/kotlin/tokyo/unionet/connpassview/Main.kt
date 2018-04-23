package tokyo.unionet.connpassview;

import tokyo.unionet.connpassview.data.*
import tokyo.unionet.connpassview.net.ConnpassClient

import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption


fun main(args: Array<String>) {
  try {
    val searchQuery = ConnpassSearchQuery(
        keyword = "android,kotlin",
        order = ConnpassSearchQueryOrder.DATES,
        start = 1,
        count = 100
    )

    val events = ConnpassClient.get(path = "/api/v1/event", searchQuery = searchQuery)

    val path = Paths.get("data", tokyo.unionet.connpassview.util.dateTimeString() + ".csv")

    Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE).use({ os: OutputStream ->
      //os.write("event_id,title,catch,description,event_url,hash_tag,started_at,ended_at,limit,owner_id,owner_nickname\n".toByteArray())
      os.write("event_id,title,catch,event_url,hash_tag,started_at,ended_at,limit,owner_id,owner_nickname\n".toByteArray())

      events.events.forEach({ e: ConnpassEvent ->
        /*
        val sb = StringBuilder()
        .append("${e.eventId},${e.title},${e.`catch`},${e.description},")
        .append("${e.eventUrl},${e.hashTag},${e.startedAt},${e.endedAt},")
        .append("${e.limit},${e.ownerId},${e.ownerNickName}\n")
        */
        val sb = StringBuilder()
        .append("${e.eventId},${e.title},${e.`catch`},")
        .append("${e.eventUrl},${e.hashTag},${e.startedAt},${e.endedAt},")
        .append("${e.limit},${e.ownerId},${e.ownerNickName}\n")

        os.write(sb.toString().toByteArray())
      })
    })

    println("Results returned : ${events.resultsReturned}")
    println("Results available: ${events.resultsAvailable}")
    println("Results start    : ${events.resultsStart}")
  } catch(e: Exception) {
    e.printStackTrace()
  }
}
