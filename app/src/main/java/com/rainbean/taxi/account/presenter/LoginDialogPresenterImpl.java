package com.rainbean.taxi.account.presenter;

import com.rainbean.taxi.account.module.IAccountManager;
import com.rainbean.taxi.account.module.response.LoginResponse;
import com.rainbean.taxi.account.view.ILoginView;
import com.rainbean.taxi.common.databus.RegisterBus;

public class LoginDialogPresenterImpl implements ILoginDialogPresenter {


    private ILoginView view;
    private IAccountManager accountManager;
    /**
     * 接收子线程消息的 Handler
     */
    /*static class MyHandler extends Handler {
        // 弱引用
        WeakReference<LoginDialogPresenterImpl> dialogRef;
        public MyHandler(LoginDialogPresenterImpl presenter)
        {
            dialogRef = new WeakReference<LoginDialogPresenterImpl>(presenter);
        }
        @Override
        public void handleMessage(Message msg) {
            LoginDialogPresenterImpl presenter = dialogRef.get();
            if (presenter == null) {
                return;
            }
            // 处理UI 变化
            switch (msg.what) {
                case IAccountManager.LOGIN_SUC:
                    // 登录成功
                    presenter.view.showLoginSuc();
                    break;
                case IAccountManager.PW_ERROR:
                   // 密码错误
                    presenter.view.showError(IAccountManager.PW_ERROR, "");
                    break;
                case IAccountManager.SERVER_FAIL:
                    // 服务器错误
                    presenter.view.showError(IAccountManager.SERVER_FAIL, "");
                    break;
            }
        }
    }*/


    public LoginDialogPresenterImpl(ILoginView view,
                                    IAccountManager accountManager) {
        this.view = view;
        this.accountManager = accountManager;
        //accountManager.setHandler(new MyHandler(this));
    }


    @Override
    public void requestLogin(String phone, String password) {
        accountManager.login(phone, password);
    }

    @RegisterBus
    public void onLoginResponse(LoginResponse response) {
        switch (response.getCode()) {
            case IAccountManager.LOGIN_SUC:
                // 登录成功
                view.showLoginSuc();
                break;
            case IAccountManager.PW_ERROR:
                // 密码错误
                view.showError(IAccountManager.PW_ERROR, "");
                break;
            case IAccountManager.SERVER_FAIL:
                // 服务器错误
                view.showError(IAccountManager.SERVER_FAIL, "");
                break;
        }
    }

}
