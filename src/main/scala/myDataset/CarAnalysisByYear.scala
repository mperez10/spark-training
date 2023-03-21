package myDataset

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object CarAnalysisByYear extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/used_cars_data.csv")

  val sc = spark.sparkContext

  val totalRowsDataset = df.filter(col("year") >= 2015 and col("year") <= 2021).count()

  val year2015 = df
    .filter(col("year") === 2015)
    .agg(count("*").alias("total_cars_per_year"))
    .withColumn("year", lit("2015"))
    .withColumn("percentage_of_dataset", round(col("total_cars_per_year") / totalRowsDataset * 100, 2))
    .select(col("year"), col("total_cars_per_year"), col("percentage_of_dataset"))

  val year2016 = df
    .filter(col("year") === 2016)
    .agg(count("*").alias("total_cars_per_year"))
    .withColumn("year", lit("2016"))
    .withColumn("percentage_of_dataset", round(col("total_cars_per_year") / totalRowsDataset * 100, 2))
    .select(col("year"), col("total_cars_per_year"), col("percentage_of_dataset"))

  val year2017 = df
    .filter(col("year") === 2017)
    .agg(count("*").alias("total_cars_per_year"))
    .withColumn("year", lit("2017"))
    .withColumn("percentage_of_dataset", round(col("total_cars_per_year") / totalRowsDataset * 100, 2))
    .select(col("year"), col("total_cars_per_year"), col("percentage_of_dataset"))

  val year2018 = df
    .filter(col("year") === 2018)
    .agg(count("*").alias("total_cars_per_year"))
    .withColumn("year", lit("2018"))
    .withColumn("percentage_of_dataset", round(col("total_cars_per_year") / totalRowsDataset * 100, 2))
    .select(col("year"), col("total_cars_per_year"), col("percentage_of_dataset"))

  val year2019 = df
    .filter(col("year") === 2019)
    .agg(count("*").alias("total_cars_per_year"))
    .withColumn("year", lit("2019"))
    .withColumn("percentage_of_dataset", round(col("total_cars_per_year") / totalRowsDataset * 100, 2))
    .select(col("year"), col("total_cars_per_year"), col("percentage_of_dataset"))

  val year2020 = df
    .filter(col("year") === 2020)
    .agg(count("*").alias("total_cars_per_year"))
    .withColumn("year", lit("2020"))
    .withColumn("percentage_of_dataset", round(col("total_cars_per_year") / totalRowsDataset * 100, 2))
    .select(col("year"), col("total_cars_per_year"), col("percentage_of_dataset"))

  val year2021 = df
    .filter(col("year") === 2021)
    .agg(count("*").alias("total_cars_per_year"))
    .withColumn("year", lit("2021"))
    .withColumn("percentage_of_dataset", round(col("total_cars_per_year") / totalRowsDataset * 100, 2))
    .select(col("year"), col("total_cars_per_year"), col("percentage_of_dataset"))

  year2015.union(year2016)
    .union(year2017)
    .union(year2018)
    .union(year2019)
    .union(year2020)
    .union(year2021)
    .orderBy(col("year").desc)
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
