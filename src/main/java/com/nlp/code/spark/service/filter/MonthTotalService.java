package com.nlp.code.spark.service.filter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.nlp.code.java.common.DateUtils;
import com.nlp.code.spark.conf.SparkContextBean;
/**
 * 월간 상품 판매 액&판매 수량 테이블
 * -- 전 집계 월을 기준으로 다음 달부터 집계하여 테이블에 추가
 * -- 매달
 * @author user
 *
 */
public class MonthTotalService {

	public static void main(String[] args) {
		
		String nowMonth = "";
		SparkSession spark = SparkSession.builder().config(SparkContextBean.sparkConf()).enableHiveSupport().getOrCreate();
		//집계 테이블에서 마지막 집계 월 가져오기
		Dataset<Row> preDataset = spark.table("review.product_count_month_rpt");
		Dataset<Row> monthDataset = preDataset.select("month").sort(preDataset.col("month").desc()).limit(1);
		if (monthDataset != null) {
			String lastMonth = preDataset.first().getString(0);
			nowMonth = DateUtils.getNextMonth(lastMonth);
		}else {
			nowMonth = "199901";
		}
		
		String whereString = "WHERE cast(substr(review_time,1,6) as bigint) >= cast(if("+nowMonth+" is '','0',"+nowMonth+") as bigint)";
		
		//집계 테이블 마지막 집계월 다음 달 부터 집계 시작
		Dataset<Row> df = spark.table("product_sale_inc").where(whereString);
		df.checkpoint();
		
		//Dataset<Row> countDataset = df.select("asin", "title","substr(review_time,1,6) as month").groupBy("asin", "substr(review_time,1,6)").count();
		//Dataset<Row> sumDataset = df.select("asin", "title","substr(review_time,1,6) as month").groupBy("asin", "substr(review_time,1,6)").sum("price");
		
		Dataset<Row> resultDataset = spark.sql("SELECT t.asin,t.title,t.month,t.count,t.total,t.brand"
										+ " FROM ("
										+ " 	SELECT asin,title,brand,substr(review_time,1,6) as month,count() as count,sum(price) as total"
										+ " 	FROM reveiw.product_sale_inc"
										+ 		whereString
										+ " 	GROUP BY asin,substr(review_time,1,6)"
										+ " ) t");	
		//보여주기
		//countDataset.show();
		//sumDataset.show();
		resultDataset.show();
		spark.sql("use review");
		//temporary view 생성(session이 종료되면 삭제됨)
		resultDataset.createOrReplaceTempView("product_count_month_rpt_tmp");
		//temporary table에 데이터 삽입
		//resultDataset.write().saveAsTable("product_count_month_rpt_tmp");
		//집계 정보를 product_count_month_rpt에 넣어주기
		spark.sql("INSERT INTO TABLE review.product_count_month_rpt_hbase "
				+ "select concat_ws(month,asin,row_number() over (order by t1.id) + t2.id_max),"
				+ "asin,title,total,brand,count,month "
				+ "FROM product_count_month_rpt_tmp t1 "
				+ "CROSS JOIN (SELECT coalesce(max(id),0) id_max "
				+ "            FROM review.product_count_month_rpt_hbase) "
				+ " t2");
		
		spark.stop();
		
	}
	
	/**
	 * hbase에 월별 상품 판매 집계 데이터 넣기
	 */
	public void AddHBaseTable() {
		
	}
}
