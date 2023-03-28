package myDataset.playground

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object StatisticsFromDatasetInformation extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/used_cars_data.csv")

  private val filterByYear = df
    .filter(col("year").isNotNull)
    .filter(col("year") === 2021)

  println("igual 2021: " + filterByYear.count())
}
