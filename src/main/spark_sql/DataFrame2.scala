import org.apache.spark.sql
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}

object DataFrame2 {
  def main(args: Array[String]): Unit = {
    //创建SparkSession
    val spark = SparkSession.builder()
      .master("local")
      .appName("DataFrame2")
      .getOrCreate()
    //创建DataFrame，读取文件,存在问题
    //val df: DataFrame = spark.read.text("input/student.txt")
    //df.show()
    //创建schema

    val schema = new StructType(Array(
      StructField("id",DataTypes.StringType),
      StructField("name",DataTypes.StringType),
      StructField("age",DataTypes.IntegerType),
      StructField("gender",DataTypes.StringType),
      StructField("class",DataTypes.StringType)
    ))
    //重新读取文件
    val df: DataFrame = spark .read
      .schema(schema)
      .option("seq",",")
      .format("csv")
      .load("input/student.txt")
    df.sort(df("age").desc).show()
  }
}
