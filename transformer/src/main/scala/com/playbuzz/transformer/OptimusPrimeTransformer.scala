package com.playbuzz.transformer

import com.playbuzz.common.entities.EnrichedEvent
import org.apache.spark.sql.{Dataset, SparkSession}

object OptimusPrimeTransformer {
  def execute(spark: SparkSession,
              existingEnrichedDataset: Dataset[EnrichedEvent]): Unit = {
    val enrichedDataset = if (existingEnrichedDataset != null) {
      existingEnrichedDataset
    } else {
      // load from somewhere
    }

    // do aggregations on enrichedDataset

    // save
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("The Transformer")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    this.execute(spark, null)
  }
}
