import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object score1 {
  def main(args: Array[String]): Unit = {
    // 建立连接
    val Conf = new SparkConf().setMaster("local").setAppName("score")
    val sc = new SparkContext(Conf)
    sc.setLogLevel("WARN")

    // 读取文件
    val lines: RDD[String] = sc.textFile("in")

    // 去成绩为kv型
    val scoreRDD = lines.map(s => (s.split(",")(1), s.split(",")(2).toDouble))

    // 持久化
    scoreRDD.cache()

    // 总成绩
    val reduceRDD = scoreRDD.reduceByKey(_+_)
    reduceRDD.collect().foreach(println)
    println("============")

    //按成绩降序排序
    val sortRDD = reduceRDD.sortBy(_._2)
    sortRDD.collect().foreach(println)
    println("============")

    //求平均
    val groupRDD = scoreRDD.groupByKey()
    groupRDD.collect().foreach(println)
    println("=============")
    val avgRDD = groupRDD.map(item => (item._1, item._2.sum / item._2.size))
    avgRDD.collect().foreach(println)

    // 关闭连接
    sc.stop()
  }
}
