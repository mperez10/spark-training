package myDataset.playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, sum}

object SimpleQuestions extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .option("inferSchema", "true")
    .option("header", "true")
    .parquet("src/main/resources/data/cars_data/used_cars_data.parquet")

  private val filterByYear = df
    .filter(col("year").isNotNull)
    .filter(col("year") <= 2021)
    .filter(col("year") >= 2015)

  val MostFuelConsumingBrands = filterByYear
    .groupBy("make_name")
    .agg(sum("city_fuel_economy").as("total_city_fuel_economy_by_make_name"))
    .orderBy(col("total_city_fuel_economy_by_make_name").desc_nulls_last)

  MostFuelConsumingBrands.limit(3).show()

  val MostFuelConsumingBrandsPerYear = filterByYear
    .groupBy("year", "make_name")
    .agg(sum("city_fuel_economy").as("total_city_fuel_economy_by_make_name_and_year"))
    .orderBy(col("total_city_fuel_economy_by_make_name_and_year").desc_nulls_last)

  MostFuelConsumingBrandsPerYear.show(3)
}
