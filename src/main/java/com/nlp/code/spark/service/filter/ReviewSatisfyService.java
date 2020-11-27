package com.nlp.code.spark.service.filter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.nlp.code.java.common.DateUtils;
import com.nlp.code.spark.conf.SparkContextBean;

/**
 * 리뷰 내역 테이블
 * -- 매일 그 날의 리뷰를 테이블에 추가
 * -- 매일
 * @author user
 *
 */
public class ReviewSatisfyService {
	
	public static void main(String[] args) {
		
		/**
		 * spark와 hive 연결
		 */
		SparkSession spark = SparkSession.builder().config(SparkContextBean.sparkConf()).enableHiveSupport().getOrCreate();
		
		Dataset<Row> goodDataset = spark.table("review.review_satisfy_good_inc");
		Dataset<Row> badDataset = spark.table("review.review_satisfy_bad_inc");
		Dataset<Row> normalDataset = spark.table("review.review_satisfy_normal_inc");
		
		Dataset<Row> goodDs = goodDataset.select("asin","title","review_text","summary")
				                         .where("cast(substr(review_time,1,8) as big int) = cast( "+DateUtils.getDate()+" as bigint)");
		Dataset<Row> badDs = badDataset.select("asin","title","review_text","summary")
                					   .where("cast(substr(review_time,1,8) as big int) = cast( "+DateUtils.getDate()+" as bigint)");
		Dataset<Row> normalDs = normalDataset.select("asin","title","review_text","summary")
                						     .where("cast(substr(review_time,1,8) as big int) = cast( "+DateUtils.getDate()+" as bigint)");
		//보여주기
		goodDs.show();
		badDs.show();
		normalDs.show();
		
		spark.sql("use review");
		//temporary table 생성(session이 종료되면 삭제됨)
		goodDs.createOrReplaceTempView("review_satisfy_good_rpt_tmp");
		badDs.createOrReplaceTempView("review_satisfy_bad_rpt_tmp");
		normalDs.createOrReplaceTempView("review_satisfy_normal_rpt_tmp");
		
		//temporary table에 데이터 삽입
		//goodDs.write().saveAsTable("review_satisfy_good_rpt_tmp");
		//badDs.write().saveAsTable("review_satisfy_bad_rpt_tmp");
		//normalDs.write().saveAsTable("review_satisfy_normal_rpt_tmp");
		
		//테이블에 넣기
		spark.sql("INSERT INTO TABLE review.review_satisfy_rpt_hbase select title,review_text,summary,'3' as satisfy,asin FROM review_satisfy_good_rpt_tmp");
		spark.sql("INSERT INTO TABLE review.review_satisfy_rpt_hbase select title,review_text,summary,'1' as satisfy,asin FROM review_satisfy_bad_rpt_tmp");
		spark.sql("INSERT INTO TABLE review.review_satisfy_rpt_hbase select title,review_text,summary,'2' as satisfy,asin FROM review_satisfy_normal_rpt_tmp");
		
		spark.stop();
		
	}

}
