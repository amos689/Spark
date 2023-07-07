import org.apache.spark.sql
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}

object sparksql_score {
  def main(args: Array[String]): Unit = {
    //创建SparkSession
    val spark = SparkSession.builder()
      .master("local")
      .appName("sparksql_score")
      .getOrCreate()
    //创建DataFrame，读取文件,存在问题
    //val df: DataFrame = spark.read.text("input/student.txt")
    //df.show()
    //创建schema
    val schema = new StructType(Array(
      StructField("id",DataTypes.StringType),
      StructField("name",DataTypes.StringType),
      StructField("gender",DataTypes.StringType),
      StructField("score",DataTypes.IntegerType)
    ))
    //重新读取文件
    val df: DataFrame = spark .read
      .schema(schema)
      .option("delimiter"," ")
      .format("csv")
      .load("input/score.txt")
    //df.show()
    df.createOrReplaceTempView("score")
    // 统计人数
    val MCount = spark.sql("select count(name) as MCount from score where gender = 'M'")
    val FCount = spark.sql("select count(name) as FCount from score where gender='F'")
    MCount.show()
    FCount.show()
    // 统计平均
    val MAvg = spark.sql("select AVG(score) as MAvg from score where gender='M'")
    val FAvg = spark.sql("select AVG(score) as FAvg from score where gender='F'")
    MAvg.show()
    FAvg.show()
    // 列出数学分数超过80分且按由高到低排列的成绩表
    val res = spark.sql("select name,gender,score from score where score > 80 order by score desc")
    res.show()
    spark.stop()
  }
}
