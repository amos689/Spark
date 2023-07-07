import org.apache.spark.{SparkConf, SparkContext}

object HomeWork2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("spark://master:7077").setAppName("Student")
      .setIfMissing("spark.driver.host", "192.168.1.4").setJars(List("target/Spark-1.0-SNAPSHOT.jar"))
    val sc = new SparkContext(conf)

    val lines = sc.textFile("hdfs://master:9000/myhdfs/Student.txt") // 输入数据文件路径
    val data = lines.map(line => line.split(" ")) // 分隔行并转换为数组
    data.collect().foreach(println)
    println("=========")
    val maleCount = data.filter(row => row(2) == "M").count() // 统计男生人数
    val femaleCount = data.filter(row => row(2) == "F").count() // 统计女生人数

    val maleScores = data.filter(row => row(2) == "M").map(row => row(3).toDouble) // 提取男生成绩并转换为Double类型
    val femaleScores = data.filter(row => row(2) == "F").map(row => row(3).toDouble) // 提取女生成绩并转换为Double类型

    val maleAvgScore = maleScores.mean() // 计算男生平均分
    val femaleAvgScore = femaleScores.mean() // 计算女生平均分

    println(s"男性人数: $maleCount, 女性人数: $femaleCount")
    println(f"男性平均分: $maleAvgScore%.2f, 女性平均分: $femaleAvgScore%.2f")

    sc.stop()
  }
}
