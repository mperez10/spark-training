package myDataset.playground

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object StudyInYearWithNulls extends App{

  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    //.schema(CarSchema.schema)
    .option("header", "true")
    //.option("inferSchema", "true")
    .csv("src/main/resources/data/newCleanDataset.csv")

  df.show()
  //val myValues = dfFilter.select("description").collect().map(_.getString(0))

  //println("Texto MP: " + myValues(0))
/*
  // Escribir el DataFrame como archivo CSV
  val sc = spark.sparkContext
  // Write a csv in columnar form to a single file
  //val rows = withRank.collect().map(_.toSeq.map(_.toString))
  val rows = dfFilNotNull.collect().map(_.toSeq.map {
    case d: Double => d.toString.replace(".", ",")
    case null => ""
    case other => other.toString
  })
  val csvData = rows.map(_.mkString(";")).mkString("\n")

  sc
    .parallelize(Seq(csvData), 1)
    .coalesce(1)
    .saveAsTextFile("src/main/resources/data/yearWithNulls.csv")
  //dfFilter.show()

  //
  */

  spark.stop()

}
