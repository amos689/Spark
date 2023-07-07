
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Score {
  def main(args : Array[String]): Unit = {
    //建立本地连接
    val conf = new SparkConf().setMaster("local").setAppName("student")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")
    //读取文件
    val lines: RDD[String] = sc.textFile("input/score")
    //求学生平均分
    val scoreRDD = lines.map(line => (line.split(",")(1), line.split(",")(3).toDouble))
    val groupRDD = scoreRDD.groupByKey()
    val avgRDD = groupRDD.map(item => (item._1, item._2.sum / item._2.size))
    //排序
    val orderRDD = avgRDD.sortBy(row => row._2, false)
    orderRDD.foreach(println)
    sc.stop()
  }
}