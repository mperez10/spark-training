package myDataset.playground

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object YearOutliers extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/used_cars_data.csv")

  val sc = spark.sparkContext

  val yearStats = df.select(avg("year"), stddev("year")).first()
  val yearMean = yearStats.getDouble(0)
  val yearStdDev = yearStats.getDouble(1)

  val yearOutliers = df
    .filter(col("year") < yearMean - 3 * yearStdDev || col("year") > yearMean + 3 * yearStdDev)
    .select("year")

  //yearOutliers.show()
  //println("Total outliers: " + yearOutliers.count())

  // Write a csv in columnar form to a single file
  //val rows = withRank.collect().map(_.toSeq.map(_.toString))
  val rows = yearOutliers.collect().map(_.toSeq.map {
    case d: Double => d.toString.replace(".", ",")
    case other => other.toString
  })
  val csvData = rows.map(_.mkString(";")).mkString("\n")

  sc
    .parallelize(Seq(csvData), 1)
    .coalesce(1)
    .saveAsTextFile("src/main/resources/data/YearOutliers.csv")

  spark.stop()
}
