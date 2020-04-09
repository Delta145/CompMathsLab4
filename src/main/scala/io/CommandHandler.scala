package io

import scala.io.StdIn
import scala.language.postfixOps
import java.lang.Math._

import chart.InterpolationChart
import method.LagrangePolynomial

class CommandHandler {
  def handleUserInput(): Unit = {
    var nSample: Option[Int] = None
    while (nSample.isEmpty || nSample.get < 1 || nSample.get > 5) {
      println("Выберите один из предложенных наборов данных\n" +
        "1) [0, 2/3*PI, 4/3*PI, 2*PI]\n" +
        "   [1, -0.5, -0.5, 1]\n" +
        "2) [0, 2/7*PI, 4/7*PI, 6/7PI, 8/7*PI, 10/7*PI, 12/7*PI, 2*PI]\n" +
        "   [1, 0.623489, -0.22252, -0.90096, -0.90096, -0.22252, 0.623489, 1]\n" +
        "3) [0, 1*50/10.0*PI, 2*50/10.0*PI, 3*50/10.0*PI, 4*50/10.0*PI, 5*50/10.0*PI, 6*50/10.0*PI, 7*50/10.0*PI, 8*50/10.0*PI, 9*50/10.0*PI, 10*50/10.0*PI]\n" +
        "   [1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1]\n" +
        "4) [0, 2/7*PI, 4/7*PI, 6/7PI, 8/7*PI, 10/7*PI, 12/7*PI, 2*PI]\n" +
        "   [1, 0.623489, -0.22252, -0.90096, 0, -0.22252, 0.623489, 1]")
      try {
        nSample = Option(StdIn.readInt())
      } catch {
        case x: Exception => println("Введите 1, 2, или 3")
      }
    }

    var arg: Option[Double] = None

    while (arg isEmpty) {
      println("Введите x, для которого вы хотите найти значение y")
      arg = readDoubleSafe()
    }

    val sampleX: List[List[Double]] = List(
      List(0, PI*2/3, PI, 2*PI),
      List(0, 1.0/7*2*PI, 2.0/7*2*PI, 3.0/7*2*PI, 4.0/7*2*PI, 5.0/7*2*PI, 6.0/7*2*PI, 2*PI),
      List(0, 1*50/10.0*PI, 2*50/10.0*PI, 3*50/10.0*PI, 4*50/10.0*PI, 5*50/10.0*PI, 6*50/10.0*PI, 7*50/10.0*PI, 8*50/10.0*PI, 9*50/10.0*PI, 10*50/10.0*PI),
      List(0, 1.0/7*2*PI, 2.0/7*2*PI, 3.0/7*2*PI, 4.0/7*2*PI, 5.0/7*2*PI, 6.0/7*2*PI, 2*PI),
    )

    val sampleY: List[List[Double]] = List(
      List(1, -0.5, -1, 1),
      List(1, 0.623489, -0.22252, -0.90096, -0.90096, -0.22252, 0.623489, 1),
      List(1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1),
      List(1, 0.623489, -0.22252, -0.90096, 0, -0.22252, 0.623489, 1),
    )

    try {
      val n = nSample.get - 1
      val method = new LagrangePolynomial(sampleX(n), sampleY(n))
      val f = method.getFunction()
      val ans = f(arg.get)
      val strF = method.getStringFunction()
      println(s"Значение y для введённого аргумента: $ans")
      println(s"Получившаяся функция: $strF")
      new InterpolationChart("Интерполирование многочленом Лагранжа", f, cos, sampleX(n), sampleY(n)).drawChart()
    } catch {
      case e: Exception => println(e.getMessage)
    }
  }

  def readDoubleSafe(): Option[Double] = {
    try {
      Option(StdIn.readDouble())
    } catch {
      case x: Exception => println("Должно быть вещественным числом!")
        None
    }
  }
}
