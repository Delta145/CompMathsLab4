package method

class LagrangePolynomial(val X: List[Double], val Y: List[Double]) {
  def getFunction(): Double => Double = {
    x => {
      var sum = 0.0
      for ((y,i) <- Y.view.zipWithIndex) {
        sum += y/getDenominator(i)*getNumerator(i)(x)
      }
      sum
    }
  }

  def getNumerator(k: Int): Double => Double = {
    x => {
      var sum = 1.0
      for ((xi, i) <- X.view.zipWithIndex) {
        if (i != k)
          sum *= x - xi
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
