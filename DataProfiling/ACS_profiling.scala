// spark-shell --packages com.databricks:spark-csv_2.10:1.5.0
// Above command used to open spark shell in dumbo with the csv package loaded

import org.apache.spark.sql.types.IntegerType

val df = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load("bdad/ACS_16_5YR_DP05.csv")
val df2 = df.withColumn("HC01_VC03_tmp", df("HC01_VC03").cast(IntegerType))
df2.groupBy().min("HC01_VC03_tmp").show() //  result is 0 - minimum zipcode population
df2.groupBy().max("HC01_VC03_tmp").show() // result is 112982 - max xipcode population
df2.groupBy().avg("HC01_VC03_tmp").show() // result is 10977.509 - average zipcode population

// only profiled the total population statistics column and not other columns(which are stringLike)
// as that is the only column we need in our dataset. No need to clean as the column is pre-cleaned.

val li = List(10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10043,10044,10045,10046,10047,10048,10055,10060,10069,10072,10079,10080,10081,10082,10087,10090,10094,10095,10096,10098,10099,10101,10102,10103,10104,10105,10106,10107,10108,10109,10110,10111,10112,10113,10114,10115,10116,10117,10118,10119,10120,10121,10122,10123,10124,10125,10126,10128,10129,10130,10131,10132,10133,10138,10149,10150,10151,10152,10153,10154,10155,10156,10157,10158,10159,10160,10161,10162,10163,10164,10165,10166,10167,10168,10169,10170,10171,10172,10173,10174,10175,10176,10177,10178,10179,10184,10185,10196,10197,10199,10203,10211,10212,10213,10242,10249,10256,10257,10258,10259,10260,10261,10265,10268,10269,10270,10271,10272,10273,10274,10275,10276,10277,10278,10279,10280,10281,10282,10285,10286,10292,10301,10302,10303,10304,10305,10306,10307,10308,10309,10310,10311,10312,10313,10314,10400,10451,10452,10453,10454,10455,10456,10457,10458,10459,10460,10461,10462,10463,10464,10465,10466,10467,10468,10469,10470,10471,10472,10473,10474,10475,10499,11001,11002,11003,11004,11005,11010,11020,11021,11022,11023,11024,11025,11026,11027,11030,11040,11041,11042,11043,11044,11050,11051,11052,11053,11054,11055,11096,11099,11201,11202,11203,11204,11205,11206,11207,11208,11209,11210,11211,11212,11213,11214,11215,11216,11217,11218,11219,11220,11221,11222,11223,11224,11225,11226,11228,11229,11230,11231,11232,11233,11234,11235,11236,11237,11238,11239,11240,11241,11242,11243,11244,11245,11247,11248,11249,11251,11252,11254,11255,11256)
val df3 = df2.withColumn("Zipcode", df2("`GEO.id2`").cast(IntegerType))
val df4 = df3.filter($"Zipcode".isin(li:_* ))
df4.describe().show