package agh.wggios.analizadanych.service

import org.apache.spark.sql.{DataFrame, SaveMode}
import agh.wggios.analizadanych.SparkSessionProvider

class DataService extends SparkSessionProvider {

  def read_csv(path: String): DataFrame = {
    logInfo(s"Wczytuję plik CSV z: $path")
    spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(path)
  }

  def transform_data(df: DataFrame): DataFrame = {
    logInfo("Transformuję dane: filtruję i dodaję kolumnę")
    import spark.implicits._

    df.filter($"money" > 100)
      .withColumn("high_value", $"money" > 500)
  }

  def save_parquet(df: DataFrame, outputPath: String): Unit = {
    logInfo(s"Zapisuję dane do: $outputPath jako Parquet")
    df.write
      .mode(SaveMode.Overwrite)
      .parquet(outputPath)
  }

}