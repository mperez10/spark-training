package myDataset.analysisaboutdataset

import org.apache.spark.sql.types._

object CarSchema {
  val schema = StructType(Array(
    StructField("vin", StringType),
    StructField("back_legroom", StringType),
    StructField("bed", StringType),
    StructField("bed_height", StringType),
    StructField("bed_length", StringType),
    StructField("body_type", StringType),
    StructField("cabin", StringType),
    StructField("city", StringType),
    StructField("city_fuel_economy", FloatType),
    StructField("combine_fuel_economy", StringType),
    StructField("daysonmarket", IntegerType),
    StructField("dealer_zip", IntegerType),
    StructField("description", StringType),
    StructField("engine_cylinders", StringType),
    StructField("engine_displacement", FloatType),
    StructField("engine_type", StringType),
    StructField("exterior_color", StringType),
    StructField("fleet", BooleanType),
    StructField("frame_damaged", BooleanType),
    StructField("franchise_dealer", BooleanType),
    StructField("franchise_make", StringType),
    StructField("front_legroom", StringType),
    StructField("fuel_tank_volume", StringType),
    StructField("fuel_type", StringType),
    StructField("has_accidents", BooleanType),
    StructField("height", StringType),
    StructField("highway_fuel_economy", FloatType),
    StructField("horsepower", FloatType),
    StructField("interior_color", StringType),
    StructField("isCab", BooleanType),
    StructField("is_certified", BooleanType),
    StructField("is_cpo", BooleanType),
    StructField("is_new", BooleanType),
    StructField("is_oemcpo", BooleanType),
    StructField("latitude", FloatType),
    StructField("length", StringType),
    StructField("listed_date", StringType),
    StructField("listing_color", StringType),
    StructField("listing_id", IntegerType),
    StructField("longitude", FloatType),
    StructField("main_picture_url", StringType),
    StructField("major_options", StringType),
    StructField("make_name", StringType),
    StructField("maximum_seating", IntegerType),
    StructField("mileage", IntegerType),
    StructField("model_name", StringType),
    StructField("owner_count", IntegerType),
    StructField("power", IntegerType),
    StructField("price", FloatType),
    StructField("salvage", BooleanType),
    StructField("savings_amount", FloatType),
    StructField("seller_rating", FloatType),
    StructField("sp_id", IntegerType),
    StructField("sp_name", StringType),
    StructField("theft_title", BooleanType),
    StructField("torque", IntegerType),
    StructField("transmission", StringType),
    StructField("transmission_display", StringType),
    StructField("trimId", IntegerType),
    StructField("trim_name", StringType),
    StructField("vehicle_damage_category", StringType),
    StructField("wheel_system", StringType),
    StructField("wheel_system_display", StringType),
    StructField("wheelbase", StringType),
    StructField("width", StringType),
    StructField("year", IntegerType)
  ))

  val schemaAllString = StructType(Array(
    StructField("vin", StringType),
    StructField("back_legroom", StringType),
    StructField("bed", StringType),
    StructField("bed_height", StringType),
    StructField("bed_length", StringType),
    StructField("body_type", StringType),
    StructField("cabin", StringType),
    StructField("city", StringType),
    StructField("city_fuel_economy", FloatType),
    StructField("combine_fuel_economy", StringType),
    StructField("daysonmarket", IntegerType),
    StructField("dealer_zip", IntegerType),
    StructField("description", StringType),
    StructField("engine_cylinders", StringType),
    StructField("engine_displacement", FloatType),
    StructField("engine_type", StringType),
    StructField("exterior_color", StringType),
    StructField("fleet", BooleanType),
    StructField("frame_damaged", BooleanType),
    StructField("franchise_dealer", BooleanType),
    StructField("franchise_make", StringType),
    StructField("front_legroom", StringType),
    StructField("fuel_tank_volume", StringType),
    StructField("fuel_type", StringType),
    StructField("has_accidents", BooleanType),
    StructField("height", StringType),
    StructField("highway_fuel_economy", FloatType),
    StructField("horsepower", FloatType),
    StructField("interior_color", StringType),
    StructField("isCab", BooleanType),
    StructField("is_certified", BooleanType),
    StructField("is_cpo", BooleanType),
    StructField("is_new", BooleanType),
    StructField("is_oemcpo", BooleanType),
    StructField("latitude", FloatType),
    StructField("length", StringType),
    StructField("listed_date", StringType),
    StructField("listing_color", StringType),
    StructField("listing_id", IntegerType),
    StructField("longitude", FloatType),
    StructField("main_picture_url", StringType),
    StructField("major_options", StringType),
    StructField("make_name", StringType),
    StructField("maximum_seating", StringType),
    StructField("mileage", StringType),
    StructField("model_name", StringType),
    StructField("owner_count", StringType),
    StructField("power", StringType),
    StructField("price", StringType),
    StructField("salvage", BooleanType),
    StructField("savings_amount", StringType),
    StructField("seller_rating", StringType),
    StructField("sp_id", StringType),
    StructField("sp_name", StringType),
    StructField("theft_title", StringType),
    StructField("torque", StringType),
    StructField("transmission", StringType),
    StructField("transmission_display", StringType),
    StructField("trimId", StringType),
    StructField("trim_name", StringType),
    StructField("vehicle_damage_category", StringType),
    StructField("wheel_system", StringType),
    StructField("wheel_system_display", StringType),
    StructField("wheelbase", StringType),
    StructField("width", StringType),
    StructField("year", IntegerType)
  ))
}
