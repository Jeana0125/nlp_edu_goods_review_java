package com.nlp.code.test;

import java.util.Arrays;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import com.nlp.code.spark.conf.SparkContextBean;

import scala.Tuple2;
/**
 * word count
 * @author Administrator
 *
 */
public class WordCountTest {

	public static void main(String[] args) {
		
        /*
         * hello java
         * hello scala
         * hello ptyhon
         */
        JavaRDD<String> lines = SparkContextBean.javaSparkContext().textFile("./data/words");
        /*
         * hello
         * java
         * hello
         * scala
         * hello
         * ptyhon
         */
        JavaRDD<String> words = lines.flatMap( line -> Arrays.asList(line.split(" ")).iterator());
        /*
         * <hello,1>
         * <hello,1>
         * <hello,1>
         * <scala,1>
         * <java,1>
         * <python,1>
         */
        JavaPairRDD<String, Integer> pairRDD = words.mapToPair(word -> new Tuple2<>(word, 1));
        /*
         * <hello,3>
         * <scala,1>
         * <java,1>
         * <python,1>
         */
        JavaPairRDD<String, Integer> result = pairRDD.reduceByKey((v1,v2) -> v1+v2);

        result.foreach(tuple -> System.out.println(tuple));

	}
}
