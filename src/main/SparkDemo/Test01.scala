import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test01 {
  def main(args : Array[String]): Unit = {
    // Application
    // Spark框架
    // TODO 建立和Spark框架的连接
    // JDBC:Connection
    val Conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(Conf)
    val listRDD:RDD[Int] = sc.makeRDD(List(1,2,3,4))
    listRDD.collect().foreach{println}
    // TODO 关闭连接
    sc.stop()
  }
}