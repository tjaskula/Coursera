package kmeans
package fun

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
    setSize(800, 500)
    setLayout(new BorderLayout)

    val rightpanel = new JPanel
    rightpanel.setBorder(BorderFactory.createEtchedBorder(border.EtchedBorder.LOWERED))
    rightpanel.setLayout(new BorderLayout)
    add(rightpanel, BorderLayout.EAST)

    val allControls = new JPanel
    allControls.setLayout(new BoxLayout(allControls, BoxLayout.Y_AXIS))
    rightpanel.add(allControls, BorderLayout.NORTH)

    // Color count selection
    val colorControls = new JPanel
    colorControls.setLayout(new GridLayout(0, 2))
    allControls.add(colorControls)

    val colorCountLabel = new JLabel("Colors")
    colorControls.add(colorCountLabel)

    val colorCountSpinner = new JSpinner(new SpinnerNumberModel(32, 16, 512, 16))
    colorControls.add(colorCountSpinner)

    // Initial selection
    val initSelectionControls = new JPanel
    initSelectionControls.setLayout(new GridLayout(0, 1))
    allControls.add(initSelectionControls)

    val initialSelectionGroup = new ButtonGroup()

    val initSelectionLabel = new JLabel("Initial Color Selection:")
    initSelectionControls.add(initSelectionLabel)

    val uniformSamplingButton = new JRadioButton("Uniform Sampling")
    uniformSamplingButton.setSelected(true);
    initSelectionControls.add(uniformSamplingButton)

    val randomSamplingButton = new JRadioButton("Random Sampling")
    initSelectionControls.add(randomSamplingButton)

    val uniformChoiceButton = new JRadioButton("Uniform Choice")
    initSelectionControls.add(uniformChoiceButton)

    initialSelectionGroup.add(randomSamplingButton)
    initialSelectionGroup.add(uniformSamplingButton)
    initialSelectionGroup.add(uniformChoiceButton)

    // Initial means selection
    val convergenceControls = new JPanel
    convergenceControls.setLayout(new BoxLayout(convergenceControls, BoxLayout.Y_AXIS))
    allControls.add(convergenceControls)

    val convergenceGroup = new ButtonGroup()

    val convergenceLabel = new JLabel("Convergence criteria:")
    initSelectionControls.add(convergenceLabel)

    val criteriaControls = new JPanel
    criteriaControls.setLayout(new GridLayout(0, 2))
    convergenceControls.add(criteriaControls)

    val stepConvergenceButton = new JRadioButton("Steps")
    criteriaControls.add(stepConvergenceButton)

    val stepCountSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 50, 1))
    criteriaControls.add(stepCountSpinner)

    val etaConvergenceButton = new JRadioButton("Eta")
    etaConvergenceButton.setSelected(true);
    criteriaControls.add(etaConvergenceButton)

    val etaCountSpinner = new JSpinner(new SpinnerNumberModel(0.001, 0.00001, 0.01, 0.00001))
    criteriaControls.add(etaCountSpinner)

    val snrConvergenceButton = new JRadioButton("Sound-to-noise")
    criteriaControls.add(snrConvergenceButton)

    val snrCountSpinner = new JSpinner(new SpinnerNumberModel(40, 10, 80, 1))
    criteriaControls.add(snrCountSpinner)

    convergenceGroup.add(stepConvergenceButton)
    convergenceGroup.add(etaConvergenceButton)
    convergenceGroup.add(snrConvergenceButton)

    // Action Buttons
    val actionControls = new JPanel
    actionControls.setLayout(new GridLayout(0, 2))
    allControls.add(actionControls)

    val stepbutton = new JButton("Apply filter")
    stepbutton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        var status = ""
        val time = measure {
          status = canvas.applyIndexedColors(getColorCount, getInitialSelectionStrategy, getConvergenceStragegy)
        }
        updateInformationBox(status, time)
      }
    })
    actionControls.add(stepbutton)

    val clearButton = new JButton("Reload")
    clearButton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        canvas.reload()
      }
    })
    actionControls.add(clearButton)

    val info = new JTextArea("              ")
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
    val saveMenuItem = new JMenuItem("Save...")
    saveMenuItem.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        val fc = new JFileChooser("epfl-view.png")
        if (fc.showSaveDialog(ScalaShopFrame.this) == JFileChooser.APPROVE_OPTION) {
          canvas.saveFile(fc.getSelectedFile.getPath)
        }
      }
    })
    fileMenu.add(saveMenuItem)
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

    def updateInformationBox(status: String, time: Double) {
      info.setText(s"$status\nTime: ${time.toInt} ms.")
    }

    def getColorCount: Int =
      colorCountSpinner.getValue.asInstanceOf[Int]

    def getInitialSelectionStrategy: InitialSelectionStrategy =
      if (randomSamplingButton.isSelected())
        RandomSampling
      else if (uniformSamplingButton.isSelected())
        UniformSampling
      else
        UniformChoice

    def getConvergenceStragegy: ConvergenceStrategy =
      if (stepConvergenceButton.isSelected())
        ConvergedAfterNSteps(stepCountSpinner.getValue.asInstanceOf[Int])
      else if (etaConvergenceButton.isSelected())
        ConvergedAfterMeansAreStill(etaCountSpinner.getValue.asInstanceOf[Double])
      else
        ConvergedWhenSNRAbove(snrCountSpinner.getValue.asInstanceOf[Int])
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
