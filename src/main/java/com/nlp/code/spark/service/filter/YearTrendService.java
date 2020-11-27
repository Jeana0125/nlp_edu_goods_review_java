package com.nlp.code.spark.service.filter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.nlp.code.java.common.DateUtils;
import com.nlp.code.spark.conf.SparkContextBean;

/**
 * 브랜드 별 연간 트렌드 상품 추출
 * -- 연간 브랜드 별 판매수가 가장 많은 top3 상품을 추출하여 테이블에 추가
 * -- 매년
 * @author user
 *
 */
public class YearTrendService {

	public static void main(String[] args) {
		
		String nowYear = "";
		SparkSession spark = SparkSession.builder().config(SparkContextBean.sparkConf()).enableHiveSupport().getOrCreate();
		
		Dataset<Row> preDataset = spark.table("review.year_trend_rpt");
		Dataset<Row> yearDataset = preDataset.select("year").sort(preDataset.col("year").desc()).limit(1);
		if (yearDataset!=null) {
			String lastYear = preDataset.first().getString(0);
			nowYear = DateUtils.getNextMonth(lastYear);
		}else {
			nowYear = "1999";
		}
		
		Dataset<Row> dataset = spark.sql("SELECT t.year,t.brand,t.title,t.asin,f.im_url"
				                       + "FROM ( "
				                       + "	SELECT t.brand,t.asin,t.title,substr(t.review_time,1,4) year,count() as count "
				                       + "	FROM review.product_sale_inc t "
				                       + "	WHERE cast(substr(t.review_time) as bigint) >= cast (" + nowYear +" as bigint) "
				                       + "	GROUP BY substr(t.review_time,1,4),t.brand ) t "
				                       + "INNER JOIN review.product_json_fact f on t.asin = f.asin"
				                       + "ORDER BY t.brand,t.year,t.count desc");
		//dataset.show();
		
		spark.sql("use review");
		
		//위의 sql 문구로 찾은 결과를 temporary view에 삽입
		dataset.createOrReplaceTempView("year_trend_rpt_tmp");
		
		//매개 브랜드 중의 판매량이 top3인 데이터 가져오기
		Dataset<Row> resultDataset = spark.sql("SELECT t.brand,t.asin,t.title,t.year,t.im_url "
											+ "FROM ( "
											+ "  SELECT brand,asin,title,year,count,im_url,row_number() over(partition by brand order by count desc) as rn"
											+ "  FROM year_trend_rpt_tmp"
											+ ") t "
											+ "WHERE t.rn <= 3 ");
		
		resultDataset.show();
		
		//결과를 temporary view에 넣기
		resultDataset.createOrReplaceTempView("year_trend_rpt_temp");
		//테이블에 넣기
		spark.sql("INSERT INTO TABLE review.year_trend_rpt select year,brand,title,asin,im_url FROM year_trend_rpt_temp");
		
		spark.stop();
	}
}
