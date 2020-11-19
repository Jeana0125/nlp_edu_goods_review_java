package com.nlp.code.spark.conf;

import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HBaseConnectionBean {

	/**
	 * hbase 설정
	 * @return
	 */
	private static Configuration getConfiguration() {
		
		try {
			
			Properties properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
			String clientPort = properties.getProperty("hbase.zookeeper.property.clientPort");
		    String quorum = properties.getProperty("hbase.zookeeper.quorum");
		    
		    log.debug("connect to zookeeper {}:{}", quorum, clientPort);
		    
		    Configuration config = HBaseConfiguration.create();
	        config.set("hbase.zookeeper.property.clientPort", clientPort);
	        config.set("hbase.zookeeper.quorum", quorum);
	        
	        return config;
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * hbase 연결
	 * @return
	 */
	public static Connection getConnection() {
	    try {
	       
	        Configuration configuration = getConfiguration();      
	        HBaseAdmin.checkHBaseAvailable(configuration);
	        return ConnectionFactory.createConnection(configuration);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
