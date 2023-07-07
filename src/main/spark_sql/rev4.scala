import org.apache.spark.sql.{DataFrame, SparkSession}

object rev4 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("rev4").getOrCreate()
    val df: DataFrame = spark.read.json("input/employee.json")
    //展示5行
    df.show(5)
    //去重
    df.distinct().show()
    //过滤age>25
    df.filter("age>25").show()
    //不等于、等于
    df.filter(df("name")=!="lisi").show()
    df.filter(df("name")==="lisi").show()
    //排序ascend, descend
    df.sort(df("name").asc).show()
    //分组
    df.groupBy(df("name")).count().show()
    //列别名
    df.select(df("name").as("ename")).show()
    //求平均年龄, 最大年龄，最小年龄
    df.agg("age" -> "avg").show()
    df.agg("age" -> "max").show()
  }

}
