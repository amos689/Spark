import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("spark://master:7077").setAppName("Test")
      .setIfMissing("spark.driver.host", "192.168.1.4")
    val sc = new SparkContext(conf)
    val rdd = sc.textFile("hdfs://master:9000/user/hadoop/input/yarn-site.xml")
    println("行数："+rdd.count())
    //关闭连接
    sc.stop()
  }
}