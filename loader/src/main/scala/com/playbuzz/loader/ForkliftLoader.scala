package com.playbuzz.loader

import com.playbuzz.common.entities.RawEvent
import com.playbuzz.enricher.ScroogeMcduckEnrich
import org.apache.spark.sql.{Dataset, SparkSession}

object ForkliftLoader {
  def execute(spark: SparkSession): Unit = {
    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

    val rawDataset = Seq(
      RawEvent("b1b2b3", 14054552, "my foobar skills are unbelivable!")).toDS()
    // you should probably load from a poor=mans-kafka (kinesis)

    // save to cache
    rawDataset.persist()

    // save
    rawDataset.write.parquet("/raw-somewhere")

    // continue to next step
    ScroogeMcduckEnrich.execute(spark, rawDataset)
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("The Loader")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    this.execute(spark)
  }
}
