package myDataset.analysisaboutdataset

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object DatasetAnalysisWithDescribe extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/newCleanDataset.csv")

  private val filterByYear = df
    .filter(col("year").isNotNull)

  filterByYear.describe("price", "horsepower", "year").show()

  /*
  df.describe(
    "vin",
    "body_type",
    "cabin",
    "city",
    "city_fuel_economy",
    "engine_cylinders",
    "engine_displacement",
    "engine_type",
    "exterior_color",
    "fuel_tank_volume",
    "fuel_type",
    "height",
    "horsepower",
    "interior_color",
    "isCab",
    "length",
    "listed_date",
    "listing_color",
    "listing_id",
    "longitude",
    "main_picture_url",
    "make_name",
    "maximum_seating",
    "model_name",
    "power",
    "price",
    "salvage",
    "torque",
    "transmission",
    "transmission_display",
    "vehicle_damage_category",
    "wheel_system",
    "wheel_system_display",
    "wheelbase",
    "width",
    "year")


   */

}
