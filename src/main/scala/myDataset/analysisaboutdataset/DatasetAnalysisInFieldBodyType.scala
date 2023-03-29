package myDataset.analysisaboutdataset

import org.apache.spark.sql.{Column, SparkSession}
import org.apache.spark.sql.functions._

object DatasetAnalysisInFieldBodyType extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Application: Analysis In Field Body Type")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/newCleanDataset.csv")
    .filter(col("year").isNotNull)
    .filter(col("year") >= 2015)
    .filter(col("year") <= 2021)

  val percentageFormat: Column => Column = (number: Column) => concat(format_number(number * 100, 2), lit(" %"))
  val total = df.count()

  val grouped = df.groupBy("body_type")
    .agg(count("*").alias("count_by_brand"))

  val mainBrands = grouped
    .withColumn("percentage", percentageFormat(col("count_by_brand") / total))
    .orderBy(col("count_by_brand").desc)
    .filter(col("body_type") =!= "null" and col("count_by_brand") > 1000)

  val otherBrands = grouped
    .filter(col("body_type").isNull or col("count_by_brand") <= 1000)
    .agg(sum("count_by_brand").alias("count_by_brand"))
    .withColumn("percentage", percentageFormat(col("count_by_brand") / total))
    .withColumn("body_type", lit("Other brands"))
    .select(col("body_type"), col("count_by_brand"), col("percentage"))

  mainBrands.union(otherBrands).show(600)
}
