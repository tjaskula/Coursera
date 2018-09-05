package barneshut

import java.awt._
import java.awt.event._
import javax.swing._
import javax.swing.event._
import scala.collection.parallel._
import scala.collection.par._
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

object BarnesHut {

  val model = new SimulationModel

  var simulator: Simulator = _

  def initialize(parallelismLevel: Int, pattern: String, nbodies: Int) {
    model.initialize(parallelismLevel, pattern, nbodies)
    model.timeStats.clear()
    simulator = new Simulator(model.taskSupport, model.timeStats)
  }

  class BarnesHutFrame extends JFrame("Barnes-Hut") {
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
    
    val parallelismLabel = new JLabel("Parallelism")
    controls.add(parallelismLabel)
    
    val items = (1 to Runtime.getRuntime.availableProcessors).map(_.toString).toArray
    val parcombo = new JComboBox[String](items)
    parcombo.setSelectedIndex(items.length - 1)
    parcombo.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) = {
        initialize(getParallelism, "two-galaxies", getTotalBodies)
        canvas.repaint()
      }
    })
    controls.add(parcombo)
    
    val bodiesLabel = new JLabel("Total bodies")
    controls.add(bodiesLabel)
    
    val bodiesSpinner = new JSpinner(new SpinnerNumberModel(25000, 32, 1000000, 1000))
    bodiesSpinner.addChangeListener(new ChangeListener {
      def stateChanged(e: ChangeEvent) = {
        if (frame != null) {
          initialize(getParallelism, "two-galaxies", getTotalBodies)
          canvas.repaint()
        }
      }
    })
    controls.add(bodiesSpinner)
    
    val stepbutton = new JButton("Step")
    stepbutton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        stepThroughSimulation()
      }
    })
    controls.add(stepbutton)
    
    val startButton = new JToggleButton("Start/Pause")
    val startTimer = new javax.swing.Timer(0, new ActionListener {
      def actionPerformed(e: ActionEvent) {
        stepThroughSimulation()
      }
    })
    startButton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        if (startButton.isSelected) startTimer.start()
        else startTimer.stop()
      }
    })
    controls.add(startButton)
    
    val quadcheckbox = new JToggleButton("Show quad")
    quadcheckbox.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        model.shouldRenderQuad = quadcheckbox.isSelected
        repaint()
      }
    })
    controls.add(quadcheckbox)

    val clearButton = new JButton("Restart")
    clearButton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        initialize(getParallelism, "two-galaxies", getTotalBodies)
      }
    })
    controls.add(clearButton)

    val info = new JTextArea("   ")
    info.setBorder(BorderFactory.createLoweredBevelBorder)
    rightpanel.add(info, BorderLayout.SOUTH)

    val canvas = new SimulationCanvas(model)
    add(canvas, BorderLayout.CENTER)
    setVisible(true)

    def updateInformationBox() {
      val text = model.timeStats.toString
      frame.info.setText("--- Statistics: ---\n" + text)
    }

    def stepThroughSimulation() {
      SwingUtilities.invokeLater(new Runnable {
        def run() = {
          val (bodies, quad) = simulator.step(model.bodies)
          model.bodies = bodies
          model.quad = quad
          updateInformationBox()
          repaint()
        }
      })
    }

    def getParallelism = {
      val selidx = parcombo.getSelectedIndex
      parcombo.getItemAt(selidx).toInt
    }

    def getTotalBodies = bodiesSpinner.getValue.asInstanceOf[Int]

    initialize(getParallelism, "two-galaxies", getTotalBodies)
  }

  try {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
  } catch {
    case _: Exception => println("Cannot set look and feel, using the default one.")
  }

  val frame = new BarnesHutFrame

  def main(args: Array[String]) {
    frame.repaint()
  }

}
