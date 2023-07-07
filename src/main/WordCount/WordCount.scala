import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    //Application
    //Spark框架
    //TODO 建立和Spark框架的连接
    //JDBC:Connction
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf);
    //TODO 执行业务操作
    //1.读取文件,获取一行一行的数据
    //hello world
    val lines: RDD[String] = sc.textFile("datas")
    //2.将行数据拆分,形成1个1个单词(分词)
    // "hello world"=>hello,world,hello,spark
    val words: RDD[String] = lines.flatMap(_.split(" "))
    //3.给每个单词记做1
    //(hello,1),(world,1)
    val wordToOne: RDD[(String, Int)] = words.map(x => (x,1))
    //4.对单词进行聚合
    //(hello,3) (world,2)
    val wordToSum: RDD[(String, Int)] = wordToOne.reduceByKey(_+_)
    //5.将转换结果采集到控制台打印出来
    val array = wordToSum.collect()
    array.foreach(println)
    //TODO 关闭连接
    sc.stop()
  }
}
/*val words: RDD[String] = rdd.flatMap(_.split(" "))
    val wordToOne: RDD[(String, Int)] = words.map(x => (x,1))
    val wordToSum: RDD[(String, Int)] = wordToOne.reduceByKey(_+_)
    val array = wordToSum.collect()
    array.foreach(println)*/