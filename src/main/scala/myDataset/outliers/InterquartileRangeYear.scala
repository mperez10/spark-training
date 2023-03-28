package myDataset.outliers

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object InterquartileRangeYear extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application Interquartile Range with year")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/newCleanDataset.csv")

  //df.select(min("year"), max("year")).show()

  val numericYearsDF = df.withColumn("numeric_year", col("year") - 1914)

  val quantiles = numericYearsDF.stat.approxQuantile("numeric_year", Array(0.25, 0.5, 0.75), 0.01)
  val Q1 = quantiles(0)
  val Q3 = quantiles(2)
  val IQR = Q3 - Q1
  val lowerLimit = Q1 - 1.5 * IQR
  val upperLimit = Q3 + 1.5 * IQR

  println(s"Min: ${1914 + lowerLimit} Max: ${1914 + upperLimit}")

  //val outliersDF = df.filter(col("year") < lowerLimit || col("year") > upperLimit)
  //outliersDF.show()
}
