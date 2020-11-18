package com.nlp.code.java.service.impl;

import com.nlp.code.java.common.DateUtils;
import com.nlp.code.java.entity.ProductEntity;
import com.nlp.code.java.entity.ReviewEntity;
import com.nlp.code.java.mapper.ReviewMapper;
import com.nlp.code.java.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    /**
     * 상품 리스트
     * @return
     */
    @Override
    public List<ProductEntity> queryProductList() {
        return reviewMapper.queryProductList();
    }

    /**
     * 리뷰 저장
     * @param entity
     */
    @Override
    public void insertReview(ReviewEntity entity) {

        entity.setReviewTime(DateUtils.getDate());
        reviewMapper.insertReview(entity);
    }
}
