package kmeans
package fun

import java.awt._
import java.awt.event._
import java.awt.image._
import java.io._
import javax.imageio._
import javax.swing._
import javax.swing.event._
import common._

class PhotoCanvas extends JComponent {

  var imagePath: Option[String] = None

  var image = loadEPFLImage()

  val timerDelay = 100
  val timer =
    new Timer(timerDelay, new ActionListener() {
      def actionPerformed(e: ActionEvent): Unit = repaint()
    })

  override def getPreferredSize = {
    new Dimension(image.width, image.height)
  }

  private def loadEPFLImage(): Img = {
    val stream = this.getClass.getResourceAsStream("/kmeans/epfl-view.jpg")
    try {
      loadImage(stream)
    } finally {
      stream.close()
    }
  }

  private def loadFileImage(path: String): Img = {
    val stream = new FileInputStream(path)
    try {
      loadImage(stream)
    } finally {
      stream.close()
    }
  }

  private def loadImage(inputStream: InputStream): Img = {
    val bufferedImage = ImageIO.read(inputStream)
    val width = bufferedImage.getWidth
    val height = bufferedImage.getHeight
    val img = new Img(width, height)
    for (x <- 0 until width; y <- 0 until height)
      img(x, y) = bufferedImage.getRGB(x, y)
    img
  }

  def reload(): Unit = {
    image = imagePath match {
      case Some(path) => loadFileImage(path)
      case None => loadEPFLImage()
    }
    repaint()
  }

  def loadFile(path: String): Unit = {
    imagePath = Some(path)
    reload()
  }

  def saveFile(path: String): Unit = {
    reload()
    val stream = new FileOutputStream(path)
    val bufferedImage = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB)
    for (x <- 0 until image.width; y <- 0 until image.height) bufferedImage.setRGB(x, y, image(x, y))
    ImageIO.write(bufferedImage, "png", stream)
  }

  def applyIndexedColors(colorCount: Int, initStrategy: InitialSelectionStrategy, convStrategy: ConvergenceStrategy): String = {
    val filter = new IndexedColorFilter(image, colorCount, initStrategy, convStrategy)
    image = filter.getResult()
    repaint()
    filter.getStatus()
  }

  override def paintComponent(gcan: Graphics) = {
    super.paintComponent(gcan)

    val width = image.width
    val height = image.height
    val bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    for (x <- 0 until width; y <- 0 until height) bufferedImage.setRGB(x, y, image(x, y))

    gcan.drawImage(bufferedImage, 0, 0, null)
  }

}
