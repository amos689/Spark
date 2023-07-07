import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object rev6 {
  def main(args: Array[String]): Unit = {
    val Conf = new SparkConf().setMaster("local[2]").setAppName("rev6")
    val ssc = new StreamingContext(Conf, Seconds(5))

    val lines = ssc.textFileStream("hdfs://master:9000/")

    val end = lines.filter(x => x.contains("lisi")||x.contains("zhaoliu"))
    end.print()

    ssc.start()

    ssc.awaitTermination()
  }

}
