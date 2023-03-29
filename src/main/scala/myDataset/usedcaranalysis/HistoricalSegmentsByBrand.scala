package myDataset.usedcaranalysis

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Column, SparkSession}

object HistoricalSegmentsByBrand extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("HistoricalSegmentsByBrand Application")
    .getOrCreate()
  val data = spark.read.format("csv")
    .schema(CarSchema.schema)
    .option("header", "true")
    .load("src/main/resources/data/newCleanDataset.csv")

  val totalRows = data.count()
  val percentageFormat: Column => Column = (number: Column) => concat(format_number(number * 100, 2), lit(" %"))
  val segmentedDataByMakeNameAndPrice = data.groupBy("make_name", "body_type")
    .agg(
      count("*").alias("num_models")
    )
    .withColumn("percentage", percentageFormat(col("num_models") / totalRows))
    .filter(col("body_type").isNotNull)
    .filter(col("num_models") > 1000)
    .orderBy(col("num_models").desc)

  segmentedDataByMakeNameAndPrice.show(600)

}
