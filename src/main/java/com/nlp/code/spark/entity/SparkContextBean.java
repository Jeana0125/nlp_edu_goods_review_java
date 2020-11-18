package com.nlp.code.spark.entity;

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
public class SparkContextBean {

    private String sparkHome = ".";
    private String appName = "";
    private String master = "local";

    @Bean
    //@ConditionalOnMissingBean: it will ensure that bean has only one
    @ConditionalOnMissingBean(SparkConf.class)
    public SparkConf sparkConf() throws Exception{
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
    public JavaSparkContext javaSparkContext() {
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


    public String getSparkHome() {
        return sparkHome;
    }

    public void setSparkHome(String sparkHome) {
        this.sparkHome = sparkHome;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
