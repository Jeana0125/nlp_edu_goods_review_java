package com.nlp.code.spark.conf;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spark")
@Slf4j
@Getter
@Setter
public class SparkContextBean {

    private static String appName = "";
    private static String master = "local";

    @Bean
    //@ConditionalOnMissingBean: it will ensure that bean has only one
    @ConditionalOnMissingBean(SparkConf.class)
    public static SparkConf sparkConf() {
        /**
         * spark object create, set the configuration
         * setAppName : set the program name
         * setMaster : set the url that the program to be linked by spark cluster
         */
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        return conf;
    }

    @Bean
    @ConditionalOnMissingBean(JavaSparkContext.class)
    public static JavaSparkContext javaSparkContext() {
        try {
            /**
             * SparkContext is the only entry point for all functions of the spark program
             */
            return new JavaSparkContext(sparkConf());
        }catch (Exception e) {
            log.error("javaSparkContext====="+e);

        }

        return null;
    }
}
