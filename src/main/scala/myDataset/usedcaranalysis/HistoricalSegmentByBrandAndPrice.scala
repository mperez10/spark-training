package myDataset.usedcaranalysis

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Column, SparkSession}

object HistoricalSegmentByBrandAndPrice extends App{

  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application SegmentByPrice")
    .getOrCreate()

  val data = spark.read.format("csv")
    .schema(CarSchema.schema)
    .option("header", "true")
    .load("src/main/resources/data/newCleanDataset.csv")
    .filter(col("year").isNotNull)
    .filter(col("year") >= 2015)
    .filter(col("year") <= 2021)

  val totalRows = data.count()

  val percentageFormat: Column => Column = (number: Column) => concat(format_number(number * 100, 2), lit(" %"))

  val lowPrice = 20000
  val highPrice = 40000

  def getPriceSegment(price: Double): String = {
    if (price < lowPrice) {
      "Low"
    } else if (price >= lowPrice && price < highPrice) {
      "Medium"
    } else {
      "High"
    }
  }

  val priceSegmentUDF = udf(getPriceSegment _)
  val priceSegmentType = StringType

  val segmentedData = data
    .withColumn("price_segment", priceSegmentUDF(col("price")).cast(priceSegmentType))

  //segmentedData.select("make_name", "year", "price", "price_segment").show()
  val segmentedDataByMakeNameAndPrice = segmentedData.groupBy("make_name", "price_segment")
    .agg(
      round(avg("price"), 2).alias("avg_price"),
      count("make_name").alias("num_models")
    )
    .withColumn("percentage", percentageFormat(col("num_models") / totalRows))
    .filter(col("num_models") > 1000)
    .orderBy(col("num_models").desc)

  segmentedDataByMakeNameAndPrice.show(100)

}
