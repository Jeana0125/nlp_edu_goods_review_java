package com.nlp.code.spark.service.list;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import com.nlp.code.java.entity.ProductEntity;
import com.nlp.code.spark.conf.SparkContextBean;

/**
 * hive에서 상품리스트 가져오기
 * @author user
 *
 */
@Service
public class ProductListService {

	/**
	 * 리뷰 작성시 상품 선택 리스트
	 * @return List<ProductEntity>
	 */
	public List<ProductEntity> getProductList(){
		
		List<ProductEntity> list = new ArrayList<ProductEntity>();
		/*
		 * sparkConf을 이용하여 spark sql를 사용할수 있는 sparkSession 생성
		 */
		SparkSession sparkSession = SparkSession.builder().appName("product list").config(SparkContextBean.sparkConf()).enableHiveSupport().getOrCreate();
		/*
		 * 데이터를 읽어오기
		 */
		Dataset<Row> ds = sparkSession.sql("select asin,title,price from review.product_json_fact");
		/*
		 * 
		 */
		//ds.checkpoint();
		/*
		 *루프 돌려  ProductEntity 리스트에 값을 넣기
		 */
		ds.foreach(row->{
			ProductEntity entity = new ProductEntity();
			entity.setAsin(row.getString(0));
			entity.setTitle(row.getString(1));
			entity.setPrice(row.getString(2));
			list.add(entity);
         });
		
		//sparksession 종료
		sparkSession.stop();
		
		return list;
		
	}
}
