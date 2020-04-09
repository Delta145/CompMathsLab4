package main
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.mutable
import java.lang.Math._

import method.LagrangePolynomial
import org.scalactic.TolerantNumerics

class LagrangeTest extends AnyFlatSpec {

  val sampleX = List(0, PI*2/3, PI, 2*PI)
  val sampleY = List(1, -0.5, -1, 1)


  "A LagrangePolynomial class" should "interpolate for sample data" in {
    val method = new LagrangePolynomial(sampleX, sampleY)
    val function = method.getFunction()
    assert(function(sampleX(0)) === sampleY(0))
    assert(function(sampleX(1)) === sampleY(1))
    assert(function(sampleX(2)) === sampleY(2))
    assert(function(sampleX(3)) === sampleY(3))
  }

  "A LagrangePolynomial class" should "calculate denominator" in {
    implicit val doubleEquality = TolerantNumerics.tolerantDoubleEquality(0.00001)
    val method = new LagrangePolynomial(sampleX, sampleY)
    val ans = method.getDenominator(1)
    assert(ans === 9.18704494)
  }

  "A LagrangePolynomial class" should "calculate numerator" in {
    implicit val doubleEquality = TolerantNumerics.tolerantDoubleEquality(0.00001)
    val method = new LagrangePolynomial(sampleX, sampleY)
    val ans = method.getNumerator(1)(1)
    assert(ans === 11.314430841409337)
  }
}
