package io

import scala.io.StdIn
import scala.language.postfixOps

class CommandHandler {
  def handleUserInput(): Unit = {
    var sampleN: Option[Int] = None
    while (sampleN.isEmpty || sampleN.get < 1 || sampleN.get > 4) {
      println("Выберите один из предложенных наборов данных\n" +
        "1) нелинейное уравнение a*x^3 - b*x^2 - c*x + d методом хорд\n" +
        "2) нелинейное уравнение a*x^3 - b*x^2 - c*x + d методом секущих\n" +
        "3) систему нелинейных уравнений \n" +
        "\ty = a*sin(x) + b\n" +
        "\ty = c*e^x + d")
      try {
        sampleN = Option(StdIn.readInt())
      } catch {
        case x: Exception => println("Введите 1, 2, или 3")
      }
    }

    var arg: Option[Double] = None

    while (arg isEmpty) {
      println("Введите x, для которого вы хотите найти значение y")
      arg = readDoubleSafe()
    }

    try {

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
