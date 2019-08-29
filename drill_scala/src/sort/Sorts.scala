package sort

object Sorts {

  private val MERGESORT_SPLIT_THRESHOLD = 5

  private def swap(a: Int, b: Int, xs: Array[String]): Unit = {
    val x = xs(a)
    xs(a) = xs(b)
    xs(b) = x
  }

  def insertionSort(xs: Array[String]): Unit = {

    for (i <- xs.indices) {
      for (j <- i until 0 by -1) {
        if (xs(j - 1) > xs(j)) swap(j - 1, j, xs)
      }
    }
  }

  def mergeSort(xs: Array[String]): Unit = {

    val buf = Array.fill[String](xs.length)(null)
    Array.copy(xs, 0, buf, 0, xs.length)

    def merge(from: Int, until: Int, cut: Int, in: Array[String], out: Array[String]): Unit = {

      println(s"merge from $from until $until cut $cut")

      println("m i: " + in.mkString(" "))

      var i1 = from
      var i2 = cut

      for (o <- from until until) {
        if (i1 >= cut) {
          out(o) = in(i2)
          i2 += 1
        } else if (i2 >= until) {
          out(o) = in(i1)
          i1 += 1
        } else if (in(i1) < in(i2)) {
          out(o) = in(i1)
          i1 += 1
        } else {
          out(o) = in(i2)
          i2 += 1
        }
      }

      println("m o: " + out.mkString(" "))
    }

    def sort(from: Int, until: Int, in: Array[String], out: Array[String]): Unit = {

      println(s"sort from $from until $until")

      println("s i: " + in.mkString(" "))

      if (until - from < MERGESORT_SPLIT_THRESHOLD) {
        Array.copy(in, from, out, from, until - from)
        for (i <- from until until) {
          for (j <- i until from by -1) {
            if (out(j - 1) > out(j)) swap(j - 1, j, out)
          }
        }
        println("s o: " + out.mkString(" "))
      } else {
        val cut = from + (until - from) / 2

        sort(from, cut, out, in)
        sort(cut, until, out, in)
        merge(from, until, cut, in, out)
      }
    }

    sort(0, xs.length, xs, buf)
    buf.copyToArray(xs)
  }

  def main(args: Array[String]): Unit = {
    val items = io.Source.stdin.getLines.next.trim.split("\\s+")

    println("items: " + items.mkString(" "))

    val buf = Array.fill(items.length)("")
    items.copyToArray(buf)

    insertionSort(buf)
    println("insertion sorted: " + buf.mkString(" "))

    items.copyToArray(buf)

    mergeSort(buf)
    println("merge sorted: " + buf.mkString(" "))
  }

}
