package tokyo.unionet.connpassview.util

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime


fun dateTimeString(): String {
  return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}
