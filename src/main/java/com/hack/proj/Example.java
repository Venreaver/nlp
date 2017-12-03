package com.hack.proj;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Example {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("App").master("local[*]").getOrCreate();
        Dataset<Row> dataset = sparkSession.read().option("header", true)
                .option("inferSchema", true).csv("src/main/resources");
        dataset.show(5);
    }
}
