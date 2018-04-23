package tokyo.unionet.connpassview.data;

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.module.kotlin.*

class UserAgent {
  companion object {
    const val CHROME = "Mozilla/5.0 (X11; Linux x86_64)" +
    " AppleWebKit/537.36 (KHTML, like Gecko) " +
    "Chrome/65.0.3325.181 Safari/537.36"

    const val CONNPASS_VIEW = "kotlin-connpass_view/0.1"
  }
}

enum class ConnpassSearchQueryOrder(val value: Int)  {
  UPDATE_DATETIME(1), // 更新日時
  DATES(2),           // 開催日時
  NEW_ARRIVAL(3)      // 新着順
}

data class ConnpassSearchQuery(
    val eventId: Long? = null,
    val keyword: String? = null,
    val keywordOr: String? = null,
    val ym: String? = null,
    val ymd: String? = null,
    val nickname: String? = null,
    val ownerNickname: String? = null,
    val seriesId: Long? = null,
    val start: Long = 1,
    val order: ConnpassSearchQueryOrder = ConnpassSearchQueryOrder.UPDATE_DATETIME,
    val count: Long = 10,
    val format: String = "json"
) {
  override fun toString(): String {
    val sb = StringBuilder()

    sb.append("?event_id=${eventId ?: ""}")
    .append("&keyword=${keyword ?: ""}")
    .append("&keyword_or=${keywordOr ?: ""}")
    .append("&ym=${ym ?: ""}")
    .append("&ymd=${ymd ?: ""}")
    .append("&nickname=${nickname ?: ""}")
    .append("&owner_nickname=${ownerNickname?: ""}")
    .append("&series_id=${seriesId ?: ""}")
    .append("&start=$start")
    .append("&order=${order.value}")
    .append("&count=$count")
    .append("&format=$format")

    return sb.toString()
  }
}


data class ConnpassSeries(
    @JsonProperty("id") val id: Long,
    @JsonProperty("title") val title: String,
    @JsonProperty("url") val url: String
)

data class ConnpassEvent(
    @JsonProperty("event_id") val eventId: Long,
    @JsonProperty("title") val title: String,
    @JsonProperty("catch") val `catch`: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("event_url") val eventUrl: String,
    @JsonProperty("hash_tag") val hashTag: String,
    @JsonProperty("started_at") val startedAt: String,
    @JsonProperty("ended_at") val endedAt: String,
    @JsonProperty("limit") val limit: Long,
    @JsonProperty("event_type") val eventType: String,
    @JsonProperty("series") val series: ConnpassSeries?,
    @JsonProperty("address") val address: String?,
    @JsonProperty("place") val place: String?,
    @JsonProperty("lat") val lat: Double?,
    @JsonProperty("lon") val lon: Double?,
    @JsonProperty("owner_id") val ownerId: Long,
    @JsonProperty("owner_nickname") val ownerNickName: String,
    @JsonProperty("owner_display_name") val ownerDisplayName: String,
    @JsonProperty("accepted") val accepted: Long,
    @JsonProperty("waiting") val waiting: Long,
    @JsonProperty("updated_at") val updatedAt: String
)

data class ConnpassEventResponse(
    @JsonProperty("results_returned") val resultsReturned: Long,
    @JsonProperty("results_available") val resultsAvailable: Long,
    @JsonProperty("results_start") val resultsStart: Long,
    @JsonProperty("events") val events: Array<ConnpassEvent>
) {
  companion object factory {
    fun parse(json: String): ConnpassEventResponse {
      val mapper = jacksonObjectMapper()
      return mapper.readValue(json)
    }
  }
}
