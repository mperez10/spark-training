package myDataset

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object EvolutionOfTheThreeBrandsThatDominateTheMarketIn2021 extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/used_cars_data.csv")

  private val filterByYear2021 = df
    .filter(col("year").isNotNull)
    .filter(col("year") === 2021)

  // Group by year and manufacturer's name and count the number of cars.
  val countOfCarsPerMakeName = filterByYear2021
    .groupBy("make_name")
    .agg(count("*").alias("total_car_per_make_name"))

  // Ordenar por total de ventas descendente
  val df_sorted = countOfCarsPerMakeName.orderBy(desc("total_car_per_make_name"))

  // Obtener las primeras 3 marcas por total de ventas
  val df_top3 = df_sorted.limit(3)

  df_top3.show(600)

  /**
    * SECOND PART TO GET THREE MAKE NAME WHICH ARE THE MAINLY OWNER IN THE MARKET
    */

  //Filter by year and per top 3 make names with more cars
  private val filterByYear = df
    .filter(col("year").isNotNull)
    .filter(col("year") >= 2015)
    .filter(col("year") <= 2021)

  val filterPerYearAndThreeMakeNameWithMoreCars = filterByYear.join(df_top3, "make_name")

  // Group by year and manufacturer's name and count the number of cars.
  val countOfCarsPerMakeNamePerYear = filterPerYearAndThreeMakeNameWithMoreCars
    .groupBy("year", "make_name")
    .agg(count("*").alias("cantidad_autos"))

  //Group total car per year
  val totalCarByYear = filterByYear
    .groupBy("year")
    .agg(count("*").alias("total_per_year"))

  //Join between two dataframes, to have the total number of cars per year for make names, aggregated to all rows
  val dfCarsByYearAndMakeNameWithTotalByCarYear = countOfCarsPerMakeNamePerYear.join(totalCarByYear, Seq("year"))

  //Add percentage column
  val dfCarWithPercentageByYear = dfCarsByYearAndMakeNameWithTotalByCarYear
    .withColumn("porcentaje", col("cantidad_autos") / col("total_per_year") * 100)

  // Ordenar por año y cantidad de autos en orden descendente
  val dfCarWithPercentageByYearDesc = dfCarWithPercentageByYear.orderBy(desc("year"), desc("cantidad_autos"))

  //Si tuviera
  val dfCarMakeNameWithRankingByYear = dfCarWithPercentageByYearDesc
    .withColumn("rank", rank().over(Window.partitionBy("year")
      .orderBy(desc("cantidad_autos"))))
    .orderBy(desc("year"), asc("rank"))

  dfCarMakeNameWithRankingByYear.show(600)

  /*
  // Escribir un csv en forma de columnas en un sólo archivo
  //val rows = withRank.collect().map(_.toSeq.map(_.toString))
  val rows = dfCarMakeNameWithRankingByYear.collect().map(_.toSeq.map {
    case d: Double => d.toString.replace(".", ",")
    case other => other.toString
  })
  val csvData = rows.map(_.mkString(";")).mkString("\n")

  sc
    .parallelize(Seq(csvData), 1)
    .coalesce(1)
    .saveAsTextFile("src/main/resources/data/csvWith3MarketDominateTheMarketIn2021.csv")


   */
  spark.stop()
}
