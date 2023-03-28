package myDataset.usedcaranalysis

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.{Column, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object PercentageOfCarBrandsPerYear extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local[*]")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/newCleanDataset.csv")

  val sc = spark.sparkContext

  private val filterByYear = df
    .filter(col("year").isNotNull)
    .filter(col("year") >= 2015)
    .filter(col("year") <= 2021)

  val percentageFormat: Column => Column = (number: Column) => concat(format_number(number * 100, 2), lit(" %"))
  private var labelTotalCars = "total_cars"
  // Group by year and manufacturer's name and count the number of cars.
  val countOfCarsPerMakeNamePerYear = filterByYear
    .groupBy("year", "make_name")
    .agg(count("*").alias(labelTotalCars))

  private val totalPerYear = "total_per_year"
  val totalCarByYear = filterByYear
    .groupBy("year")
    .agg(count("*").alias(totalPerYear))

  //Join between two dataframes, to have the total number of cars per year for make names, aggregated to all rows
  val dfCarsByYearAndMakeNameWithTotalByCarYear = countOfCarsPerMakeNamePerYear.join(totalCarByYear, Seq("year"))

  //Add percentage column
  val dfCarWithPercentageByYear = dfCarsByYearAndMakeNameWithTotalByCarYear
    .withColumn("percentage", percentageFormat(col(labelTotalCars) / col(totalPerYear)))

  // Sort by year and number of cars in descending order
  val dfCarWithPercentageByYearDesc = dfCarWithPercentageByYear.orderBy(desc("year"), desc(labelTotalCars))

  val dfCarMakeNameWithRankingByYear = dfCarWithPercentageByYearDesc
    .withColumn("rank", rank().over(Window.partitionBy("year")
      .orderBy(desc(labelTotalCars))))
    .orderBy(desc("year"), asc("rank"))

  dfCarMakeNameWithRankingByYear.show(600)

  /*
  // Write a csv in columnar form to a single file
  //val rows = withRank.collect().map(_.toSeq.map(_.toString))
  val rows = dfCarMakeNameWithRankingByYear.collect().map(_.toSeq.map {
    case d: Double => d.toString.replace(".", ",")
    case other => other.toString
  })
  val csvData = rows.map(_.mkString(";")).mkString("\n")

  sc
    .parallelize(Seq(csvData), 1)
    .coalesce(1)
    .saveAsTextFile("src/main/resources/data/csvfinal.csv")


   */
  spark.stop()
}
