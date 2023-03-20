package myDataset.playground

import myDataset.CarSchema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object CarApplicationFromCSV10GB extends App {

  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val t_ini = System.nanoTime()
  val carDF = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("src/main/resources/data/used_cars_data.csv")
  carDF.printSchema()
  carDF.schema
  val t_fin = System.nanoTime()
  val totalTime = t_fin - t_ini
  println("Total reading time: " + totalTime + "ns")
  /**
    * Questions:
    *
    * 1. Which zones have the most pickups/dropoffs overall?
    * 2. What are the peak hours for taxi?
    * 3. How are the trips distributed by length? Why are people taking the cab?
    * 4. What are the peak hours for long/short trips?
    * 5. What are the top 3 pickup/dropoff zones for long/short trips?
    * 6. How are people paying for the ride, on long/short trips?
    * 7. How is the payment type evolving with time?
    * 8. Can we explore a ride-sharing opportunity by grouping close short trips?
    *
    */

  val totalRowsCarDF = carDF.count()

  /*
  // 0 ¿How many rows?
  val t_ini2 = System.nanoTime()
  println(s"Total rows: $totalRowsCarDF")
  val t_fin2 = System.nanoTime()
  val totalTime2 = t_fin2 - t_ini2
  println("Total reading time to count: " + totalTime2 + "ns")
  */

  /*
  // 1
  val t_ini3 = System.nanoTime()
  val pickupsByCarDF = carDF.groupBy("year")
    .agg(count("*").as("totalCar"))
    //.drop("LocationID", "service_zone")
    .orderBy(col("totalCar").desc_nulls_last)
  pickupsByCarDF.na.drop().show()
  val t_fin3 = System.nanoTime()
  val totalTime3 = t_fin3 - t_ini3
  println("Total reading time to query by year: " + totalTime3 + "ns")
   */

  /*
  val marcasMasGastadoras = carDF
    .groupBy("make_name")
    .agg(sum("city_fuel_economy").as("total_city_fuel_economy_by_make_name"))
    .orderBy(col("total_city_fuel_economy_by_make_name").desc_nulls_last)

  marcasMasGastadoras.show()

   */

  /*
  val marcasMasGastadorasPorAnio = carDF
    .groupBy("year", "make_name")
    .agg(sum("city_fuel_economy").as("total_city_fuel_economy_by_make_name_and_year"))
    .orderBy(col("total_city_fuel_economy_by_make_name_and_year").desc_nulls_last)

  marcasMasGastadorasPorAnio.show(2)

   */

  val porcentajePorAnio = carDF.groupBy("year")
    .agg(count("*").alias("totalCar"))
    .withColumn("porcentaje", col("totalCar") / totalRowsCarDF * 100)
    .orderBy(col("porcentaje").desc_nulls_last)
  porcentajePorAnio.show()

  val porcentajePorMarcaPorAnio = carDF.groupBy("make_name", "year")
    .agg(count("*").alias("totalCar"))
    .withColumn("porcentaje", col("totalCar") / totalRowsCarDF * 100)
    .orderBy(col("year").desc_nulls_last, col("totalCar").desc_nulls_last)

  //Agrego ranking
  val windowSpec = Window.partitionBy("year").orderBy(col("totalCar").desc_nulls_last)
  val rankedPorcentajePorMarcaPorAnio = porcentajePorMarcaPorAnio
    .withColumn("ranking", rank().over(windowSpec))

  //borro nulls
  //rankedPorcentajePorMarcaPorAnio.na.drop().show()
  rankedPorcentajePorMarcaPorAnio.show()
  //porcentajePorMarcaPorAnio.show()

  //Buscar outliers:




  spark.stop()
}
