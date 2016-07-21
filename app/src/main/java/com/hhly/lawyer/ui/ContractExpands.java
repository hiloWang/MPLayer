package com.hhly.lawyer.ui;

/**
 * 扩展的契约接口
 * 作用：当共用的接口不足以满足需求时，这里可以自定义接口供Presenter使用。
 */
public interface ContractExpands {

    /**
     * 登录
     */
    interface LoginContract {
        void validateCredentials(String username, String password);
    }
}
