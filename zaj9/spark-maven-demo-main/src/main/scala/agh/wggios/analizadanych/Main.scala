package agh.wggios.analizadanych

import agh.wggios.analizadanych.datareader.DataReader

object Main extends SparkSessionProvider {
LoggingUtils.setupLogging()
  def main(args: Array[String]): Unit = {
    val inputPath = args(0)
    val outputPath = args(1)

    val service = new DataService()

    val rawDF = service.read_csv(inputPath)
    val transformedDF = service.transform_data(rawDF)
    service.save_parquet(transformedDF, outputPath)

    logInfo("Zakończono przetwarzanie.")
  }
}
