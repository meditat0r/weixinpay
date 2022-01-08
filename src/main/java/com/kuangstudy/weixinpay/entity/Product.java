package com.kuangstudy.weixinpay.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 遇见狂神说
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
//@TableName("ksd-product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 酒的名称
     */
    private String title;

    /**
     * 产品展示图
     */
    private String cover;

    /**
     * 产品详情头图
     */
    private String headimg;

    /**
     * 香型
     */
    private String xiangxin;

    /**
     * 原料
     */
    private String yuanliao;

    /**
     * 规格
     */
    private String guige;

    /**
     * 执行标准
     */
    private String biaozhun;

    /**
     * 产品许可证号
     */
    private String xukehao;

    /**
     * 酒精度
     */
    private String jiujingdu;

    /**
     * 零售价
     */
    private String price;

    /**
     * 生产企业
     */
    private String company;

    /**
     * 厂家地址
     */
    private String address;

    /**
     * 邮编
     */
    private String youbian;

    /**
     * 联系电话
     */
    private String phone;


    /**
     * 贮存条件
     */
    private String zhucuntiaojian;

    /**
     * 质检图片1
     */
    private String zhijian1;

    /**
     * 质检图片2
     */
    private String zhijian2;

    /**
     * 质检图片3
     */
    private String zhijian3;

    /**
     * 产品详情
     */
    private String detail;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否发布 0未发布 1发布
     */
    private Integer status;

    /**
     * 分类id
     */
    private Integer categoryid;

    /**
     * 分类name
     */
    private String categoryname;
}
