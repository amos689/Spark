import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test02 {
  def main(args : Array[String]): Unit = {
    // Application
    // Spark框架
    // TODO 建立和Spark框架的连接
    // JDBC:Connection
    val Conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(Conf)
    val fileRDD:RDD[String] = sc.textFile(path = "datas")
    fileRDD.collect().foreach{println}
    // fileRDD.saveAsTextFile(path = "output")
    // 转化算子
    val filterRDD = fileRDD.filter(lines => lines.contains("spark"))
    // 行动算子
    println(filterRDD.count())
    // TODO 关闭连接
    sc.stop()
  }
}