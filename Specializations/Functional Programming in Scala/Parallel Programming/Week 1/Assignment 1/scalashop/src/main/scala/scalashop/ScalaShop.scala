package scalashop

import java.awt._
import java.awt.event._
import javax.swing._
import javax.swing.event._
import scala.collection.parallel._
import scala.collection.par._
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import org.scalameter._
import common._

object ScalaShop {

  class ScalaShopFrame extends JFrame("ScalaShop\u2122") {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setSize(1024, 600)
    setLayout(new BorderLayout)

    val rightpanel = new JPanel
    rightpanel.setBorder(BorderFactory.createEtchedBorder(border.EtchedBorder.LOWERED))
    rightpanel.setLayout(new BorderLayout)
    add(rightpanel, BorderLayout.EAST)

    val controls = new JPanel
    controls.setLayout(new GridLayout(0, 2))
    rightpanel.add(controls, BorderLayout.NORTH)

    val filterLabel = new JLabel("Filter")
    controls.add(filterLabel)

    val filterCombo = new JComboBox(Array(
      "horizontal-box-blur",
      "vertical-box-blur"
    ))
    controls.add(filterCombo)

    val radiusLabel = new JLabel("Radius")
    controls.add(radiusLabel)

    val radiusSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 16, 1))
    controls.add(radiusSpinner)

    val tasksLabel = new JLabel("Tasks")
    controls.add(tasksLabel)

    val tasksSpinner = new JSpinner(new SpinnerNumberModel(32, 1, 128, 1))
    controls.add(tasksSpinner)

    val stepbutton = new JButton("Apply filter")
    stepbutton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        val time = measure {
          canvas.applyFilter(getFilterName, getNumTasks, getRadius)
        }
        updateInformationBox(time)
      }
    })
    controls.add(stepbutton)

    val clearButton = new JButton("Reload")
    clearButton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        canvas.reload()
      }
    })
    controls.add(clearButton)

    val info = new JTextArea("   ")
    info.setBorder(BorderFactory.createLoweredBevelBorder)
    rightpanel.add(info, BorderLayout.SOUTH)

    val mainMenuBar = new JMenuBar()

    val fileMenu = new JMenu("File")
    val openMenuItem = new JMenuItem("Open...")
    openMenuItem.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        val fc = new JFileChooser()
        if (fc.showOpenDialog(ScalaShopFrame.this) == JFileChooser.APPROVE_OPTION) {
          canvas.loadFile(fc.getSelectedFile.getPath)
        }
      }
    })
    fileMenu.add(openMenuItem)
    val exitMenuItem = new JMenuItem("Exit")
    exitMenuItem.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        sys.exit(0)
      }
    })
    fileMenu.add(exitMenuItem)

    mainMenuBar.add(fileMenu)

    val helpMenu = new JMenu("Help")
    val aboutMenuItem = new JMenuItem("About")
    aboutMenuItem.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        JOptionPane.showMessageDialog(null, "ScalaShop, the ultimate image manipulation tool\nBrought to you by EPFL, 2015")
      }
    })
    helpMenu.add(aboutMenuItem)

    mainMenuBar.add(helpMenu)

    setJMenuBar(mainMenuBar)

    val canvas = new PhotoCanvas

    val scrollPane = new JScrollPane(canvas)

    add(scrollPane, BorderLayout.CENTER)
    setVisible(true)

    def updateInformationBox(time: Double) {
      info.setText(s"Time: $time")
    }

    def getNumTasks: Int = tasksSpinner.getValue.asInstanceOf[Int]

    def getRadius: Int = radiusSpinner.getValue.asInstanceOf[Int]

    def getFilterName: String = {
      filterCombo.getSelectedItem.asInstanceOf[String]
    }

  }

  try {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
  } catch {
    case _: Exception => println("Cannot set look and feel, using the default one.")
  }

  val frame = new ScalaShopFrame

  def main(args: Array[String]) {
    frame.repaint()
  }

}
