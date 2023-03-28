package myDataset.analysisaboutdataset

import org.apache.spark.sql.SparkSession

object ClearDataset extends App{
  val spark = SparkSession.builder()
    .appName("Remove double quotes")
    .master("local[*]")
    .getOrCreate()

  val fileName = "src/main/resources/data/used_cars_data.csv"
  val linesRDD = spark.sparkContext.textFile(fileName)

  val newLinesRDD = linesRDD.map(line => line.replaceAll("\"\"", ""))

  val outputPath = "src/main/resources/data/newCleanDataset.csv"
  newLinesRDD.saveAsTextFile(outputPath)

  // Cierra la sesión de Spark
  spark.stop()
}
