package com.nlp.code.java.mapper;

import com.nlp.code.java.entity.ProductEntity;
import com.nlp.code.java.entity.ReviewEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    /**
     * 상품 리스트
     * @return
     */
    public List<ProductEntity> queryProductList();

    /**
     * 리뷰 추가
     * @param entity
     * @return
     */
    int insertReview(ReviewEntity entity);
}
