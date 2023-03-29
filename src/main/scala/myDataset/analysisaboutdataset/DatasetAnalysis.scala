package myDataset.analysisaboutdataset

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object DatasetAnalysis extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local[*]")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/newCleanDataset.csv")

  val sc = spark.sparkContext

  val totalRowsDataset = df.count()

  val yearCounts = df.groupBy(coalesce(col("year"), lit("N/A"))
    .alias("year")).count().orderBy(col("count").desc)

  import spark.implicits._
  val totalDataset = Seq(("Full Dataset", totalRowsDataset, 100.00)).toDF("type_of_data", "total_rows", "percentage_of_dataset")

  val cleanInformation = yearCounts
    .filter(col("year") >= 2012 and col("year") <= 2024 )
    .agg(sum("count").alias("total_rows"))
    .withColumn("type_of_data", lit("Useful"))
    .withColumn("percentage_of_dataset", round(col("total_rows") / totalRowsDataset * 100, 2))
    .select(col("type_of_data"), col("total_rows"), col("percentage_of_dataset"))

  val yearOutliers = yearCounts
    .filter(col("year") < 2012 or col("year") > 2024 )
    .agg(sum("count").alias("total_rows"))
    .withColumn("type_of_data", lit("Outlier"))
    .withColumn("percentage_of_dataset", round(col("total_rows") / totalRowsDataset * 100, 2))
    .select(col("type_of_data"), col("total_rows"), col("percentage_of_dataset"))

  val yearNulls = yearCounts
    .filter(col("year") === "N/A")
    .agg(sum("count").alias("total_rows"))
    .withColumn("type_of_data", lit("N/A"))
    .withColumn("percentage_of_dataset", round(col("total_rows") / totalRowsDataset * 100, 2))
    .select(col("type_of_data"), col("total_rows"), col("percentage_of_dataset"))

  totalDataset.union(cleanInformation).union(yearOutliers).union(yearNulls).show()

}
