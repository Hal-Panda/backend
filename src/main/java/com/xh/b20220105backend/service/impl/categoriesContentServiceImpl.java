//package com.xh.b20220105backend.service.impl;
//
//import com.xh.b20220105backend.service.categoriesService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class categoriesContentServiceImpl implements categoriesService {
//    @Autowired
//    private categoriesMapper categoriesMapper;
//
//    @Override
//    public List<categories> showAll() {
//        categoriesExample categoriesExample = new categoriesExample();
//        List<categories> categories = categoriesMapper.selectByExample(categoriesExample);
//        return categories;
//    }
//}
