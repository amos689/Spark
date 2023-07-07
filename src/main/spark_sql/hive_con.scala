import org.apache.spark.sql.SparkSession

object hive_con {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName("sparksql_score")
      .enableHiveSupport()
      .getOrCreate()
    spark.sql("show tables").show
    spark.stop()
  }
}
