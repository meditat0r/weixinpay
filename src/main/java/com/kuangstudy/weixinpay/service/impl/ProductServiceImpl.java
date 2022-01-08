package com.kuangstudy.weixinpay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuangstudy.weixinpay.entity.Product;
import com.kuangstudy.weixinpay.mapper.ProductMapper;
import com.kuangstudy.weixinpay.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 遇见狂神说
 * @since 2021-04-15
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
