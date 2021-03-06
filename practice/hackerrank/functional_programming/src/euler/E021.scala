package euler

object AmicableNumbers  {

  lazy val primes: Stream[Int] =
    2 #:: Stream.from(3, 2).filter(x => primes.takeWhile(_ <= math.sqrt(x)).forall(x % _ != 0))

  // return Seq(prime factors, power)
  def factorize(n: Int): Seq[(Int, Int)] = {

    def divRep(a: Int, b: Int): (Int, Int) = {
      def loop(a: Int, b: Int, count: Int = 0): (Int, Int) =
        if (a % b != 0) (a, count)
        else loop(a / b, b, count + 1)

      loop(a, b)
    }

    val facs = collection.mutable.ListBuffer[(Int, Int)]()

    def loop(a: Int): Unit = {
      val ps = primes.takeWhile(_ <= math.sqrt(a)).filter(a % _ == 0) // small factors

      if (ps.size == 0) { // a is prime
        if (a != 1) facs += ((a, 1))
        return
      } else {
        val next = ps.foldLeft(a) {
          case (a, b) =>
            val (a1, count) = divRep(a, b)
            facs += ((b, count))
            a1
        }
        if (next == 1) return
        else loop(next)
      }
    }

    loop(n)
    facs
  }

  def d(n: Int): Int = {
    val factors = factorize(n).map(x => (0 to x._2).map(math.pow(x._1, _).toInt)).
      foldLeft(Vector(1)) { case (a, b) => a.flatMap { x => b.map(_ * x) } }
    factors.sum - n
  }

  val dCache = collection.mutable.Map[Int, Int]()

  def dc(n: Int): Int = {
    if (dCache.contains(n)) dCache(n)
    else {
      val v = d(n)
      dCache.put(n, v)
      v
    }
  }
  
  lazy val amicables = Stream.from(2).filter { a => dc(a) != a && dc(dc(a)) == a }

  def main(args: Array[String]): Unit = {
    val in = io.Source.stdin.getLines
    val t = in.next.trim.toInt
    val ns = in.take(t).map(_.trim.toInt)

    ns foreach (n => println(amicables.takeWhile(_ < n).sum))
  }
}