package myDataset
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object CarApplication extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/used_cars_data.csv")

  private val filterByYear = df
    .filter(col("year").isNotNull)
    .filter(col("year") >= 2015)
    .filter(col("year") <= 2021)

  // Group by year and manufacturer's name and count the number of cars.
  val countOfCarsPerMakeNamePerYear = filterByYear
    .groupBy("year", "make_name")
    .agg(count("*").alias("cantidad_autos"))

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

  val dfCarMakeNameWithRankingByYear = dfCarWithPercentageByYearDesc
    .withColumn("rank", rank().over(Window.partitionBy("year")
      .orderBy(desc("cantidad_autos"))))
    .orderBy(desc("year"), asc("rank"))

  dfCarMakeNameWithRankingByYear.show(600)

  /*
  // Escribir un csv en forma de columnas en un sólo archivo
  //val rows = withRank.collect().map(_.toSeq.map(_.toString))
  val rows = withRank.collect().map(_.toSeq.map {
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
