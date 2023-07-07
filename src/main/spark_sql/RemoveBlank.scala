import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.StructType

object RemoveBlank {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("JDBC_Mysql")
      .getOrCreate()

    val jdbcDF = spark.read
      .format("jdbc")
      .option("url", "jdbc:mysql://192.168.2.161:3306")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "root")
      .option("password", "123456")
      .option("dbtable", "information_schema.tables")
      .load()

    val tables = jdbcDF.select("TABLE_NAME")
      .where("TABLE_SCHEMA = 'XinChuang' and TABLE_NAME not like 'new\\_%'")
      .collect()
      .map(_.getString(0))
    tables.foreach(table => {
      // 处理每个表
      //      if(table == "comments"){
      //      }else{
      Blank(table, spark)
      //      }
    })
  }

  def Blank(table:String, spark: SparkSession):Unit={
    //连接mysql
    val df: DataFrame = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://192.168.2.161:3306/XinChuang")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "root")
      .option("password", "123456")
      .option("dbtable", table)
      .load()
    //处理空值
    val dfWithoutNull: DataFrame = df.na.drop()
    //获取表的结构信息
    val schema: StructType = dfWithoutNull.schema
    //生成createTableColumnTypes参数
    val columnTypes: String = schema.fields.map(field => {
      val dataType = field.dataType.typeName match {
        case "integer" => "INT"
        case "string" => "VARCHAR(255)"
        case "timestamp" => "TIMESTAMP"
        case "boolean" => "BOOLEAN"
        case "double" => "DOUBLE"
        case "text" => "TEXT"
        case "decimal" => "DECIMAL(10,2)"
        case _ => "VARCHAR(255)"
      }
      s"${field.name} $dataType"
    }).mkString(", ")
    //保存到mysql
    dfWithoutNull.write.format("jdbc")
      .option("url", "jdbc:mysql://192.168.2.161:3306/XinChuang")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "root")
      .option("password", "123456")
      .option("createTableOptions", "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4")
      .option("createTableColumnTypes", columnTypes)
      .option("dbtable", "new_"+table)
      .mode("overwrite")
      .save()
    dfWithoutNull.show()
  }
}