//package com.xh.b20220105backend.controller;
//
//import com.xh.b20220105backend.entity.categories;
//import com.xh.b20220105backend.entity.categories_content;
//import com.xh.b20220105backend.entity.response.resultMap;
//import com.xh.b20220105backend.service.categoriesContentService;
//import com.xh.b20220105backend.service.categoriesService;
//import com.xh.b20220105backend.util.resultMapUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("/categories")
//public class CateegoriesController {
//    @Autowired
//    private categoriesService categoriesService;
//    @Autowired
//    private categoriesContentService categoriesContentService;
//
//    @RequestMapping("/showAll")
//    public resultMap showAll(){
//        List<categories> categories = categoriesService.showAll();
//        resultMap data = resultMapUtil.backResultMap("ok", categories, 100);
//        return data;
//    }
//
//    @RequestMapping("/showPart/{id}")
//    public resultMap showPart(@PathVariable Integer id){
//        List<categories_content> categories_contents = categoriesContentService.showPart(id);
//        resultMap data = resultMapUtil.backResultMap("ok", categories_contents, 100);
//        return data;
//    }
//}
