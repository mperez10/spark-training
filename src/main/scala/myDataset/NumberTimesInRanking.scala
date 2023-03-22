package myDataset

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object NumberTimesInRanking extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .option("header", "false")
    .option("sep", ";")
    .csv("src/main/resources/data/csvfinal.csv")

  df.toDF("year", "make_name", "total_per_make_name_per_year", "total_car_in_year", "percentage_per_year", "ranking" )
    .filter(col("ranking") <= 3)
    .groupBy(col("make_name"))
    .agg(count("*").alias("total_times_in_top3"))
    .orderBy(col("total_times_in_top3").desc)
    .show()
}
