package com.nlp.code.java.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 월간 집계
 * @author user
 *
 */
@Getter
@Setter
public class MonthTotalEntity {

	private String month;
	private String asin;
	private String title;
	private String count;
	private String total;
	private String brand;
}
