package tokyo.unionet.sof.net

import java.lang.StringBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset


enum class Order(val value: Int) {
  DESC(0),
  ASC(1);

  override fun toString(): String {
    when(this) {
      DESC -> return "desc"
      ASC -> return "asc"

      else -> return ""
    }
  }
}

enum class Sort(val value: Int) {
  ACTIVITY(0),
  VOTES(1),
  CREATION(2),
  HOT(3),
  WEEK(4),
  MONTH(5),
  POPULAR(6),
  NAME(7);

  override fun toString(): String {
    when(this) {
      ACTIVITY -> return "activity"
      VOTES -> return "votes"
      CREATION -> return "creation"
      HOT -> return "hot"
      WEEK -> return "week"
      MONTH -> return "month"

      else -> return ""
    }
  }
}


abstract class StackOverflowQuery(
    val accessToken: String? = null,
    val key: String? = null
) {
  abstract fun toQuery(): String
}


data class StackOverflowOAuthQuery(
    val clientId: Long,
    val scope: String,
    val redirectUri: String,
    val state: String?
): StackOverflowQuery(accessToken = null, key = null) {
  override fun toQuery(): String {
    var app: String
    app = "access_token=${this.accessToken ?: ""}&key=${key ?: ""}"

    return "$app&client_id=$clientId&scope=$scope&redirect_uri=$redirectUri&state=${state ?: ""}"
  }
}


data class StackOverflowAccessToken(
    val clientId: Long,
    val clientSecret: String,
    val code: String,
    val redirectUri: String
): StackOverflowQuery(accessToken = null) {
  override fun toQuery(): String {
    return "client_id=$clientId&client_secret=$clientSecret&code=$code&redirect_uri=$redirectUri"
  }
}

class StackOverflowQuestion(
    accessToken: String? = null,
    key: String? = null,
    val page: Long = 1,
    val pageSize: Long = 10,
    val fromDate: LocalDate? = null,
    val toDate: LocalDate? = null,
    val min: LocalDate? = null,
    val max: LocalDate? = null,
    val order: Order = Order.DESC,
    val sort: Sort = Sort.ACTIVITY,
    val tagged: String? = null
) : StackOverflowQuery(accessToken, key) {

  override fun toQuery(): String {
    val sb = StringBuilder()

    val f: (LocalDate?) -> String = { ld ->
      if(ld == null) {
        ""
      } else {
        "${ld.atStartOfDay()?.toEpochSecond(ZoneOffset.UTC)}"
      }
    }

    sb.append("?page=${page}&page_size=${pageSize}")
    .append("&fromdate=${f(fromDate)}")
    .append("&todate=${f(fromDate)}")
    .append("&min=${f(min)}")
    .append("&max=${f(max)}")
    .append("&order=${order.toString()}")
    .append("&sort=${sort.toString()}")
    .append("&tagged=${ tagged ?: ""}")
    .append("&site=stackoverflow.com")
    .append("&access_token=${accessToken ?: ""}")
    .append("&key=${key ?: ""}")

    return sb.toString()
  }
}
