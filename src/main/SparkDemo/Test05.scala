import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test05 {
  def main(args : Array[String]): Unit = {
    // Application
    // Spark框架
    // TODO 建立和Spark框架的连接
    // JDBC:Connection
    val Conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(Conf)
    val RDD1:RDD[Int] = sc.parallelize(Array(1, 2, 3, 4))
    val RDD2:RDD[Int] = sc.parallelize(Array(3, 4, 5, 6))
    // 转化因子
    val RDD3 = RDD1.union(RDD2)
    RDD3.distinct()
    // 行动因子
    RDD3.collect().foreach(println)
    // TODO 关闭连接
    sc.stop()
  }
}