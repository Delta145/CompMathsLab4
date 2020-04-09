package chart

import java.awt.Color
import java.lang.Math.PI

import method.LagrangePolynomial
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYSplineRenderer
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.data.xy.{XYDataset, XYSeries, XYSeriesCollection}

object InterpolationChart extends App {
  val sampleX = List(0, PI*2/3, PI, 2*PI)
  val sampleY = List(1, -0.5, -1, 1)

  val interF = new LagrangePolynomial(sampleX, sampleY).getFunction()

  new InterpolationChart("test", interF, Math.cos, sampleX, sampleY).drawChart()

}

class InterpolationChart(title: String, interF: Double => Double, origF: Double => Double, datasetX: List[Double], datasetY: List[Double]) extends ApplicationFrame("Lab4") {
  def drawChart(): Unit = {
    val chart = createChart()
    val panel = new ChartPanel(chart)
    panel.setPreferredSize(new java.awt.Dimension(560, 480))
    setContentPane(panel)
    pack()
    setVisible(true)
  }

  def createChart(): JFreeChart = {
    val chart = ChartFactory.createXYLineChart(
      title,
      "x",
      "y",
      null,
      PlotOrientation.VERTICAL,
      true,
      false,
      false)

    val plot = chart.getXYPlot
    plot.setBackgroundPaint(new Color(159, 190, 237))

    val r = new XYSplineRenderer() //характеристики графика

    r.setSeriesShapesVisible(0, false)
    r.setSeriesPaint(0, Color.blue)

    val r1 = new XYSplineRenderer()

    r1.setSeriesPaint(0, Color.RED)
    r1.setSeriesShapesVisible(0, false)

    val dataset1 = calcFuncDataset(datasetX.head - 1, datasetX.last + 2, origF, "cos(x)")
    val dataset2 = calcFuncDataset(datasetX.head - 1, datasetX.last + 2, interF, "interF(x)")

    plot.setDataset(0, dataset1)
    plot.setDataset(2, dataset2)

    // Подключение Spline Renderer к набору данных
    plot.setRenderer(0, r)
    plot.setRenderer(2, r1)

    chart
  }

  def calcFuncDataset(a: Double, b: Double, f: Double => Double, desc: String): XYDataset = {
    val step = Math.abs(b - a) / 100
    val dataset = new XYSeries(new String(desc))
    var i = a
    while (i < b) {
      dataset.add(i, f(i))
      i += step
    }

    val series = new XYSeriesCollection()
    series.addSeries(dataset)

    series
  }
}
