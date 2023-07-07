import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object wordCountDS {
  def main(args: Array[String]): Unit = {
    val sparkConf = new
        SparkConf().setMaster("local[2]").setAppName("Streaming")
    //创建Streaming入口并设置间隔5秒取一次数据
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    //从文件中获取数据，生成lines DStream
    val lines = ssc.textFileStream("hdfs://master:9000/")
    //过滤ｌｉｓｉ、ｗａｎｇｗｕ
    val filtered_lines = lines.filter(line=>line.contains("lisi")||line.contains("zhouliu"))
    //打印到控制台
    filtered_lines.print()
    //启动 Spark Streaming 应用程序上下文，并开始监听正在接收的数据 流
    ssc.start()
    //一直运行，直到应用程序被手动停止或发生未知错误才会结束。
    ssc.awaitTermination()
  }
}
