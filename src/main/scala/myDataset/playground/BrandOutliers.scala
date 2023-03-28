package myDataset.playground

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object BrandOutliers extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/used_cars_data.csv")

  val sc = spark.sparkContext

  val makeCounts = df.groupBy("make_name").count().orderBy(col("count").desc)
  val sortedMakes = makeCounts.filter(col("count") <= 100)

  //yearOutliers.show()
  //sortedMakes.show()
  //println("Total outliers: " + sortedMakes.count())
  // Write a csv in columnar form to a single file
  //val rows = withRank.collect().map(_.toSeq.map(_.toString))
  val rows = sortedMakes.collect().map(_.toSeq.map {
    case d: Double => d.toString.replace(".", ",")
    case other => other.toString
  })
  val csvData = rows.map(_.mkString(";")).mkString("\n")

  sc
    .parallelize(Seq(csvData), 1)
    .coalesce(1)
    .saveAsTextFile("src/main/resources/data/BrandsOutliers.csv")

  spark.stop()
}
