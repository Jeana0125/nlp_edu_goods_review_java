package com.nlp.code.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nlp.code.java.common.DateUtils;
import com.nlp.code.java.entity.ReviewEntity;
import com.nlp.code.java.mapper.ReviewMapper;
import com.nlp.code.java.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

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
