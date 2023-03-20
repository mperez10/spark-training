package myDataset.playground

import org.apache.spark.sql.{SparkSession, functions}
import org.apache.spark.sql.functions._

object ExamPractice extends App{

  //Spark Session
  val sparkSession = SparkSession
    .builder()
    .config("spark.master", "local")
    .appName("Exam Practice")
    .getOrCreate()

  //Spark dataframe reader
  val dfModelByCity = sparkSession
    .read
    .option("InferSchema", "true")
    .parquet("src/main/resources/data/cars_data/inner_model_city.parquet")

  val dfModelByFuel = sparkSession
    .read
    .option("InferSchema", "true")
    .parquet("src/main/resources/data/cars_data/inner_model_fuel.parquet")

  /*
  dfModelByFuel.show()
  println("Prueba take: " + dfModelByFuel.take(1).mkString("Array(", ", ", ")"))
  dfModelByFuel.take(1).foreach(x => println("nro " + x.getAs(1) + " letra " + x.getAs(0)))
  dfModelByFuel.take(2).foreach(x => println("nro " + x.getAs(1) + " letra " + x.getAs(0)))
  dfModelByFuel.take(3).foreach(x => println("marca " + x.getAs("make_name") + " fuel " + x.getAs("city_fuel_economy")))
  dfModelByFuel.filter("make_name = 'Jeep'").show()
  dfModelByFuel.filter(col("city_fuel_economy") >= 22).show()
  dfModelByFuel.select("make_name").show()
  dfModelByFuel.selectExpr("make_name = 'Jeep'").show()
  dfModelByFuel.sort("city_fuel_economy").show()
  dfModelByFuel.withColumn("nueva", lit("test")).show()
  dfModelByFuel.withColumnRenamed("city_fuel_economy", "fuel").show()

  dfModelByFuel.select(
    min(col("city_fuel_economy")),
    max(col("city_fuel_economy")),
    count("city_fuel_economy"),
    count("make_name")
  ).show

  dfModelByFuel.groupBy("make_name")
    .agg(count("*").as("totalCar")).show()

   */

  //dfModelByFuel.join(dfModelByCity).show()
  //dfModelByFuel.join(dfModelByCity, "make_name").show()
  //dfModelByFuel.join(dfModelByCity, Seq("make_name"), "left_anti").show()
  //dfModelByFuel.join(dfModelByCity, Seq("make_name"), "left_semi").show()
  //dfModelByFuel.join(dfModelByCity, Seq("make_name"), "outer").show(100)

  /*
  val dfModelByFuelRenamedKeyField = dfModelByFuel.withColumnRenamed("make_name", "make_name_2")
  //dfModelByFuelRenamedKeyField.show()

  dfModelByFuelRenamedKeyField
    .join(dfModelByCity, col("make_name_2") === col("make_name"))
    .drop("make_name")
    .show()

  dfModelByCity.first.getAs[Int]("make_name")

   */
  dfModelByCity.printSchema
  dfModelByCity.printSchema()
}
