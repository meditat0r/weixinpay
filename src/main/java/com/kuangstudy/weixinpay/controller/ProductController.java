package com.kuangstudy.weixinpay.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuangstudy.weixinpay.anno.IgnoreToken;
import com.kuangstudy.weixinpay.entity.Product;
import com.kuangstudy.weixinpay.service.ProductService;
import com.kuangstudy.weixinpay.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: xuke
 * @time: 2021/4/24 21:50
 */
@RestController
public class ProductController extends ApiBaseController {

    @Autowired
    private ProductService productService;


    /**
     * @return com.kuangstudy.weixinpay.vo.R
     * @Author xuke
     * @Description 查询产品信息
     * @Date 21:52 2021/4/24
     * @Param []
     **/
    @GetMapping("/product/list")
    public R findProducts() {
        //1 :设置查询条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        //2: 执行查询
        List<Product> list = productService.list(queryWrapper);
        //3: 返回
        return R.ok().data("productList", list);
    }



    /**
     * @return com.kuangstudy.weixinpay.vo.R
     * @Author xuke
     * @Description 查询产品信息
     * @Date 21:52 2021/4/24
     * @Param []
     **/
    @GetMapping("/product/{id}")
    public R getProduct(@PathVariable("id")Integer id) {
        //2: 执行查询
        Product product = productService.getById(id);
        //3: 返回
        return R.ok().data("product", product);
    }





}