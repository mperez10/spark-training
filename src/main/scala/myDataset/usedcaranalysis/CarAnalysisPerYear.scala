package myDataset.usedcaranalysis

import myDataset.analysisaboutdataset.CarSchema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object CarAnalysisPerYear extends App{
  val spark = SparkSession.builder()
    .config("spark.master", "local[*]")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/newCleanDataset.csv")

  val sc = spark.sparkContext

  private val labelYear = "year"
  val totalRowsDataset = df.filter(col(labelYear) >= 2014 and col(labelYear) <= 2021).count()

  private val percentageOfDataset = "usefull_percentage_of_dataset"
  private val labelTotalPerYear = "total_cars_per_year"
  val year2014 = df
    .filter(col(labelYear) === 2014)
    .agg(count("*").alias(labelTotalPerYear + labelYear))
    .withColumn(labelYear, lit("2014"))
    .withColumn(percentageOfDataset, round(col(labelTotalPerYear + labelYear) / totalRowsDataset * 100, 2))
    .select(col(labelYear), col(labelTotalPerYear + labelYear), col(percentageOfDataset))

  val year2015 = df
    .filter(col(labelYear) === 2015)
    .agg(count("*").alias(labelTotalPerYear + labelYear))
    .withColumn(labelYear, lit("2015"))
    .withColumn(percentageOfDataset, round(col(labelTotalPerYear + labelYear) / totalRowsDataset * 100, 2))
    .select(col(labelYear), col(labelTotalPerYear + labelYear), col(percentageOfDataset))

  val year2016 = df
    .filter(col(labelYear) === 2016)
    .agg(count("*").alias(labelTotalPerYear + labelYear))
    .withColumn(labelYear, lit("2016"))
    .withColumn(percentageOfDataset, round(col(labelTotalPerYear + labelYear) / totalRowsDataset * 100, 2))
    .select(col(labelYear), col(labelTotalPerYear + labelYear), col(percentageOfDataset))

  val year2017 = df
    .filter(col(labelYear) === 2017)
    .agg(count("*").alias(labelTotalPerYear + labelYear))
    .withColumn(labelYear, lit("2017"))
    .withColumn(percentageOfDataset, round(col(labelTotalPerYear + labelYear) / totalRowsDataset * 100, 2))
    .select(col(labelYear), col(labelTotalPerYear + labelYear), col(percentageOfDataset))

  val year2018 = df
    .filter(col(labelYear) === 2018)
    .agg(count("*").alias(labelTotalPerYear + labelYear))
    .withColumn(labelYear, lit("2018"))
    .withColumn(percentageOfDataset, round(col(labelTotalPerYear + labelYear) / totalRowsDataset * 100, 2))
    .select(col(labelYear), col(labelTotalPerYear + labelYear), col(percentageOfDataset))

  val year2019 = df
    .filter(col(labelYear) === 2019)
    .agg(count("*").alias(labelTotalPerYear + labelYear))
    .withColumn(labelYear, lit("2019"))
    .withColumn(percentageOfDataset, round(col(labelTotalPerYear + labelYear) / totalRowsDataset * 100, 2))
    .select(col(labelYear), col(labelTotalPerYear + labelYear), col(percentageOfDataset))

  val year2020 = df
    .filter(col(labelYear) === 2020)
    .agg(count("*").alias(labelTotalPerYear + labelYear))
    .withColumn(labelYear, lit("2020"))
    .withColumn(percentageOfDataset, round(col(labelTotalPerYear + labelYear) / totalRowsDataset * 100, 2))
    .select(col(labelYear), col(labelTotalPerYear + labelYear), col(percentageOfDataset))

  val year2021 = df
    .filter(col(labelYear) === 2021)
    .agg(count("*").alias(labelTotalPerYear + labelYear))
    .withColumn(labelYear, lit("2021"))
    .withColumn(percentageOfDataset, round(col(labelTotalPerYear + labelYear) / totalRowsDataset * 100, 2))
    .select(col(labelYear), col(labelTotalPerYear + labelYear), col(percentageOfDataset))

  year2014
    .union(year2015)
    .union(year2016)
    .union(year2017)
    .union(year2018)
    .union(year2019)
    .union(year2020)
    .union(year2021)
    .orderBy(col(labelYear).desc)
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
