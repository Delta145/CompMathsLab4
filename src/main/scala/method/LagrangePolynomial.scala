package method

class LagrangePolynomial(val X: List[Double], val Y: List[Double]) {
  var firstRun = true
  val stringFunction = new StringBuilder

  def getFunction(): Double => Double = {
    x => {
      var sum = 0.0
      for ((y,i) <- Y.view.zipWithIndex) {
        val denominator = getDenominator(i)
        if (firstRun) stringFunction.append(s"$y")
        sum += y/denominator*getNumerator(i)(x)
        if (firstRun) stringFunction.append(s"/$denominator + ")
      }
      firstRun = false
      sum
    }
  }

  def getStringFunction(): String = stringFunction.toString()

  def getNumerator(k: Int): Double => Double = {
    x => {
      var sum = 1.0
      for ((xi, i) <- X.view.zipWithIndex) {
        if (i != k) {
          sum *= x - xi
          if (firstRun) stringFunction.append(s"(x-$xi)")
        }
      }
      sum
    }
  }

  def getDenominator(k: Int): Double = {
    var ans = 1.0
    for((xi,i) <- X.view.zipWithIndex) {
      if (k != i)
        ans *= X(k) - xi
    }
    ans
  }

}
