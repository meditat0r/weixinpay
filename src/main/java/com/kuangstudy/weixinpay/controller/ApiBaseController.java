package com.kuangstudy.weixinpay.controller;

import com.kuangstudy.weixinpay.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName BaseController
 * @Author ：xuke
 * @Date ：2021/1/17 13:01
 * @Description：
 * @Version: 1.0
 */
@RequestMapping("/api")
public class ApiBaseController {

    @Autowired(required = false)
    public HttpServletRequest request;


    @Autowired(required = false)
    public HttpServletResponse response;

    /**
     * 处理上传异常
     * @param t
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    public R handleAll(Throwable t) throws Exception {
        // TODO do Throwable t
        t.printStackTrace();
        return R.error().code(408).message("文件上传出现异常");
    }


}
