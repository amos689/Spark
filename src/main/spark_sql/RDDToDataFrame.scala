import org.apache.spark.sql.SparkSession

object RDDToDataFrame {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName("RDDToDataFrame")
      .getOrCreate()
    val sc = spark.sparkContext

    import spark.implicits._
    val rdd = sc.textFile("input/student.txt")
    val result = rdd.map(line => {
      //按逗号切分
      val x = line.split(",")
      (x(0), x(1), x(2), x(3), x(4))
    })
    result.toDF("id","name","age","gender","class").show()
  }
}
