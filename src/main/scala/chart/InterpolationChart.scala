package chart

import java.awt.Color
import java.awt.geom.Ellipse2D
import java.lang.Math.PI

import method.LagrangePolynomial
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYSplineRenderer
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.data.xy.{XYDataset, XYSeries, XYSeriesCollection}

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

    val rCosX = new XYSplineRenderer() //характеристики графика

    rCosX.setSeriesShapesVisible(0, false)
    rCosX.setSeriesPaint(0, Color.BLACK)

    val rInterpolated = new XYSplineRenderer()

    rInterpolated.setSeriesPaint(0, Color.BLUE)
    rInterpolated.setSeriesShapesVisible(0, false)

    val rPoints = new XYSplineRenderer()

    rPoints.setSeriesPaint(0, Color.RED)
    rPoints.setSeriesShapesVisible(0, true)
    rPoints.setSeriesLinesVisible(0, false)
    rPoints.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6))

    val rX = new XYSplineRenderer()

    rX.setSeriesPaint(0, Color.GRAY)
    rX.setSeriesShapesVisible(0, false)

    val a = datasetX.head - 1; val b = datasetX.last + 1.75
    val dataset1 = calcFuncDataset(a, b, origF, "cos(x)")
    val dataset2 = calcFuncDataset(a, b, interF, "interF(x)")
    val points = createDatasetFromLists(datasetX, datasetY)
    val xAxis = createXaxis(a, b)

    plot.setDataset(0, dataset1)
    plot.setDataset(1, dataset2)
    plot.setDataset(2, points)
    plot.setDataset(3, xAxis)

    // Подключение Spline Renderer к набору данных
    plot.setRenderer(0, rCosX)
    plot.setRenderer(1, rInterpolated)
    plot.setRenderer(2, rPoints)
    plot.setRenderer(3, rX)

    chart
  }

  def createDatasetFromLists(listX: List[Double], listY: List[Double]): XYDataset = {
    val dataset = new XYSeries(new String("sample"))
    for ((x,i) <- listX.view.zipWithIndex) {
      dataset.add(x, listY(i))
    }

    val series = new XYSeriesCollection()
    series.addSeries(dataset)

    series
  }

  def createXaxis(a: Double, b: Double): XYDataset = {
    val dataset = new XYSeries(new String("y = 0"))
    dataset.add(a , 0)
    dataset.add(b , 0)

    val series = new XYSeriesCollection()
    series.addSeries(dataset)

    series
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
