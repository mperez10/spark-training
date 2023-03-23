## Dataset Exploration
## About Dataset
### Context
The dataset contains 3 million real world used cars details.

### Content
This data was obtained by running a self made crawler on Cargurus inventory in September 2020. The data set has 66 columns, and they are as follows:

| Field Name               | Data Type    | Description                                                                                                      |
|--------------------------|--------------|------------------------------------------------------------------------------------------------------------------|
| vin                       | String       | Vehicle Identification Number is a unique encoded string for every vehicle. Read more at https://www.autocheck.com/vehiclehistory/vin-basics |
| back_legroom             | String       | Legroom in the rear seat.                                                                                        |
| bed                       | String       | Category of bed size (open cargo area) in a pickup truck. Null usually means the vehicle isn't a pickup truck.    |
| bed_height               | String       | Height of bed in inches.                                                                                         |
| bed_length               | String       | Length of bed in inches.                                                                                         |
| body_type                | String       | Body Type of the vehicle, like Convertible, Hatchback, Sedan, etc.                                              |
| cabin                    | String       | Category of cabin size (open cargo area) in a pickup truck, like Crew Cab, Extended Cab, etc.                    |
| city                     | String       | City where the car is listed, like Houston, San Antonio, etc.                                                   |
| city_fuel_economy        | Float        | Fuel economy in city traffic in km per litre.                                                                    |
| combine_fuel_economy     | Float        | Combined fuel economy is a weighted average of City and Highway fuel economy in km per litre.                    |
| daysonmarket             | Integer      | Days since the vehicle was first listed on the website.                                                         |
| dealer_zip               | Integer      | Zipcode of the dealer.                                                                                           |
| description              | String       | Vehicle description on the vehicle's listing page.                                                               |
| engine_cylinders         | String       | The engine configuration, like I4, V6, etc.                                                                      |
| engine_displacement      | Float        | Engine displacement is the measure of the cylinder volume swept by all of the pistons of a piston engine, excluding the combustion chambers. |
| engine_type              | String       | The engine configuration, like I4, V6, etc.                                                                      |
| exterior_color           | String       | Exterior color of the vehicle, usually a fancy one same as the brochure.                                         |
| fleet                    | Boolean      | Whether the vehicle was previously part of a fleet.                                                              |
| frame_damaged            | Boolean      | Whether the vehicle has a damaged frame.                                                                         |
| franchise_dealer         | Boolean      | Whether the dealer is a franchise dealer.                                                                        |
| franchise_make           | String       | The company that owns the franchise.                                                                             |
| front_legroom            | String       | The legroom in inches for the passenger seat.                                                                     |
| fuel_tank_volume         | String       | Fuel tank's filling capacity in gallons.                                                                         |
| fuel_type                | String       | Dominant type of fuel ingested by the vehicle.                                                                    |
| has_accidents            | Boolean      | Whether the VIN has any accidents registered.                                                                     |
| height                   | String       | Height of the vehicle in inches.                                                                                  |
| highway_fuel_economy     | Float        | Fuel economy in highway traffic in km per litre.                                                                  |
| horsepower               | Float        | Horsepower is the power produced by an engine.                                                                    |
| interior_color           | String       | Interior color of the vehicle, usually a fancy one same as the brochure.                                         |
| isCab                    | Boolean      | Whether the vehicle was previously a taxi/cab.                                                                   |
| is_certified             | Boolean      | Whether the vehicle is certified. Certified cars are covered through the warranty period.                        |
| is_cpo                   | Boolean      | Type Boolean. Pre-owned cars certified by the dealer. Certified vehicles come with a manufacturer warranty for free repairs for a certain time period. Read more at https://www.cartrade.com/blog/2015/auto-guides/pros-and-cons-of-buying-a-certified-pre-owned-car-1235.html |
| is_new                    | Boolean       | If True, means the vehicle was launched less than 2 years ago.                                          |
| is_oemcpo                 | Boolean       | Pre-owned cars certified by the manufacturer.                                                            |
| latitude                  | Float         | Latitude from the geolocation of the dealership.                                                         |
| length                    | String        | Length of the vehicle in inches.                                                                         |
| listed_date               | String        | The date the vehicle was listed on the website. Does not make days_on_market obsolete.                    |
| listing_color             | String        | Dominant color group from the exterior color.                                                            |
| listing_id                | Integer       | Listing id from the website.                                                                             |
| longitude                 | Float         | Longitude from the geolocation of the dealership.                                                        |
| main_picture_url          | String        | The URL of the main image of the vehicle.                                                                 |
| major_options             | String | A list of major options for the vehicle, such as transmission, fuel type, entertainment system, etc.     |
| make_name                 | String        | The make of the vehicle.                                                                                 |
| maximum_seating           | Integer       | The maximum seating capacity of the vehicle.                                                              |
| mileage                   | Integer       | The total distance traveled by the vehicle in miles.                                                      |
| model_name                | String        | The name or model of the vehicle.                                                                        |
| owner_count               | Integer       | The number of previous owners of the vehicle.                                                             |
| power                     | Integer       | The power of the engine in horsepower (HP).                                                               |
| price                     | Float         | The price of the vehicle in USD.                                                                         |
| salvage                   | Boolean       | Indicates whether the vehicle has a salvage title.                                                        |
| savings_amount            | Float         | The savings amount in USD compared to the market price.                                                   |
| seller_rating             | Float         | The rating of the seller on a scale of 0 to 5.                                                            |
| sp_id                     | Integer       | The ID of the salesperson.                                                                               |
| sp_name                   | String        | The name of the salesperson.                                                                             |
| theft_title               | Boolean       | Indicates whether the vehicle has a theft title.                                                          |
| torque                    | Integer       | The torque of the engine in pound-feet (lb-ft).                                                           |
| transmission              | String        | The type of transmission (automatic or manual).                                                           |
| transmission_display      | String        | The type of transmission in a human-readable format.                                                      |
| trimId                    | Integer       | The ID of the trim level of the vehicle.                                                                  |
| trim_name                 | String        | The name of the trim level of the vehicle.                                                                |
| vehicle_damage_category   | String        | Indicates the damage category of the vehicle.                                                             |
| wheel_system              | String        | The type of wheel system (2-wheel drive or 4-wheel drive).                                                |
| wheel_system_display      | String        | The type of wheel system in a human-readable format.                                                      |
| wheelbase                 | String        | The distance between the centers of the front and rear wheels of the vehicle.                            |
| width                     | String        | The width of the vehicle in inches.                                                                       |
| year                      | Integer       | The year the vehicle was manufactured.                                                                   |


### Grain
Each row of the dataset represents a used car.

### Acknowledgements
This data is for academic, research and individual experimentation only and is not intended for commercial purposes.

Link: https://www.kaggle.com/datasets/ananaymital/us-used-cars-dataset

## Schema definition

The schema definition can be found in CarSchema.scala. Below is the code fragment with the schema definition:

```scala
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
    StructField("combine_fuel_economy", FloatType),
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
```
## How the Dataset is processed

There is 3 Main files on the Project inside the myDataset Package.

### CarApplication.scala

In this file you will be able to find the code needed to calculate the percentage of cars by brand registered between 2015 and 2021 (percentage of market dominance of brands by year).

### Top3MarketBrandRankings2015to2021.Scala

In this file you will be able to find the code needed to see the evolution of the three brands registering the most cars between 2015 and 2021.

### EvolutionOfTheThreeBrandsThatDominateTheMarketIn2021.scala

In this file you will be able to find the code needed to see the evolution of the three brands that dominate the market in 2021.

## Questions to answer with the dataset

### How to work with the dataset?
The first thing that was done was a study of all the information that made up the dataset. It was decided to discard the rows that did not have a year, and to keep only the rows that were between 2015 and 2021.
As outliers in the year field we can identify several values to review.
To begin with, the years to identify should be from 1886 (year of creation of the first car in the world) to 2021 (last year of the dataset sampling). But when analyzing the dataset, we see that there is a small percentage that does not meet the expected value.

-Percentage of values that are above the year 2021: 0.23% (7042 rows).

-Percentage of values below the year 1886: 0.12% (317 rows).

The code for the percentage calculation can be found in the OutliersAnalysis.scala file. The resulting dataframe according to the dataset we work with is the one we see below:

![](./src/main/resources/images/datasetAnalysis.png)

On the other hand, the number of vehicles per year between 1900 and 2014, is always less than 4%, so it was considered that in order to have a more accurate analysis of the current market, all useful data from 2015 to 2021 (which this range covers 49.28% of the total dataset) should be taken into account.
For the analysis in question, it was decided to consider all the years with at least 4% of cars in the useful data records.

### General summary of dataset information

Full Dataset: It is the complete dataset.

Usefull: It is the information leaked between 2015 and 2021.

N/A: It is the information that contains nulls.

Outlier: It is the information that is part of the outliers.

![](./src/main/resources/images/graldataset.png)

The code of this summary can be seen in the file DatasetAnalysis.scala

### Statistics from useful data.

![](./src/main/resources/images/percentageperyear.png)

The code of this summary can be seen in the file CarAnalysisByYear.scala


### What is the ranking by year of car brands?

In order to be able to answer and give visibility of this question, what was done was to put together a query that has the total cars by year (regardless of brand), and then calculate the total cars by brand. Having these two pieces of information, we can now get the percentage of dominance of a brand in the market according to the model.
The query can be seen in the CarApplication.scala file.
Below are graphs based on the information obtained from the query:

### 2021
In the year 2021, we see that the three leading brands in the market were Hyunday, Kia y Toyota.

![](./src/main/resources/images/data2021.png)

![](./src/main/resources/images/1-2021.png)

### 2020
In the year 2020, we see that the three leading brands in the market were Ford, Honda and Chevrolet.

![](./src/main/resources/images/data2020.png)

![](./src/main/resources/images/1-2020.png)

### 2019
In the year 2019, we see that the three leading brands in the market were Ford, Nissan and Toyota.

![](./src/main/resources/images/data2019.png)

![](./src/main/resources/images/1-2019.png)

### 2018
In the year 2018, we see that the three leading brands in the market were Ford, Toyota and Chevrolet.

![](./src/main/resources/images/data2018.png)

![](./src/main/resources/images/1-2018.png)

### 2017
In the year 2017, we see that the three leading brands in the market were Ford, Nissan and Toyota.

![](./src/main/resources/images/data2017.png)

![](./src/main/resources/images/1-2017.png)

### 2016
In the year 2016, we see that the three leading brands in the market were Ford, Chevrolet and Toyota.

![](./src/main/resources/images/data2016.png)

![](./src/main/resources/images/1-2016.png)

### 2015
In the year 2015, we see that the three leading brands in the market were Ford, Chevrolet and Toyota:

![](./src/main/resources/images/data2015.png)

![](./src/main/resources/images/1-2015.png)

### Conclusions

Number of times in the top three ranking between 2015 and 2021:

![](./src/main/resources/images/timesinranking.png)

The code of this ranking can be seen in the file NumberTimesInRanking.scala

Toyota and Ford are the car brands that have been part of the ranking the most times.

Hyundai and Kia, despite only being part of the ranking once between 2015 and 2021, in 2021 are the leaders in number of cars registered.

### How is the evolution of the three brands with the most cars registered between 2015 and 2021?
The three brands that led the car market between 2015 and 2021 were Ford, Chevrolet and Toyota. The query can be seen in the Top3MarketBrandRankings2015to2021.scala file. The evolution graph can be seen below:

![](./src/main/resources/images/evolution1.png)

![](./src/main/resources/images/2-evolution.png)

### Conclusions

Of the three brands that dominated the market between 2015 and 2021, Ford, Chevrolet and Toyota, only the latter appears in the 2021 ranking, and it appears in third place. In the 2021 ranking, Chevrolet appears in rank 4, and Ford drops to rank 15.

### How is the evolution of the three brands that dominate the market in 2021?
The three brands that dominated the car market in 2021 were Hyundai, Kia and Toyota. The query can be seen in the EvolutionOfTheThreeBrandsThatDominateTheMarketIn2021.scala file. The evolution graph (between 2015 and 2021) of the three brands that dominated the market in 2021 can be viewed below:

![](./src/main/resources/images/evolution2.png)

![](./src/main/resources/images/2-evolution2.png)

### Conclusions

Of the three brands dominating the 2021 market, only Toyota appeared in the previous rankings (with the exception of 2020 which appeared in rank 4, then appeared in the top three rankings 2015, 2016, 2017, 2018, 2019 and 2021).
Hyundai appearing first in the 2021 ranking, in the 2020 ranking it was ranked 6th.
Kia which appears second in the 2021 ranking, in the 2020 ranking it was ranked 8th.

### Final conclusion
The dominance in the car market has been varying year after year, but there are some brands that are highly valued by the general public. Among these we find Toyota, Ford and Chevrolet. We should mention that Toyota has been almost always fighting in the ranking between the years 2015 to 2021. It is also worth mentioning that in the last year of the study, 2021, there are brands that promise future growth, such as Hyundai and Kia. However, we will only be able to verify this promise with the passage of time and new market studies.