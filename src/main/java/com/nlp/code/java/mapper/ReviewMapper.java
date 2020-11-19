package com.nlp.code.java.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nlp.code.java.entity.ReviewEntity;

@Mapper
public interface ReviewMapper {

    /**
     * 리뷰 추가
     * @param entity
     * @return
     */
    int insertReview(ReviewEntity entity);
}
