package method

import scala.collection.mutable.ListBuffer

class LagrangePolynomial(val X: List[Double], val Y: List[Double]) {
  def getFunction(): Double => Double = {
    val function = ListBuffer.empty[Double => Double]

    for((v,i) <- Y.view.zipWithIndex) {
      function.addOne((x: Double) => v/getDenominator(i)*getNumerator(i)(x))
    }

    (x: Double) => {
      var sum = 0.0
      function.foreach((f: Double => Double) => sum += f(x))
      sum
    }
  }

  def getNumerator(k: Int): Double => Double = {
    val numerator = ListBuffer.empty[Double => Double]

    for ((v, i) <- X.view.zipWithIndex) {
      if (i != k)
        numerator.addOne((x: Double) => x - v)
    }

    (x: Double) => {
      var sum = 1.0
      numerator.foreach((f: Double => Double) => sum *= f(x))
      sum
    }
  }

  def getDenominator(k: Int): Double = {
    var ans = 1.0
    for((v,i) <- X.view.zipWithIndex) {
      if (k != i)
        ans *= X(k) - v
    }
    ans
  }

}
