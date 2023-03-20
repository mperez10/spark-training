package myDataset.playground

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object SpecificRowsFromStatics extends App {

  val schema = StructType(Array(
    StructField("make_name", StringType),
    StructField("year", IntegerType),
    StructField("totalCar", IntegerType),
    StructField("porcentaje", DoubleType),
    StructField("ranking", IntegerType)))

  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val rankedPorcentajePorMarcaPorAnio = spark.read
    .schema(schema)
    .option("header", "true")
    //.option("inferSchema", "true")
    .csv("src/main/resources/data/statics/cars.csv")


  rankedPorcentajePorMarcaPorAnio.take(500).foreach(
    x => println(x.getAs[String]("ranking"))
  )
  /*rankedPorcentajePorMarcaPorAnio.select("year").show()
  rankedPorcentajePorMarcaPorAnio.select("totalCar").show()
  rankedPorcentajePorMarcaPorAnio.select("porcentaje").show()
  rankedPorcentajePorMarcaPorAnio.select("ranking").show()

   */
}
