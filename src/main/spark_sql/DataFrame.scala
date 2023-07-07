import org.apache.spark.sql
import org.apache.spark.sql.{DataFrame, SparkSession}

object DataFrame {
  def main(args: Array[String]): Unit = {
    //创建
    val spark = new sql.SparkSession.Builder().master("local").appName("DataFrame")
        .getOrCreate()
    //创建DataFrame，读取文件
    val df:DataFrame = spark.read.json("input/employee.json")
    //展示5行
    df.show(5)
    //去重
    df.distinct().show()
    //过滤
    df.filter("age>25").show()
    //去重过滤
    df.distinct().filter("age>25").show()
    //不等于、等于
    //错误df.filter("name!=lisi").show()
    //错误df.filter(df("name")!="lisi").show()
    //不等于lisi
    df.filter(df("name")=!="lisi").show()
    //等于lisi
    df.filter(df("name")==="lisi").show()
    //排序ascend, descend
    df.sort(df("name").asc).show()
    df.sort(df("name").desc).show()
    //分组
    df.groupBy(df("name")).count().show()
    //列别名
    df.select(df("id").as("eno"), df("name").as("ename")).show()
    //求平均年龄
    df.agg("age"->"avg").show()
    //求最大年龄
    df.agg("age" -> "max").show()
    //求最小年龄
    df.agg("age" -> "min").show()
  }
}
