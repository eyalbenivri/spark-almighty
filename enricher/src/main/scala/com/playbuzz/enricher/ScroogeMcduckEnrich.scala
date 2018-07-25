package com.playbuzz.enricher

import com.playbuzz.common.entities.{EnrichedEvent, RawEvent}
import com.playbuzz.transformer.OptimusPrimeTransformer
import org.apache.spark.sql.{Dataset, SparkSession}

object ScroogeMcduckEnrich {
  def execute(spark: SparkSession,
              existingRawDataset: Dataset[RawEvent]): Unit = {
    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

    val rawDataset = if (existingRawDataset != null) {
      existingRawDataset
    } else {
      Seq(RawEvent("asdf", 1234, "my foobar skills are amazing!")).toDS()
      // you should probably load from S3 using a param
    }

    // do some enriching with rawDataset, ending up with new dataset of EnrichedEvent

    val enrichedDataset: Dataset[EnrichedEvent] = rawDataset.map(
      re =>
        EnrichedEvent(re.id,
                      re.ts,
                      new java.util.Date(2018, 7, 25),
                      s"---> ${re.foobar} <---"))

    // save to cache
    enrichedDataset.persist()

    // save
    enrichedDataset.write.parquet("/somewhere-else")

    // continue to next step
    OptimusPrimeTransformer.execute(spark, enrichedDataset)
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("The Enricher")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    this.execute(spark, null)
  }
}
