import org.apache.spark.{SparkConf, SparkContext}

object rev2 {
  def main(args: Array[String]): Unit = {
    val Conf = new SparkConf().setMaster("local").setAppName("rev2")
    val sc = new SparkContext(Conf)
    val linesRDD = sc.textFile("datas")

    val wordsRDD = linesRDD.flatMap(_.split(" ")).map(x => (x, 1)).reduceByKey(_+_).collect().foreach(println)
    val wordsRDD1 = linesRDD.map(line=>{
      val x = line.split(" ")
      (x(0), x(1))
    }).collect().foreach(println)
    val wordsRDD2 = linesRDD.map(line => line.split(" ")).collect().foreach(println)
  }

}
