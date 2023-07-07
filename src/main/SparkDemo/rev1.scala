import org.apache.spark.{SparkConf, SparkContext}

object rev1 {
  def main(args: Array[String]) : Unit = {
    val Conf = new SparkConf().setMaster("local").setAppName("rev1")
    val sc = new SparkContext(Conf)

    // 初始化RDD
    val lines = sc.textFile("in")

    // 转化为k,v
    val scores = lines.map(x => (x.split(",")(1), x.split(",")(2).toDouble))

    // 总成绩
    val allScore = scores.reduceByKey(_ + _)
    allScore.collect().foreach(println)

    // 求平均
    val groupRdd = scores.groupByKey()
    val avg = groupRdd.map(x => (x._1, x._2.sum / x._2.size))
    avg.collect().foreach(println)

    // 排序
    val sortRdd = avg.sortBy(_._2)
    sortRdd.collect().foreach(println)

  }

}
