package com.nlp.code.java.entity;


import lombok.Getter;
import lombok.Setter;

/**
 * 리뷰 정보
 */
@Getter
@Setter
public class ReviewEntity {

    //리뷰 id
    private Integer reviewId;
    //상품 id
    private String asin;
    //평점
    private String overall;
    //총괄
    private String summary;
    //리뷰
    private String reviewText;
    //작성 일자
    private String reviewTime;
    //작성자 성명
    private String reviewerName;
}
