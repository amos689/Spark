import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}

object rev5 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("rev5").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    val schema = new StructType(Array(
      StructField("id", DataTypes.StringType),
      StructField("name", DataTypes.StringType),
      StructField("gender", DataTypes.StringType),
      StructField("score", DataTypes.IntegerType)
    ))
    val df:DataFrame = spark.read.schema(schema).option("delimiter", " ").format("csv").load("input/score.txt")
    df.createOrReplaceTempView("score") //设置临时视图表名
    spark.sql("select count(name) as MCount from score where gender = 'M'").show()
    spark.sql("select count(name) as FCount from score where gender = 'F'").show()
    spark.sql("select name,gender,score from score where score > 80 order by score desc").show()
  }
}
