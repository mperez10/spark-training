package myDataset.usedcaranalysis

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.{Column, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object Top3MarketBrandRankings2015to2021 extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local[*]")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/newCleanDataset.csv")

  private val labelYear = "year"
  private val filterByYear = df
    .filter(col(labelYear).isNotNull)
    .filter(col(labelYear) >= 2015)
    .filter(col(labelYear) <= 2021)

  private val labelCarPerBrand = "total_car_per_make_name"

  val percentageFormat: Column => Column = (number: Column) => concat(format_number(number * 100, 2), lit(" %"))

  // Group by year and manufacturer's name and count the number of cars.
  val countOfCarsPerMakeName = filterByYear
    .groupBy("make_name")
    .agg(count("*").alias(labelCarPerBrand))

  // Sort by descending sales total
  val df_sorted = countOfCarsPerMakeName.orderBy(desc(labelCarPerBrand))

  // To obtain the first 3 brands by total sales
  val df_top3 = df_sorted.limit(3)

  df_top3.show(600)

  /**
    * SECOND PART TO GET THREE MAKE NAME WHICH ARE THE MAINLY OWNER IN THE MARKET
    */

  //Filter by year and per top 3 make names with more cars
  val filterPerYearAndThreeMakeNameWithMoreCars = filterByYear.join(df_top3, "make_name")

  private val labelTotalCars = "total_cars"
  // Group by year and manufacturer's name and count the number of cars.
  val countOfCarsPerMakeNamePerYear = filterPerYearAndThreeMakeNameWithMoreCars
    .groupBy(labelYear, "make_name")
    .agg(count("*").alias(labelTotalCars))

  //Group total car per year
  val totalCarByYear = filterByYear
    .groupBy(labelYear)
    .agg(count("*").alias("total_per_year"))

  //Join between two dataframes, to have the total number of cars per year for make names, aggregated to all rows
  val dfCarsByYearAndMakeNameWithTotalByCarYear = countOfCarsPerMakeNamePerYear.join(totalCarByYear, Seq(labelYear))

  private val labelPercentage = "percentage"
  //Add percentage column
  val dfCarWithPercentageByYear = dfCarsByYearAndMakeNameWithTotalByCarYear
    .withColumn(labelPercentage, percentageFormat(col(labelTotalCars) / col("total_per_year")))
    //.withColumn(labelPercentage, col(labelTotalCars) / col("total_per_year" + labelYear) * 100)

  // Sort by year and number of cars in descending order
  val dfCarWithPercentageByYearDesc = dfCarWithPercentageByYear.orderBy(desc(labelYear), desc(labelTotalCars))

  val dfCarMakeNameWithRankingByYear = dfCarWithPercentageByYearDesc
    .withColumn("rank", rank().over(Window.partitionBy(labelYear)
      .orderBy(desc(labelTotalCars))))
    .orderBy(desc(labelYear), asc("rank"))

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
    .saveAsTextFile("src/main/resources/data/csvWith3Market.csv")
   */
  spark.stop()
}
