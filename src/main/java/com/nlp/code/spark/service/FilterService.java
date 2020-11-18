package com.nlp.code.spark.service;

import com.nlp.code.spark.entity.SparkContextBean;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class FilterService {

    SparkContextBean conf = new SparkContextBean();
    //SparkContext object
    JavaSparkContext sc = conf.javaSparkContext();

    //According to the data source(HDFS,HBase,Hive,Local...), JavaRDD is created by JavaSparkContext
    JavaRDD<String> lines = sc.textFile("C://users/user/desktop/edu/train_sub.csv");

    public void getTest() throws Exception{

        SparkSession spark = SparkSession.builder()
                .appName("hive test")
                .master("local")
                .config("hadoop.home.dir","/usr/hive/warehouse")
                .enableHiveSupport()
                .getOrCreate();

        spark.sql("SELECT * FROM review_json_src LIMIT 20").show();
    }


}
