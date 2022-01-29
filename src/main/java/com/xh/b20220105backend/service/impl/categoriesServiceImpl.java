//package com.xh.b20220105backend.service.impl;
//
//import com.xh.b20220105backend.service.categoriesContentService;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@Service
//public class categoriesServiceImpl implements categoriesContentService {
//    @Resource
//    private categories_contentMapper categories_contentMapper;
//    @Override
//    public List<categories_content> showPart(Integer id) {
//        categories_contentExample categories_contentExample = new categories_contentExample();
//        categories_contentExample.createCriteria().andCategoriesidEqualTo(id);
//        List<categories_content> categories_contents = categories_contentMapper.selectByExample(categories_contentExample);
//        return categories_contents;
//    }
//}
