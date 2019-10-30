package com.rainbean.taxi.account.view;

public interface IView {

    /**
     * 显示loading
     */
    void showLoading();
    /**
     *  显示错误
     */
    void showError(int Code, String msg);
}
