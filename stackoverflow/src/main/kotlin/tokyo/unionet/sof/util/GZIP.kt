package tokyo.unionet.sof.util

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.zip.GZIPInputStream


public class GZIP {
  companion object {
    fun deflate(dataset: ByteArray?): ByteArray? {
      var outputArray: ByteArray? = null

      if(dataset == null) {
        return null
      }

      ByteArrayOutputStream().use({ _bos ->
        ByteArrayInputStream(dataset).use({ _bis: ByteArrayInputStream ->
          GZIPInputStream(_bis).use({ _gis: GZIPInputStream ->
            var read_size: Int
            val array = ByteArray(1024)

            while(true) {
              read_size = _gis.read(array, 0, 1024)
              if(read_size > 0) {
                _bos.write(array, 0, read_size)
              } else {
                break
              }
            }
          })
        })

        outputArray = _bos.toByteArray()
      })

      return outputArray
    }
  }
}
