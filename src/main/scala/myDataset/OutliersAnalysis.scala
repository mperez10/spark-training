package myDataset

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object OutliersAnalysis extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/used_cars_data.csv")

  val sc = spark.sparkContext

  val totalRowsDataset = df.count()

  val lessThanTheYear1886 = df
    .filter(col("year") < 1886)
    .agg(count("*").alias("total"))
    .withColumn("type_of_information", lit("Less than 1886"))
    .withColumn("percentage_of_dataset", round(col("total") / totalRowsDataset * 100, 2))
    .select(col("type_of_information"), col("total"), col("percentage_of_dataset"))

  val olderThanTheYear2021 = df
    .filter(col("year") > 2021)
    .agg(count("*").alias("total"))
    .withColumn("type_of_information", lit("Greater than 2021"))
    .withColumn("percentage_of_dataset", round(col("total") / totalRowsDataset * 100, 2))
    .select(col("type_of_information"), col("total"), col("percentage_of_dataset"))

  val between1886And1949 = df
    .filter(col("year") >= 1886 and col("year") <= 1949)
    .agg(count("*").alias("total"))
    .withColumn("type_of_information", lit("1886 to 1949"))
    .withColumn("percentage_of_dataset", round(col("total") / totalRowsDataset * 100, 2))
    .select(col("type_of_information"), col("total"), col("percentage_of_dataset"))

  val between1950And1999 = df
    .filter(col("year") >= 1950 and col("year") <= 1999)
    .agg(count("*").alias("total"))
    .withColumn("type_of_information", lit("1950 to 1999"))
    .withColumn("percentage_of_dataset", round(col("total") / totalRowsDataset * 100, 2))
    .select(col("type_of_information"), col("total"), col("percentage_of_dataset"))

  val between2000And2014 = df
    .filter(col("year") >= 2000 and col("year") <= 2014)
    .agg(count("*").alias("total"))
    .withColumn("type_of_information", lit("2000 to 2014"))
    .withColumn("percentage_of_dataset", round(col("total") / totalRowsDataset * 100, 2))
    .select(col("type_of_information"), col("total"), col("percentage_of_dataset"))

  val between2014And2021 = df
    .filter(col("year") >= 2015 and col("year") <= 2021)
    .agg(count("*").alias("total"))
    .withColumn("type_of_information", lit("2015 to 2021"))
    .withColumn("percentage_of_dataset", round(col("total") / totalRowsDataset * 100, 2))
    .select(col("type_of_information"), col("total"), col("percentage_of_dataset"))

  val yearNulls = df
    .filter(col("year").isNull)
    .agg(count("*").alias("total"))
    .withColumn("type_of_information", lit("Null"))
    .withColumn("percentage_of_dataset", round(col("total") / totalRowsDataset * 100, 2))
    .select(col("type_of_information"), col("total"), col("percentage_of_dataset"))




  lessThanTheYear1886.union(olderThanTheYear2021)
    .union(between1886And1949)
    .union(between1950And1999)
    .union(between2000And2014)
    .union(between2014And2021)
    .union(yearNulls)
    .orderBy(col("total").desc)
    .show()

  /*
  // Write a csv in columnar form to a single filemake_name
  //val rows = withRank.collect().map(_.toSeq.map(_.toString))
  val rows = yearOutliers.collect().map(_.toSeq.map {
    case d: Double => d.toString.replace(".", ",")
    case other => other.toString
  })
  val csvData = rows.map(_.mkString(";")).mkString("\n")
  sc
    .parallelize(Seq(csvData), 1)
    .coalesce(1)
    .saveAsTextFile("src/main/resources/data/YearOutliersAnalysis.csv")
   */
  spark.stop()
}
