package myDataset.playground

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object CarApplicationFromParquet3GBWithPredefinedSchema extends App {

  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val t_ini = System.nanoTime()
  val carDF = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .option("enforceSchema", "true")
    .parquet("src/main/resources/data/cars_data/used_cars_data.parquet")
  carDF.printSchema()
  val t_fin = System.nanoTime()
  val totalTime = t_fin - t_ini
  println("Total reading time: " + totalTime + "ns")

  /**
    * Questions:
    *
    * 1. The most fuel efficient car models.
    * 2. The car brands that use the most gasoline.
    * 3. City with the largest number of vehicles.
    * 4. Percentage of cars per year.
    * 5. Percentage of card per make name.
    *
    */

  // 0 ¿How many rows?
  val t_ini2 = System.nanoTime()
  val totalRowsCarDF = carDF.count()
  println(s"Total rows: $totalRowsCarDF")
  val t_fin2 = System.nanoTime()
  val totalTime2 = t_fin2 - t_ini2
  println("Total reading time to count: " + totalTime2 + "ns")

  //corregir dataset, varias opciones: ignorar registros o corregirlos (outliers, datos fuera de lo esperado)
  //precio de un vehículo, ver el promedio de precios, y los que están arriba (ej 10 a 20), ver que hacer
  //identificar outliers y darles tratamiento
  // 1
  val t_ini3 = System.nanoTime()

  val marcasMasGastadoras = carDF
    .groupBy("make_name")
    .agg(sum("city_fuel_economy").as("total_city_fuel_economy_by_make_name"))
    .orderBy(col("total_city_fuel_economy_by_make_name").asc_nulls_last)
  marcasMasGastadoras.show()

  val marcasMasGastadorasPorAnio = carDF
    .groupBy("year", "make_name")
    .agg(sum("city_fuel_economy").as("total_city_fuel_economy_by_make_name_and_year"))
    .orderBy(col("total_city_fuel_economy_by_make_name_and_year").asc_nulls_last)
  marcasMasGastadorasPorAnio.show()

  //SACAR EL PORCENTAJE DE CARROS POR MARCA POR AÑO
  /**
    * 2020: porcentaje/cantidad de autos de una marca, por año (ponerle el ranking)
    * Agregar joins en la práctica
    */

  val porcentajePorAnio = carDF.groupBy("year")
    .agg(count("*").alias("totalCar"))
    .withColumn("porcentaje", col("totalCar") / totalRowsCarDF * 100)
    .orderBy(col("porcentaje").desc_nulls_last)
  porcentajePorAnio.show()

  val porcentajePorMarca = carDF.groupBy("make_name")
    .agg(count("*").alias("totalCar"))
    .withColumn("porcentaje", col("totalCar") / totalRowsCarDF * 100)
    .orderBy(col("porcentaje").desc_nulls_last)
  porcentajePorMarca.show()

  val t_fin3 = System.nanoTime()
  val totalTime3 = t_fin3 - t_ini3
  println("Total reading time to query by year: " + totalTime3 + "ns")

  spark.stop()
}
