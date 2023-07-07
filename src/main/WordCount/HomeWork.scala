import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object HomeWork {
  def main(args: Array[String]): Unit = {
    // 连接 Spark 集群
    val conf = new SparkConf().setMaster("spark://master:7077").setAppName("RemoteFileWordCount")
      .setIfMissing("spark.driver.host", "192.168.1.4").setJars(List("target/Spark-1.0-SNAPSHOT.jar"))
    val sc = new SparkContext(conf)

    // 读取远程文件
    val file: RDD[String] = sc.textFile("hdfs://master:9000/myhdfs/test.txt")
    val words: RDD[String] = file.flatMap(_.split(" "))
    val wordToOne: RDD[(String, Int)] = words.map(x => (x,1))
    val wordToSum: RDD[(String, Int)] = wordToOne.reduceByKey(_+_)
    val array = wordToSum.collect()
    array.foreach(println)

    // 关闭 Spark 连接
    sc.stop()
  }
}
