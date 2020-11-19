package com.nlp.code.spark.service;

import org.apache.spark.sql.SparkSession;

import com.nlp.code.spark.conf.SparkContextBean;

/**
 * 리뷰 내역 테이블
 * @author user
 *
 */
public class ReviewSatisfyService {
	
	public static void main(String[] args) {
		
		SparkSession spark = SparkSession.builder().config(SparkContextBean.sparkConf()).enableHiveSupport().getOrCreate();
		
	}

}
