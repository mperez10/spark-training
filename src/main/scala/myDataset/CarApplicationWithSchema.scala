package myDataset

import org.apache.spark.sql.{Column, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{functions => func}

object CarApplicationWithSchema extends App {
  val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Car Big Data Application")
    .getOrCreate()

  val df = spark.read
    .schema(CarSchema.schema)
    .option("header", "true")
    .csv("src/main/resources/data/used_cars_data.csv")

  val totalRows = df.count()

  // Creating a new format for the percentages
  val percentageFormat: Column => Column = (number: Column) => concat(format_number(number * 100, 2), lit(" %"))

  // Inspecting missing values
  def countNullsCols(columns: Array[String]): Array[Column] = {
    columns.map(c => {
      count(when(col(c).isNull, c)).alias(c)
    })
  }

  val cols = Seq("vin", "back_legroom", "bed", "bed_height", "bed_length", "body_type", "cabin", "city",
    "city_fuel_economy", "combine_fuel_economy", "daysonmarket", "dealer_zip", "description", "engine_cylinders",
    "engine_displacement", "engine_type", "exterior_color", "fleet", "frame_damaged", "franchise_dealer",
    "franchise_make", "front_legroom", "fuel_tank_volume", "fuel_type", "has_accidents", "height",
    "highway_fuel_economy", "horsepower", "interior_color", "isCab", "is_certified", "is_cpo", "is_new", "is_oemcpo",
    "latitude", "length", "listed_date", "listing_color", "listing_id", "longitude", "main_picture_url",
    "major_options", "make_name", "maximum_seating", "mileage", "model_name", "owner_count", "power", "price",
    "salvage", "savings_amount", "seller_rating", "sp_id", "sp_name", "theft_title", "torque", "transmission",
    "transmission_display", "trimId", "trim_name", "vehicle_damage_category", "wheel_system", "wheel_system_display",
    "wheelbase", "width", "year")

  // Creating a DataFrame with the column names and statistics of null values
  val nullStatsDF = df.select(
    countNullsCols(df.columns): _*)
    .select(
      func.explode(
        func.array(
          cols.map(
            col =>
              func.struct(
                func.lit(col).alias("column_name"),
                func.col(col).alias("null_count")
              )
          ): _*)
      ).alias("v")
    )
    .selectExpr("v.*")
    .withColumn("percentage", percentageFormat(col("null_count") / totalRows))
    .orderBy(col("null_count").desc)


    val sc = spark.sparkContext

    // Write a csv in columnar form to a single file
    //val rows = withRank.collect().map(_.toSeq.map(_.toString))
    val rows = nullStatsDF.collect().map(_.toSeq.map {
      case d: Double => d.toString.replace(".", ",")
      case other => other.toString
    })
    val csvData = rows.map(_.mkString(";")).mkString("\n")

    sc
      .parallelize(Seq(csvData), 1)
      .coalesce(1)
      .saveAsTextFile("src/main/resources/data/percentageofnulls.csv")


  //nullStatsDF.show(70)
}
