import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test04 {
  def main(args : Array[String]): Unit = {
    // Application
    // Spark框架
    // TODO 建立和Spark框架的连接
    // JDBC:Connection
    val Conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(Conf)
    val RDD1:RDD[String] = sc.textFile(path = "datas")
    // 转化因子
    val RDD2:RDD[String] = RDD1.flatMap(_.split(" "))
    // 行动因子
    RDD2.collect().foreach(println)
    // TODO 关闭连接
    sc.stop()
  }
}