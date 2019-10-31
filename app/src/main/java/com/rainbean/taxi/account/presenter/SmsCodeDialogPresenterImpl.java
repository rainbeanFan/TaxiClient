package com.rainbean.taxi.account.presenter;

import com.rainbean.taxi.account.module.IAccountManager;
import com.rainbean.taxi.account.module.response.SmsCodeResponse;
import com.rainbean.taxi.account.module.response.UserExistResponse;
import com.rainbean.taxi.account.view.ISmsCodeDialogView;
import com.rainbean.taxi.common.databus.RegisterBus;

public class SmsCodeDialogPresenterImpl implements ISmsCodeDialogPresenter {

    private ISmsCodeDialogView view;
    private IAccountManager accountManager;

    @RegisterBus
    public void onSmsCodeResponse(SmsCodeResponse smsCodeResponse) {
        switch (smsCodeResponse.getCode()) {
            case IAccountManager.SMS_SEND_SUC:
                view.showCountDownTimer();
                break;
            case IAccountManager.SMS_SEND_FAIL:
                view.showError(IAccountManager.SMS_SEND_FAIL, "");
                break;
            case IAccountManager.SMS_CHECK_SUC:
                view.showSmsCodeCheckState(true);

                break;
            case IAccountManager.SMS_CHECK_FAIL:
                view.showError(IAccountManager.SMS_CHECK_FAIL, "");
                break;
            case IAccountManager.SERVER_FAIL:
                view.showError(IAccountManager.SERVER_FAIL, "");
                break;
        }
    }

    @RegisterBus
    public void onSmsCodeResponse(UserExistResponse userExistResponse) {
        switch (userExistResponse.getCode()) {

            case IAccountManager.USER_EXIST:
                view.showUserExist(true);
                break;
            case IAccountManager.USER_NOT_EXIST:
                view.showUserExist(false);
                break;
            case IAccountManager.SERVER_FAIL:
                view.showError(IAccountManager.SERVER_FAIL, "");
                break;

        }
    }
    public SmsCodeDialogPresenterImpl(ISmsCodeDialogView view,
                                      IAccountManager accountManager) {
        this.view = view;
        this.accountManager = accountManager;

    }

    /**
     * 获取验证码
     * @param phone
     */
    @Override
    public void requestSendSmsCode(String phone) {
        accountManager.fetchSMSCode(phone);
    }

    /**
     * 验证码这验证码
     * @param phone
     * @param smsCode
     */
    @Override
    public void requestCheckSmsCode(String phone, String smsCode) {

        accountManager.checkSmsCode(phone, smsCode);
    }

    /**
     * 检查用户是否存在
     * @param phone
     */
    @Override
    public void requestCheckUserExist(String phone) {

        accountManager.checkUserExist(phone);
    }

}
