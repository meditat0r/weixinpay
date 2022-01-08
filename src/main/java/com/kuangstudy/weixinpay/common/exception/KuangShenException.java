package com.kuangstudy.weixinpay.common.exception;

import com.kuangstudy.weixinpay.vo.ResultCodeEnum;
import lombok.Data;

@Data
public class KuangShenException extends RuntimeException {
    private Integer code;

    /**
     * 接受状态码和消息
     * @param code
     * @param message
     */
    public KuangShenException(Integer code, String message) {
        super(message);
        this.code=code;
    }

    /**
     * 接收枚举类型
     * @param resultCodeEnum
     */
    public KuangShenException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}