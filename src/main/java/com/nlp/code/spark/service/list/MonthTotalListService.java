package com.nlp.code.spark.service.list;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

import com.nlp.code.java.entity.MonthTotalEntity;
import com.nlp.code.spark.conf.HBaseConnectionBean;

/**
 * hbase에서 월간 집계 정보 가져오기
 * @author user
 *
 */
@Service
public class MonthTotalListService {

	/**
	 * 월간 집계 데이터 가져오기
	 * @param entity
	 * @return
	 * @throws IOException
	 */
	public List<MonthTotalEntity> getMonthTotalList(MonthTotalEntity entity) throws IOException{
		
		List<MonthTotalEntity> list = new ArrayList<MonthTotalEntity>();
		
		//hbase연결
		Connection connection =  HBaseConnectionBean.getConnection();
		//테이블 설정
		Table table =connection.getTable(TableName.valueOf("product_count_month_rpt"));
		//검색조건 추가
		Scan scan = new Scan();
		String startRow = entity.getMonth()+entity.getAsin()+"00001";
		String stopRow = entity.getMonth()+entity.getAsin()+"99999";
		scan.setStartRow(Bytes.toBytes(startRow));
		scan.setStopRow(Bytes.toBytes(stopRow));
		//테이터 가져오기
		ResultScanner scanResult = table.getScanner(scan);   
		//결과를 MonthTotalEntity 오브젝에 넣기
		for (Result result : scanResult) {
			MonthTotalEntity en = new MonthTotalEntity();
      	  	en.setAsin(Bytes.toString(CellUtil.cloneValue(
      	  			result.getColumnLatestCell("info".getBytes(),"asin".getBytes()))));
      	  	en.setTitle(Bytes.toString(CellUtil.cloneValue(
      	  			result.getColumnLatestCell("info".getBytes(),"title".getBytes()))));
      	  	en.setBrand((Bytes.toString(CellUtil.cloneValue(
      	  			result.getColumnLatestCell("info".getBytes(),"brand".getBytes())))));
      	  	en.setCount(Bytes.toString(CellUtil.cloneValue(
      	  			result.getColumnLatestCell("statistics".getBytes(),"count".getBytes()))));
      	  	en.setTotal(Bytes.toString(CellUtil.cloneValue(
      	  			result.getColumnLatestCell("statistics".getBytes(),"total".getBytes()))));
      	  	en.setMonth(Bytes.toString(CellUtil.cloneValue(
      	  			result.getColumnLatestCell("statistics".getBytes(),"month".getBytes()))));
      	  	list.add(en);
		}
		return list;
	}
}
