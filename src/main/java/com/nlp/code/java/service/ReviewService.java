package com.nlp.code.java.service;

import com.nlp.code.java.entity.ProductEntity;
import com.nlp.code.java.entity.ReviewEntity;

import java.util.List;

public interface ReviewService {

    public List<ProductEntity> queryProductList();
    
    public void insertReview(ReviewEntity entity);
}
