package com.hhly.lawyer.ui;

public interface ContractView {

    interface LoginContract {
        void validateCredentials(String username, String password);
    }
}
