package com.kuangstudy.weixinpay.common.pay.weixin.enumtype;

/**
 * 业务类型
 * @author xiaojiewen
 */

public enum BusinessType {

    CLIENT("客户端",6L),DISTRIBUTOR("分销商",2L),MERCHANT("服务商",3L),OTHER("其他",0L);

    private String type;

    private Long enumType;

    BusinessType(String type, Long enumType) {
        this.type = type;
        this.enumType = enumType;
    }

    public Long getEnumType() {
        return enumType;
    }

    public String getType() {
        return type;
    }
}
