package myDataset.playground

import org.apache.spark.sql.SparkSession

object CsvToParquetConverter {
  def main(args: Array[String]) {
    val spark = SparkSession.builder()
      .appName("CsvToParquetConverter")
      .master("local")
      .getOrCreate()

    val csvFile = "src/main/resources/data/used_cars_data.csv"
    val parquetFile = "src/main/resources/data/cars_data/used_cars_data.parquet"

    val data = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(csvFile)

    data.write.format("parquet").save(parquetFile)

    spark.stop()
  }
}