package com.nlp.code.java.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlp.code.java.entity.ProductEntity;
import com.nlp.code.java.entity.ReviewEntity;
import com.nlp.code.java.service.ReviewService;
import com.nlp.code.spark.service.ProductListService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/review")
@Slf4j
public class MainController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductListService productListService;

    /**
     * 상품 리스트
     * @return
     */
    @RequestMapping(value = "/queryList")
    public String queryProductList(Model model){
        log.info("queryProductList start===");
        //List<ProductEntity> list = productListService.getProductList();
        List<ProductEntity> list = new ArrayList<>();
        ProductEntity entity = new ProductEntity();
        entity.setAsin("1");
        entity.setTitle("Book");
        entity.setPrice("99.8");
        list.add(entity);
        model.addAttribute("productList",list);
        log.info("queryProductList end===");
        return "index";
    }

    /**
     * 리뷰 저장
     * @param entity 리뷰 오브젝
     */
    @RequestMapping(value = "/insertReview")
    public String insertReview(@RequestBody ReviewEntity entity){
        log.info("insertReview start===",entity);
        reviewService.insertReview(entity);
        log.info("insertReview end===");
        return "index";
    }

    /**
     * 자동 리뷰 생성 버튼
     * @param entity
     * @return
     */
    @RequestMapping(value = "/a")
    @ResponseBody
    public ReviewEntity queryAutoReview(@RequestBody ReviewEntity entity){
        ReviewEntity result = new ReviewEntity();
        return result;
    }

}
