import org.apache.spark.sql.SparkSession

object rev3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("rev3").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    val rdd = sc.textFile("input/student.txt")
    val res = rdd.map(x => {
      val y = x.split(",")
      (y(0), y(1), y(2), y(3), y(4))
    }).toDF("id","name","age","gender","class").show()
  }

}
