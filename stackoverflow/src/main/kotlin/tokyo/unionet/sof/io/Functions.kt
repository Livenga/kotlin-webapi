package tokyo.unionet.sof.io

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption


fun saveToFile(path: String = "output.txt", array: ByteArray) {
  val nioPath: Path = Paths.get(path)

  Files.newOutputStream(nioPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE).use({
    it.write(array, 0, array.size)
    it.flush()
  })
}


fun readInputStream(strm: InputStream): ByteArray? {
  var tmp: ByteArray? = null

  ByteArrayOutputStream().use({ _bos: ByteArrayOutputStream -> 
    val array =  ByteArray(1024)
    var readSize: Int

    while(true) {
      readSize = strm.read(array, 0, 1024)

      if(readSize > 0) {
        _bos.write(array, 0, readSize)
      } else {
        break
      }
    }

    tmp = _bos.toByteArray()
  })

  return tmp
}
