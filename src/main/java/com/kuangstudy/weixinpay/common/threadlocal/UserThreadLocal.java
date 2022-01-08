package com.kuangstudy.weixinpay.common.threadlocal;


import com.kuangstudy.weixinpay.entity.User;

/**
 * @Author xueke
 * @Date 2020/5/25 14:52
 */
public class UserThreadLocal {

    private static ThreadLocal<User
            > userDoThreadLocal = new ThreadLocal<>();

    public static void put(User userDo) {
        userDoThreadLocal.set(userDo);
    }

    public static User get() {
        return userDoThreadLocal.get();
    }

    public static void remove() {
        userDoThreadLocal.remove();
    }
}
