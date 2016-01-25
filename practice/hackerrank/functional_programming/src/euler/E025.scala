package euler

object NDigitFibonacciNumber {

  lazy val fib: Stream[BigInt] =
    1 #:: 1 #:: (fib zip fib.tail).map { case (a, b) => (a + b) }

  // (digits, index)
  lazy val fib2 = 
    (0, 0) #:: (fib zip Stream.from(1)).map { case (a, b) => (a.toString.length, b) }

  // series of fibonacci indices where digit changes)
  //lazy val fib3: Stream[Int] = 
  //  (fib2 zip fib2.tail).filter { case ((a, _), (b, _)) => a != b }. map { case (a, b) => b._2 }

  val fib2Cache = fib2.takeWhile{ case(a, b) => a <= 5000}.toArray
  val fib3Cache = (fib2 zip fib2.tail).filter { case ((a, _), (b, _)) => a != b }. map { case (a, b) => b._2 }
    
  def main(args: Array[String]): Unit = {
    val in = io.Source.stdin.getLines
    val t = in.next.toInt
    val ns = in.take(t).map(_.toInt)

    ns foreach { n => println(fib3Cache(n - 1)) }

    /*
    println(fib.take(50).mkString(" "))
    println(fib2.take(50).mkString(" "))
    println(fib3.take(5).mkString(" "))
    */
  }
}