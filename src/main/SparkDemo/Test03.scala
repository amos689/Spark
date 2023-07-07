import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test03 {
  def main(args : Array[String]): Unit = {
    // Application
    // Spark框架
    // TODO 建立和Spark框架的连接
    // JDBC:Connection
    val Conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(Conf)
    val RDD1:RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    // 转化因子
    val RDD2 = RDD1.map(_ + 1)
    val RDD3 = RDD1.filter(_>3)
    // 行动因子
    RDD2.collect().foreach(println)
    RDD3.collect().foreach(println)
    // TODO 关闭连接
    sc.stop()
  }
}